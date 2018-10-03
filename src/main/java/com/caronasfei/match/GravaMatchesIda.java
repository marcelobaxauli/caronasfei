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
	public void gravaSugestoes(Set<No> nosMotoristas) {

		// puxa os trajetos atraves dos nós motoristas. Verifica se esses trajetos já
		// estão gravados como sugestão no banco.
		// caso não esteja cria uma nova sugestao e grava no banco.
		// caso estejam verifica se os nós são os mesmos, caso seja ignora, caso não
		// seja verifica se o score geral é melhor que
		// o score da sugestao cadastrada, para "substituir", lógico que se o usuário
		// não tiver visualizado a sugestão em questão.

		for (No noMotorista : nosMotoristas) {

			List<No> nosPassageiros = new LinkedList<No>();

			No noPassageiro = noMotorista.getProximoNo();
			while (noPassageiro != null) {
				nosPassageiros.add(noPassageiro);
				noPassageiro = noPassageiro.getProximoNo();
			}

			// TODO verificar se a intenção de carona do motorista e dos passageiros ainda estão ativas
			// antes de criar a sugestao de trajeto
			
			int score = noMotorista.getCurrentBestScore(); // na ida a propagação inicia na FEI e termina no nó
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
				
				List<SugestaoTrajetoPassageiro> sugestaoTrajetoPassageiros = new LinkedList<SugestaoTrajetoPassageiro>();
				for (No noPassageiroAtual : nosPassageiros) {
					SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = new SugestaoTrajetoPassageiro();

					if (noPassageiroAtual.getSugestaoTrajetoUsuario() != null) {
						SugestaoTrajetoPassageiro sugestaoTrajetoPassageiroExistente = (SugestaoTrajetoPassageiro) noPassageiroAtual
								.getSugestaoTrajetoUsuario();
						sugestaoTrajetoPassageiro.setEstado(sugestaoTrajetoPassageiroExistente.getEstado());
					} else {
						sugestaoTrajetoPassageiro.setEstado(SugestaoTrajetoPassageiroEstado.NAO_CONFIRMADO);
					}

				}
				
				if (sugestaoTrajeto == null) {
					this.entityManager.persist(novoSugestaoTrajeto);
				}

			}

		}

	}

}
