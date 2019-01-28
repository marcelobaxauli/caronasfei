package com.caronasfei.match;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
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
@Scope("prototype")
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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void formaMatches() {

		List<IntencaoCarona> intencoesMotoristas = this.intencaoServico.findMotoristasDisponiveis(DirecaoCarona.IDA_FEI);
		List<IntencaoCarona> intencoesPassageiros = this.intencaoServico.findAllPassageirosDisponiveis(DirecaoCarona.IDA_FEI);
		Endereco enderecoFei = this.enderecoServico.findEnderecoById(Endereco.ID_ENDERECO_FEI_SBC);

		LOGGER.info("encontrando matches para {} intencoes de motoristas e {} de passageiros",
				intencoesMotoristas.size(), intencoesPassageiros.size());
		
		for (IntencaoCarona intencaoMotorista : intencoesMotoristas) {

			SugestaoTrajeto sugestaoComPassageirosEmSubstituicao = this.sugestaoTrajetoService.findSugestaoComPassageirosEmSubstituicaoParaMotorista(intencaoMotorista);
			
			this.dijkstraAlgoritmo.rodar(intencaoMotorista, intencoesPassageiros, enderecoFei, sugestaoComPassageirosEmSubstituicao);

			No noDestino = this.dijkstraAlgoritmo.getNoDestino();			
			this.gravaMatches.gravaSugestoes(noDestino);

			LOGGER.info("gravando sugestoes de carona encontradas...");

		}
		
//			LOGGER.info("não executando procura de matches. {} intencoes de motoristas e {} de passageiros",
//					intencoesMotoristas.size(), intencoesPassageiros.size());			

	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void substituiMatches() {

		// TODO: buscar todos os motoristas que estão em uma sugestao de trajeto com algum passageiro 
		// em substituição
		
		List<SugestaoTrajeto> sugestaoTrajetoComSubstituicaoList = this.sugestaoTrajetoService.findAllSugestaoTrajetoComPassageiroEmSubstituicao();
		Endereco enderecoFei = this.enderecoServico.findEnderecoById(Endereco.ID_ENDERECO_FEI_SBC);
		
		for (SugestaoTrajeto sugestaoTrajetoComSubstituicao : sugestaoTrajetoComSubstituicaoList) {
			IntencaoCarona intencaoCaronaMotoristaSubstituicao = sugestaoTrajetoComSubstituicao.getMotorista().getIntencaoCarona();
			List<IntencaoCarona> intencoesPassageirosSubstituicao = new LinkedList<IntencaoCarona>();
			List<SugestaoTrajetoPassageiro> passageirosEmSubstituicao = sugestaoTrajetoComSubstituicao.getPassageiros();
			
			for (SugestaoTrajetoPassageiro sugestaoPassageiro : passageirosEmSubstituicao) {
				intencoesPassageirosSubstituicao.add(sugestaoPassageiro.getIntencaoCarona());
			}
				
			this.dijkstraAlgoritmo.rodar(intencaoCaronaMotoristaSubstituicao, intencoesPassageirosSubstituicao, enderecoFei, sugestaoTrajetoComSubstituicao);
			
		}
		
		
	}

}
