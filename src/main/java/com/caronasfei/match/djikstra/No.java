package com.caronasfei.match.djikstra;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.model.PercorreNos;
import com.caronasfei.match.djikstra.model.RestricaoTempo;
import com.caronasfei.match.djikstra.model.SolucaoParcialDebug;

public class No<E> {

	private Set<Vertice> verticesDeSaida = new HashSet<Vertice>();

	private Vertice verticeSelecionado;

	private int numero;

	private int score;
	
	private Integer caminhoScore;

	private RestricaoTempo restricaoTempo;

	private int numeroPassageirosAtual;
	private long currentTime;

	private IntencaoCarona intencaoCarona;

	private E sugestaoTrajetoUsuario;

	private Endereco endereco;

	private Grafo graph;

	// nó já confirmado pelo motorista/passageiro
	private boolean fixo;

	public No(int numero, Grafo graph) {

		this.numero = numero;
		this.graph = graph;
		this.verticeSelecionado = null;
		this.caminhoScore = null;
		
	}

	public Vertice getVerticeSelecionado() {
		return verticeSelecionado;
	}

	public void setVerticeSelecionado(Vertice verticeSelecionado) {
		this.verticeSelecionado = verticeSelecionado;
	}

	public Set<Vertice> getVerticesDeSaida() {
		return verticesDeSaida;
	}

	public void addVerticeDeSaida(Vertice vertex) {
		this.verticesDeSaida.add(vertex);
	}

	public int getNumber() {
		return numero;
	}

	public void setNumber(int number) {
		this.numero = number;
	}

	public IntencaoCarona getIntencaoCarona() {
		return intencaoCarona;
	}

	public void setIntencaoCarona(IntencaoCarona intencaoCarona) {
		this.intencaoCarona = intencaoCarona;
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {

		if (this.numero == this.graph.getCurrentSize()) {
			return true;
		}

		return this.restricaoTempo.isInTimeRestriction(visitTime, maximumTime);
	}

	public RestricaoTempo getRestricaoTempo() {
		return this.restricaoTempo;
	}

	public void setRestricaoTempo(RestricaoTempo timeRestriction) {
		this.restricaoTempo = timeRestriction;
	}

	public int getCurrentNumberOfPassengers() {
		return numeroPassageirosAtual;
	}

	public Integer getCaminhoScore() {
		return caminhoScore;
	}

	public void setCaminhoScore(Integer caminhoScore) {
		this.caminhoScore = caminhoScore;
	}

	public void setCurrentNumberOfPassengers(int currentNumberOfPassengers) {
		this.numeroPassageirosAtual = currentNumberOfPassengers;
	}

	// span costs to forward adjacent vertices
	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isFixo() {
		return fixo;
	}

	public void setFixo(boolean fixo) {
		this.fixo = fixo;
	}

	public E getSugestaoTrajetoUsuario() {
		return sugestaoTrajetoUsuario;
	}

	public void setSugestaoTrajetoUsuario(E sugestaoTrajetoUsuario) {
		this.sugestaoTrajetoUsuario = sugestaoTrajetoUsuario;
	}

	public void spanCustos(Map<Integer, No> nosVisitados) {

		if (this.intencaoCarona != null && this.intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA
				&& this.numeroPassageirosAtual >= this.intencaoCarona.getNumeroAssentos()) {
			return;
		}

		for (Vertice verticeDeSaida : this.verticesDeSaida) {

			if (verticeDeSaida.getI() > this.graph.getCurrentSize()
					|| verticeDeSaida.getJ() > this.graph.getCurrentSize()) {
				break;
			}

			No noCandidato = verticeDeSaida.getNoDestino();

			
			double custoTransitoMinutos = verticeDeSaida.getCustoTransito() / 60; // custo esta em segundos / transforma em minutos
			long custoEstimadoNoCandidato = (long) (this.currentTime + custoTransitoMinutos);

			int numeroPassageirosCandidato = this.numeroPassageirosAtual;

			if (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
				numeroPassageirosCandidato++;
			}

			String bairroOrigem = this.getEndereco().getBairro();
			String cidadeOrigem = this.getEndereco().getCidade();
			
			String bairroNodo = noCandidato.getEndereco().getBairro();
			String cidadeNodo = noCandidato.getEndereco().getCidade();

			// TODO só somar o numero de passageiros se o nó candidato for de passageiro
			// fera.
			int noCandidatoScore = this.graph.getObjectiveValue(numeroPassageirosCandidato, custoEstimadoNoCandidato);
			
			// TODO preciso ver se o nó i já recusou o nó j, ou foi recusado.
			// criar uma tabela de recusa pra facilitar o trabalho, no estilo nó i recusou
			// nó j.
			// em consequencia nó j foi recusado por nó i.
			// para não ter que consultar o banco a cada verifificação, carregar essa
			// relação de recusas em uma estrutura
			// de dados em memória para agilizar estas verificações.

			if (nosVisitados.get(noCandidato.getNumber()) == null
					&& noCandidatoScore < noCandidato.getScore()
					&& RestricaoTempo.isCaminhoValido(verticeDeSaida, this)
					&& (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA 
						|| (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA
							&& this.numeroPassageirosAtual + 1 <= noCandidato.getIntencaoCarona().getNumeroAssentos())
						)
					&& PercorreNos.isMelhorCaminhoScore(this, noCandidatoScore)
					) {

				// print para debug
				SolucaoParcialDebug.print(this, noCandidato, numeroPassageirosCandidato, custoTransitoMinutos, custoEstimadoNoCandidato, noCandidatoScore, true);
				
				PercorreNos.destravaDependencias(this);
				
				List<No> nosCarona = this.getNosCarona(noCandidato);

				Vertice verticeInvertido = new Vertice();
				verticeInvertido.setCustoTransito(verticeDeSaida.getCustoTransito());
				verticeInvertido.setI(verticeDeSaida.getJ());
				verticeInvertido.setJ(verticeDeSaida.getI());
				verticeInvertido.setNoDestino(this);
				
				noCandidato.setScore(noCandidatoScore);
				noCandidato.setVerticeSelecionado(verticeInvertido);
				noCandidato.setCurrentTime(custoEstimadoNoCandidato);

				if (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
					noCandidato.setCurrentNumberOfPassengers(this.numeroPassageirosAtual + 1);
				} else {
					noCandidato.setCurrentNumberOfPassengers(this.numeroPassageirosAtual);
				}
			} else {
				// print para debug
				SolucaoParcialDebug.print(this, noCandidato, numeroPassageirosCandidato, custoTransitoMinutos, custoEstimadoNoCandidato, noCandidatoScore, false);

			}

		}

	}
	
	public List<No> getNosCarona(No noCandidato) {

		List<No> nosCarona = new ArrayList<No>();
		nosCarona.add(noCandidato);
		nosCarona.add(this);
		
		for (Vertice verticeAtual = this.verticeSelecionado; verticeAtual != null; ) {
			No noDestino = verticeAtual.getNoDestino();
			
			if (noDestino != null) {
				nosCarona.add(noDestino);
				verticeAtual = noDestino.getVerticeSelecionado();
			}
			
			
		}
		
		return nosCarona;
		
	}

	public List<No> getNosCarona() {

		List<No> nosCarona = new ArrayList<No>();
		
		for (Vertice verticeAtual = this.verticeSelecionado; verticeAtual != null; ) {
			No noDestino = verticeAtual.getNoDestino();
			
			if (noDestino != null) {
				nosCarona.add(noDestino);
				verticeAtual = noDestino.getVerticeSelecionado();
			}
			
			
		}
		
		return nosCarona;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		No other = (No) obj;
		if (numero != other.numero)
			return false;
		return true;
	}

}
