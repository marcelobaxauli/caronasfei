package com.caronasfei.match;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajeto.SugestaoTrajetoEstado;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista.SugestaoTrajetoMotoristaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro.SugestaoTrajetoPassageiroEstado;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;

@Component
@Scope("singleton")
public class GravaMatchesIda {

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoService;

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager entityManager;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void gravaSugestoes(No noDestino) {

		// puxa os trajetos atraves dos nós motoristas. Verifica se esses trajetos já
		// estão gravados como sugestão no banco.
		// caso não esteja cria uma nova sugestao e grava no banco.
		// caso estejam verifica se os nós são os mesmos, caso seja ignora, caso não
		// seja verifica se o score geral é melhor que
		// o score da sugestao cadastrada, para "substituir", lógico que se o usuário
		// não tiver visualizado a sugestão em questão.

		List<No> nosPassageiros = new LinkedList<No>();

		Endereco enderecoDestino = noDestino.getEndereco();
		
		No noAtual = noDestino.getVerticeIncidente().getNoDestino();
		while (noAtual.getVerticeIncidente() != null) {
			if (noAtual.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
				nosPassageiros.add(0, noAtual);				
			}

			if (noAtual.getVerticeIncidente() != null) {				
				noAtual = noAtual.getVerticeIncidente().getNoDestino();
			}
		}

		// depois de percorrer todos os nós chegamos ao nó motorista
		No noMotorista = noAtual;
		
		// TODO verificar se a intenção de carona do motorista e dos passageiros ainda estão ativas
		// antes de criar a sugestao de trajeto
		
		int score = noDestino.getScore(); // na ida a propagação inicia na FEI e termina no nó
														// motorista (current best score tá no nó motorista).

		// pode ser uma substituição de passageiro tbm.. mas acredito que rode
		// corretamente da mesma forma.

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoService
				.findSugestaoTrajetoByIntencaoCaronaMotorista(noMotorista.getIntencaoCarona());

		if (sugestaoTrajeto != null && !sugestaoTrajeto.isVisualizada() && score < sugestaoTrajeto.getScore()) {
			// deleta sugestao anterior, grava sugestao nova
			this.entityManager.remove(sugestaoTrajeto);
		}

		if (sugestaoTrajeto == null || !sugestaoTrajeto.isVisualizada()) {

			SugestaoTrajetoMotorista sugestaoTrajetoMotorista = new SugestaoTrajetoMotorista();
			SugestaoTrajetoMotorista sugestaoTrajetoMotoristaNo = (SugestaoTrajetoMotorista) noMotorista
					.getSugestaoTrajetoUsuario();

			if (sugestaoTrajetoMotoristaNo != null) {
				sugestaoTrajetoMotorista.setEstado(sugestaoTrajetoMotoristaNo.getEstado());
			} else {
				sugestaoTrajetoMotorista.setEstado(SugestaoTrajetoMotoristaEstado.NAO_CONFIRMADO);
			}

			sugestaoTrajetoMotorista.setIntencaoCarona(noMotorista.getIntencaoCarona());

			SugestaoTrajeto novoSugestaoTrajeto = new SugestaoTrajeto();
			novoSugestaoTrajeto.setMotorista(sugestaoTrajetoMotorista);
			novoSugestaoTrajeto.setScore(score);
			novoSugestaoTrajeto.setVisualizada(false);
			novoSugestaoTrajeto.setEstado(SugestaoTrajetoEstado.NORMAL);
			
			for (No noPassageiroAtual : nosPassageiros) {
				SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = new SugestaoTrajetoPassageiro();

				if (noPassageiroAtual.getSugestaoTrajetoUsuario() != null) {
					SugestaoTrajetoPassageiro sugestaoTrajetoPassageiroExistente = (SugestaoTrajetoPassageiro) noPassageiroAtual
							.getSugestaoTrajetoUsuario();
					sugestaoTrajetoPassageiro.setEstado(sugestaoTrajetoPassageiroExistente.getEstado());
				} else {
					sugestaoTrajetoPassageiro.setIntencaoCarona(noPassageiroAtual.getIntencaoCarona());
					sugestaoTrajetoPassageiro.setSugestaoTrajeto(novoSugestaoTrajeto);
					sugestaoTrajetoPassageiro.setEstado(SugestaoTrajetoPassageiroEstado.NAO_CONFIRMADO);
				}
				
				novoSugestaoTrajeto.addSugestaoTrajetoPassageiro(sugestaoTrajetoPassageiro);

			}
			
			novoSugestaoTrajeto.setEnderecoDestino(enderecoDestino);
			
			if (sugestaoTrajeto == null) {
				this.entityManager.persist(novoSugestaoTrajeto);
			}

		}

	}

}
