package com.caronasfei.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.match.djikstra.DijkstraAlgorimo;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.service.endereco.EnderecoServico;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
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

	private static final Logger LOGGER = LogManager.getLogger(MatchCaronasIda.class);

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoService;

	@Autowired
	private DijkstraAlgorimo dijkstraAlgoritmo;

	@Autowired
	private IntencaoCaronaServico intencaoServico;

	@Autowired
	private EnderecoServico enderecoServico;

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager entityManager;

	@Autowired
	private GravaMatchesIda gravaMatches;

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

		if (intencoesMotoristas.size() > 0 && intencoesPassageiros.size() > 0) {
			LOGGER.info("encontrando matches para {} intencoes de motoristas e {} de passageiros",
					intencoesMotoristas.size(), intencoesPassageiros.size());
			this.dijkstraAlgoritmo.rodar(intencoes, enderecoFei, sugestoesComPassageirosEmSubstituicao);
			Set<No> nosMotoristas = this.dijkstraAlgoritmo.getNosMotoristasVisitados();

			LOGGER.info("gravando sugestoes de carona encontradas...");
			
			//this.gravaMatches.gravaSugestoes(nosMotoristas);
		} else {
			LOGGER.info("não executando procura de matches. {} intencoes de motoristas e {} de passageiros",
					intencoesMotoristas.size(), intencoesPassageiros.size());			
		}

	}

}
