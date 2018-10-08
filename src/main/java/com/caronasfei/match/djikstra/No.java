package com.caronasfei.match.djikstra;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.model.RestricaoTempo;

public class No<E> {
	
	private Set<Vertice> verticesDeSaida = new HashSet<Vertice>();
	
	private Vertice verticeSelecionado;

	private int number;

	private RestricaoTempo restricaoTempo;

	private int numeroPassageirosAtual;
	private long currentTime;

	private int currentBestScore;

	private IntencaoCarona intencaoCarona;

	private E sugestaoTrajetoUsuario;

	private Endereco endereco;

	private Grafo graph;

	// nó já confirmado pelo motorista/passageiro
	private boolean fixo;

	public No(int number, Grafo graph) {

		this.number = number;
		this.graph = graph;
		this.verticeSelecionado = null;

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
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public IntencaoCarona getIntencaoCarona() {
		return intencaoCarona;
	}

	public void setIntencaoCarona(IntencaoCarona intencaoCarona) {
		this.intencaoCarona = intencaoCarona;
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {

		if (this.number == this.graph.getCurrentSize()) {
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

	public int getCurrentBestScore() {
		return currentBestScore;
	}

	public void setCurrentBestScore(int currentBestScore) {
		this.currentBestScore = currentBestScore;
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

		if (this.intencaoCarona != null 
				&& this.intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA
				&& this.numeroPassageirosAtual >= this.intencaoCarona.getNumeroAssentos()) {
			return;
		}

		for (Vertice verticeDeSaida : this.verticesDeSaida) {

			if (verticeDeSaida.getI() > this.graph.getCurrentSize()
					|| verticeDeSaida.getJ() > this.graph.getCurrentSize()) {
				break;
			}

			No noCandidato = verticeDeSaida.getNoDestino();
			
			long custoEstimadoNoCandidato = (long) (this.currentTime + verticeDeSaida.getCustoTransito());
			custoEstimadoNoCandidato /= 60; // custo esta em segundos / transforma em minutos

			int numeroPassageirosCandidato = this.numeroPassageirosAtual;

			if (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
				numeroPassageirosCandidato++;
			}
			
			// TODO só somar o numero de passageiros se o nó candidato for de passageiro fera.
			int noCandidatoScore = this.graph.getObjectiveValue(numeroPassageirosCandidato,
					custoEstimadoNoCandidato);

			// TODO preciso ver se o nó i já recusou o nó j, ou foi recusado.
			// criar uma tabela de recusa pra facilitar o trabalho, no estilo nó i recusou
			// nó j.
			// em consequencia nó j foi recusado por nó i.
			// para não ter que consultar o banco a cada verifificação, carregar essa
			// relação de recusas em uma estrutura
			// de dados em memória para agilizar estas verificações.

			if (nosVisitados.get(noCandidato.getNumber()) == null
					&& RestricaoTempo.isCaminhoValido(verticeDeSaida, this)
					&& (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA ||
							(noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA
								&& this.numeroPassageirosAtual + 1 <= noCandidato.getIntencaoCarona().getNumeroAssentos()))
					&& noCandidatoScore < noCandidato.getCurrentBestScore()) {

				noCandidato.setCurrentBestScore(noCandidatoScore);
				noCandidato.setVerticeSelecionado(verticeDeSaida);
				noCandidato.setCurrentTime(custoEstimadoNoCandidato);
				
				if (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
					noCandidato.setCurrentNumberOfPassengers(this.numeroPassageirosAtual + 1);	
				} else {
					noCandidato.setCurrentNumberOfPassengers(this.numeroPassageirosAtual);					
				}
			}

		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		if (number != other.number)
			return false;
		return true;
	}

}
