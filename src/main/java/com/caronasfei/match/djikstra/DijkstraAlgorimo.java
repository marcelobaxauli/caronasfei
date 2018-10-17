package com.caronasfei.match.djikstra;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import com.caronasfei.match.djikstra.model.PercorreNos;

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
	public void rodar(IntencaoCarona intencaoCaronaMotorista, List<IntencaoCarona> intencoesCaronaPassageiro,
			Endereco destino, SugestaoTrajeto sugestaoTrajetoComSubstituicao) {

		Map<Integer, No> nosVisitados = new HashMap<Integer, No>();
		this.nosMotoristasVisitados = new HashMap<Integer, No>();

		List<IntencaoCarona> intencoes = new LinkedList<IntencaoCarona>();
		intencoes.add(intencaoCaronaMotorista);
		intencoes.addAll(intencoesCaronaPassageiro);
		this.grafo.init(intencoesCaronaPassageiro.size() + 2, intencaoCaronaMotorista);
		this.grafo.instancia(intencoes, destino);
		if (sugestaoTrajetoComSubstituicao != null) {
			this.grafo.fixaNosConfirmados(sugestaoTrajetoComSubstituicao);			
		}

		Date inicio = new Date();

		No no = null;
		do {

			no = this.grafo.getNoMinimoCusto();

			if (no != null) {

				no.expandeCustos(nosVisitados);
				nosVisitados.put(no.getNumber(), no);

			}
			
			// TODO apagar isto daqui.
			// isso só serve pra debug, no auxilio do solucaoParcialDebug.print
			System.out.println();

		} while (no != null && no.getScore() != Integer.MAX_VALUE && no.getIntencaoCarona() != null);

		Date fim = new Date();

		LOGGER.info("tempo de processamento: " + ((fim.getTime() - inicio.getTime())) + " milisegundos");

	}

	public No getNoDestino() {
		return this.grafo.getUltimoNo();
	}

}
