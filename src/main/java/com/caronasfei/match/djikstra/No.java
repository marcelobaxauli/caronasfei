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
import com.caronasfei.match.djikstra.model.RestricaoTempo;
import com.caronasfei.match.djikstra.model.SolucaoParcialDebug;

public class No<E> {

	private Set<Vertice> verticesDeSaida = new HashSet<Vertice>();

	private Vertice verticeIncidente;

	// TODO: só pra debug
	private No noAnterior;
	
	private int numero;

	private int score;
	
	private Integer caminhoScore;

	private RestricaoTempo restricaoTempo;

	private int numeroPassageirosAtual;
	private long horarioEstimado;

	private IntencaoCarona intencaoCarona;

	private E sugestaoTrajetoUsuario;

	private Endereco endereco;

	private Grafo grafo;

	// nó já confirmado pelo motorista/passageiro
	private boolean fixo;

	public No(int numero, Grafo graph) {

		this.numero = numero;
		this.grafo = graph;
		this.verticeIncidente = null;
		this.caminhoScore = null;
		this.noAnterior = null;
		
	}

	public Vertice getVerticeIncidente() {
		return verticeIncidente;
	}

	public void setVerticeIncidente(Vertice verticeIncidente) {
		this.verticeIncidente = verticeIncidente;
	}
	
	public No getNoAnterior() {
		return noAnterior;
	}

	public void setNoAnterior(No noAnterior) {
		this.noAnterior = noAnterior;
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

		if (this.numero == this.grafo.getCurrentSize()) {
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
	public long getHorarioEstimado() {
		return horarioEstimado;
	}

	public void setHorarioEstimado(long currentTime) {
		this.horarioEstimado = currentTime;
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

	public void expandeCustos(Map<Integer, No> nosVisitados) {

		if (this.intencaoCarona != null && this.intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA
				&& this.numeroPassageirosAtual >= this.intencaoCarona.getNumeroAssentos()) {
			return;
		}

		for (Vertice verticeSaida : this.verticesDeSaida) {

			if (verticeSaida.getI() > this.grafo.getCurrentSize()
					|| verticeSaida.getJ() > this.grafo.getCurrentSize()) {
				break;
			}

			No noCandidato = verticeSaida.getNoOrigem();

			long custoTransitoSaida = verticeSaida.getCustoTransito(); // custo esta em milisegundos
			long horarioEstimadoNoCandidato = (long) (this.horarioEstimado + custoTransitoSaida);

			int numeroPassageirosCandidato = this.numeroPassageirosAtual;

			if (noCandidato.getIntencaoCarona() != null && noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
				numeroPassageirosCandidato++;
			}

			String bairroOrigem = this.getEndereco().getBairro();
			String cidadeOrigem = this.getEndereco().getCidade();
			
			String bairroNodo = noCandidato.getEndereco().getBairro();
			String cidadeNodo = noCandidato.getEndereco().getCidade();

			// TODO só somar o numero de passageiros se o nó candidato for de passageiro
			// fera.
			int noCandidatoScore = this.grafo.getObjectiveValue(numeroPassageirosCandidato, this.getCustoTransitoAcumulado() + custoTransitoSaida);
			
			// TODO preciso ver se o nó i já recusou o nó j, ou foi recusado.
			// criar uma tabela de recusa pra facilitar o trabalho, no estilo nó i recusou
			// nó j.
			// em consequencia nó j foi recusado por nó i.
			// para não ter que consultar o banco a cada verifificação, carregar essa
			// relação de recusas em uma estrutura
			// de dados em memória para agilizar estas verificações.

			if (noCandidato.getNumber() == 9) {
				System.out.println("debug");
			}
			
			if (nosVisitados.get(noCandidato.getNumber()) == null
					&& noCandidatoScore < noCandidato.getScore()
					&& RestricaoTempo.isCaminhoValido(horarioEstimadoNoCandidato, noCandidato)
					&& (noCandidato.getIntencaoCarona() == null 
						|| (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA 
						|| (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA
							&& this.numeroPassageirosAtual + 1 <= noCandidato.getIntencaoCarona().getNumeroAssentos())
						))
					) {
				
				// print para debug
				SolucaoParcialDebug.print(this, noCandidato, numeroPassageirosCandidato, custoTransitoSaida, horarioEstimadoNoCandidato, noCandidatoScore, true);

				Vertice vertice = new Vertice();
				vertice.setCustoTransito(verticeSaida.getCustoTransito());
				vertice.setI(verticeSaida.getI());
				vertice.setJ(verticeSaida.getJ());
				vertice.setNoOrigem(this);
				
				noCandidato.setScore(noCandidatoScore);
				noCandidato.setVerticeIncidente(vertice);
				noCandidato.setHorarioEstimado(horarioEstimadoNoCandidato);
				noCandidato.setNoAnterior(this);
				
				if (noCandidato.getIntencaoCarona() != null) {
					if (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
						noCandidato.setCurrentNumberOfPassengers(this.numeroPassageirosAtual + 1);
					} else {
						noCandidato.setCurrentNumberOfPassengers(this.numeroPassageirosAtual);
					}	
				}
			} else {
				// print para debug

				if (noCandidatoScore >= noCandidato.getScore()) {
					System.out.println("	+ invalido score calculado não é menor que score do nó candidato");
				}

				
				if (nosVisitados.get(noCandidato.getNumber()) != null) {
					System.out.println("	+ invalido porque o nó candidato já foi visitado");
				}
				
				if (!RestricaoTempo.isCaminhoValido(horarioEstimadoNoCandidato, noCandidato)) {
					System.out.println("	+ invalido porque o não satisfaz restrição de tempo do nó candidato");
					
				}
				
				if (noCandidato.getIntencaoCarona() != null && (noCandidato.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA
						&& this.numeroPassageirosAtual + 1 > noCandidato.getIntencaoCarona().getNumeroAssentos())) {
					System.out.println("	+ invalido porque o número de assentos é maior que o limite");
				}
				
				SolucaoParcialDebug.print(this, noCandidato, numeroPassageirosCandidato, custoTransitoSaida, horarioEstimadoNoCandidato, noCandidatoScore, false);

			}

		}

	}
	
	public List<No> getNosCarona(No noCandidato) {

		List<No> nosCarona = new ArrayList<No>();
		nosCarona.add(noCandidato);
		nosCarona.add(this);
		
		for (Vertice verticeAtual = this.verticeIncidente; verticeAtual != null; ) {
			No noDestino = verticeAtual.getNoOrigem();
			
			if (noDestino != null) {
				nosCarona.add(noDestino);
				verticeAtual = noDestino.getVerticeIncidente();
			}
			
			
		}
		
		return nosCarona;
		
	}

	public List<No> getNosCarona() {

		List<No> nosCarona = new ArrayList<No>();
		
		for (Vertice verticeAtual = this.verticeIncidente; verticeAtual != null; ) {
			No noDestino = verticeAtual.getNoOrigem();
			
			if (noDestino != null) {
				nosCarona.add(noDestino);
				verticeAtual = noDestino.getVerticeIncidente();
			}
			
			
		}
		
		return nosCarona;
		
	}
	
	public long getCustoTransitoAcumulado() {
		
		if (this.verticeIncidente == null) {
			return 0;
		}
		
		long custoTransitoAcumulado = 0;
		Vertice verticeIncidenteAtual = this.verticeIncidente;
		do {
			
			custoTransitoAcumulado += verticeIncidenteAtual.getCustoTransito();
			
			verticeIncidenteAtual = verticeIncidenteAtual.getNoOrigem().getVerticeIncidente();
			
		} while (verticeIncidenteAtual != null);
		
		return custoTransitoAcumulado;
		
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
