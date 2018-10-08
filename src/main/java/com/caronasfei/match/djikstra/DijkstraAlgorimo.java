package com.caronasfei.match.djikstra;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;

@Component
@Scope("singleton")
public class DijkstraAlgorimo /* implements Algoritmo */ {

	private static final Logger LOGGER = LogManager.getLogger(DijkstraAlgorimo.class);

	// TODO: isso deveria ser configuração externa (via config .properties)
	private static final int MAXIMO_NUMERO_NOS = 3000;

	@Autowired
	private Grafo grafo;
	
	private Map<Integer, No> nosMotoristasVisitados;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void rodar(List<IntencaoCarona> intencoesCarona, Endereco destino, List<SugestaoTrajeto> sugestoesTrajetoComSubstituicao) {

		Date inicio = new Date();

		Map<Integer, No> nosVisitados = new HashMap<Integer, No>();
		this.nosMotoristasVisitados = new HashMap<Integer, No>();

		this.grafo.init(intencoesCarona.size() + 1);
		this.grafo.instancia(intencoesCarona, destino);
		this.grafo.fixaNosConfirmados(sugestoesTrajetoComSubstituicao);
		Set<Integer> nosMotoristas = this.grafo.getMotoristaNodesNumbers();

		No no = null;
		do {
			
			no = this.grafo.getNoMinimoCusto();

			if (no != null) {
				no.spanCustos(nosVisitados);

				nosVisitados.put(no.getNumber(), no);

				if (no.getIntencaoCarona() != null 
						&& no.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA) {
					nosMotoristasVisitados.put(no.getNumber(), no);
				}
			}

		} while (no != null && no.getCurrentBestScore() != Integer.MAX_VALUE
				&& nosMotoristasVisitados.size() != nosMotoristas.size());

		Date fim = new Date();

		LOGGER.info("tempo de processamento: " + ((fim.getTime() - inicio.getTime())) + " milisegundos");

	}
	
	public Set<No> getNosMotoristasVisitados() {
		
		Collection<No> values = this.nosMotoristasVisitados.values();
		
		Set<No> nos = new HashSet<No>();
		nos.addAll(values);
		
		return nos;
	}

}
