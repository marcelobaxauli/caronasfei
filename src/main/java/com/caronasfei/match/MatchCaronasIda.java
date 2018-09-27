package com.caronasfei.match;

import java.util.ArrayList;
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

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista.SugestaoTrajetoMotoristaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro.SugestaoTrajetoPassageiroEstado;
import com.caronasfei.match.djikstra.DijkstraAlgorimo;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;

/**
 * 
 * Faz o match de caronas para a ida para a FEI
 * 
 * @author mbax
 *
 */

@Component
@Scope("singleton")
public class MatchCaronasIda {

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoService;

	@Autowired
	private DijkstraAlgorimo dijkstraAlgoritmo;

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager entityManager;

	public void encontraMatches() {
		List<IntencaoCarona> intencoesMotoristas = this.intencaoServico
				.findMotoristasDisponiveis(DirecaoCarona.IDA_FEI);
		List<IntencaoCarona> intencoesPassageiros = this.intencaoServico
				.findAllPassageirosDisponiveis(DirecaoCarona.IDA_FEI);
		List<IntencaoCarona> intencoes = new ArrayList<IntencaoCarona>();
		intencoes.addAll(intencoesMotoristas);
		intencoes.addAll(intencoesPassageiros);
		Endereco enderecoFei = this.enderecoServico.findEnderecoById(Endereco.ID_ENDERECO_FEI_SBC);
		List<SugestaoTrajeto> sugestoesComPassageirosEmSubstituicao = this.sugestaoTrajetoService
				.findSugestoesComPassageirosEmSubstituicao();

		Set<No> nosMotoristas = this.matchCaronasIda.encontraMatches(intencoes, enderecoFei,
				sugestoesComPassageirosEmSubstituicao);
		this.matchCaronasIda.gravaSugestoes(nosMotoristas);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Set<No> encontraMatches(List<IntencaoCarona> intencoes, Endereco enderecoFei,
			List<SugestaoTrajeto> sugestoesComPassageirosEmSubstituicao) {
		this.dijkstraAlgoritmo.rodar(intencoes, enderecoFei, sugestoesComPassageirosEmSubstituicao);
		Set<No> nosMotoristas = this.dijkstraAlgoritmo.getNosMotoristasVisitados();

		return nosMotoristas;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
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

			}

		}

	}

}
