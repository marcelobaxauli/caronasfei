package com.caronasfei.service.sugestao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.sugestao.RejeicaoCarona;
import com.caronasfei.db.sugestao.RejeicaoCarona.RejeicaoCaronaSentido;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro.SugestaoTrajetoPassageiroEstado;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.service.exception.DomainSecurityException;
import com.caronasfei.service.intencao.IntencaoCaronaServico;

@Service
public class SugestaoTrajetoServico {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Autowired
	private IntencaoCaronaServico intencaoCaronaServico;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarSugestaoTrajeto(SugestaoTrajeto sugestaoTrajeto) {
		em.persist(sugestaoTrajeto);
	}

	@Transactional(readOnly = true)
	public SugestaoTrajeto findById(int sugestaoTrajetoId) {
	
		TypedQuery<SugestaoTrajeto> query = this.em.createQuery("SELECT st FROM SugestaoTrajeto st "
				+ "WHERE st.id = :id_sugestao_trajeto", SugestaoTrajeto.class);
		
		query.setParameter("id_sugestao_trajeto", sugestaoTrajetoId);
		
		List<SugestaoTrajeto> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
		
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajetoMotorista findMotoristaById(int motoristaId) {
		TypedQuery<SugestaoTrajetoMotorista> query = this.em.createQuery("SELECT stm FROM SugestaoTrajeto st "
				+ "INNER JOIN SugestaoTrajetoMotorista stm "
				+ "ON st.motorista = stm.id "
				+ "WHERE stm.id = :motorista_id", SugestaoTrajetoMotorista.class);
		
		query.setParameter("motorista_id", motoristaId);
		
		List<SugestaoTrajetoMotorista> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
		
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajetoPassageiro findPassageiroById(int passageiroId) {
		TypedQuery<SugestaoTrajetoPassageiro> query = this.em.createQuery("SELECT stp FROM SugestaoTrajetoPassageiro stp "
				+ "WHERE stp.id = :passageiro_id", SugestaoTrajetoPassageiro.class);
		
		query.setParameter("passageiro_id", passageiroId);
		
		List<SugestaoTrajetoPassageiro> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajeto findSugestaoTrajetoByIntencaoCaronaMotorista(IntencaoCarona intencaoCaronaMotorista) {
		
		TypedQuery<SugestaoTrajeto> query = em.createQuery("SELECT st FROM SugestaoTrajeto st "
							   + "INNER JOIN SugestaoTrajetoMotorista stm "
							   + "ON st.motorista = stm.id "
							   + "INNER JOIN SugestaoTrajetoPassageiro stp "
							   + "ON st.id = stp.sugestaoTrajeto "
							   + "WHERE stm.intencaoCarona = :intencaoCarona "
							   + "AND stp.estado != 'REJEITADO_MOTORISTA' "
							   + "AND stp.estado != 'REJEITADO_PASSAGEIRO' ", SugestaoTrajeto.class);
		
		query.setParameter("intencaoCarona", intencaoCaronaMotorista);
		
		List<SugestaoTrajeto> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajeto findByIntencaoCarona(IntencaoCarona intencaoCarona) {
		
		if (intencaoCarona.getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
			return this.findSugestaoTrajetoByIntencaoCaronaPassageiro(intencaoCarona);
		} else if (intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA) {
			return this.findSugestaoTrajetoByIntencaoCaronaMotorista(intencaoCarona);
		}
		
		return null;
		
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajeto findSugestaoTrajetoByIntencaoCaronaPassageiro(IntencaoCarona intencaoCaronaPassageiro) {
		
		TypedQuery<SugestaoTrajeto> query = this.em.createQuery("SELECT st FROM SugestaoTrajeto st "
				+ "INNER JOIN SugestaoTrajetoPassageiro stp "
				+ "ON st.id = stp.sugestaoTrajeto "
				+ "INNER JOIN IntencaoCarona i "
				+ "ON stp.intencaoCarona = i.id "
				+ "AND i.id = :intencaoPassageiroId "
				+ "AND stp.estado = :estadoPassageiro ", SugestaoTrajeto.class);
		
		query.setParameter("intencaoPassageiroId", intencaoCaronaPassageiro.getId());
		
		// o motorista precisa ter aceito o passageiro para 
		// que ele consiga visualizar a sugestao de carona
		query.setParameter("estadoPassageiro", SugestaoTrajetoPassageiroEstado.CONFIRMADO_MOTORISTA);
		
		List<SugestaoTrajeto> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
		
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajetoPassageiro getPassageiro(IntencaoCarona intencaoCaronaPassageiro, 
			SugestaoTrajeto sugestaoTrajeto) {
		
		List<SugestaoTrajetoPassageiro> passageiros = sugestaoTrajeto.getPassageiros();
		
		for (SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro : passageiros) {
			
			if (sugestaoTrajetoPassageiro.getIntencaoCarona().equals(intencaoCaronaPassageiro)) {
				return sugestaoTrajetoPassageiro;
			}
			
		}
		
		return null;
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void aceitarPassageiro(SugestaoTrajetoMotorista motorista, SugestaoTrajetoPassageiro passageiro, Usuario usuario) throws DomainSecurityException {
		
		IntencaoCarona intencaoCaronaUsuarioLogado = this.intencaoCaronaServico.findByUsuario(usuario);
		
		IntencaoCarona intencaoCaronaMotorista = motorista.getIntencaoCarona();
		
		if (!intencaoCaronaUsuarioLogado.equals(intencaoCaronaMotorista)) {
			String errorMsg = String.format("Motorista [IntencaoCarona] (id: %d) "
					+ "e usuario logado [IntencaoCarona] (id: %d) não são o mesmo", 
					intencaoCaronaMotorista.getId(), intencaoCaronaUsuarioLogado.getId());
			
			// loggar esta exception como error no controller mas enviar para o usuário apenas a mensagem "Requisicao inválida"
			// sem dar muita descrição.
			throw new DomainSecurityException(errorMsg);

		}
		// verifica se o motorista e passageiro possuem intencaoCarona ativa...
		// verifica se o motorista e passageiro estão na mesma sugestão de carona (ativa)..
		// caso algum erro mandar a exception como "RequestInvalido" no Controller (sem dar muito detalhe).
		
		SugestaoTrajeto sugestaoTrajeto = this.findSugestaoTrajetoByIntencaoCaronaMotorista(motorista.getIntencaoCarona());
		
		SugestaoTrajetoPassageiro sugestaoPassageiro = this.getPassageiro(passageiro.getIntencaoCarona(), sugestaoTrajeto);
		
		if (sugestaoPassageiro == null) {
			String errorMsg = String.format("Passageiro [SugestaoTrajetoPassageiro] (id: %d) "
					+ "e motorista [SugestaoTrajetoPassageiro] (id: %) não estão na mesma sugestao de trajeto", 
					passageiro.getId(), motorista.getId());
			
			// loggar esta exception como error no controller mas enviar para o usuário apenas a mensagem "Requisicao inválida"
			// sem dar muita descrição.
			throw new DomainSecurityException(errorMsg);
		}
		
		sugestaoPassageiro.setEstado(SugestaoTrajetoPassageiroEstado.CONFIRMADO_MOTORISTA);
		
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public void rejeitarPassageiro(SugestaoTrajetoMotorista motorista, SugestaoTrajetoPassageiro passageiro, Usuario usuario) throws DomainSecurityException {
		
		IntencaoCarona intencaoCaronaUsuarioLogado = this.intencaoCaronaServico.findByUsuario(usuario);
		
		IntencaoCarona intencaoCaronaMotorista = motorista.getIntencaoCarona();
		
		if (!intencaoCaronaUsuarioLogado.equals(intencaoCaronaMotorista)) {
			String errorMsg = String.format("Motorista [IntencaoCarona] (id: %d) "
					+ "e usuario logado [IntencaoCarona] (id: %d) não são o mesmo", 
					intencaoCaronaMotorista.getId(), intencaoCaronaUsuarioLogado.getId());
			
			// loggar esta exception como error no controller mas enviar para o usuário apenas a mensagem "Requisicao inválida"
			// sem dar muita descrição.
			throw new DomainSecurityException(errorMsg);

		}
		// verifica se o motorista e passageiro possuem intencaoCarona ativa...
		// verifica se o motorista e passageiro estão na mesma sugestão de carona (ativa)..
		// caso algum erro mandar a exception como "RequestInvalido" no Controller (sem dar muito detalhe).
		
		SugestaoTrajeto sugestaoTrajeto = this.findSugestaoTrajetoByIntencaoCaronaMotorista(motorista.getIntencaoCarona());
		
		SugestaoTrajetoPassageiro sugestaoPassageiro = this.getPassageiro(passageiro.getIntencaoCarona(), sugestaoTrajeto);
		
		if (sugestaoPassageiro == null) {
			String errorMsg = String.format("Passageiro [SugestaoTrajetoPassageiro] (id: %d) "
					+ "e motorista [SugestaoTrajetoPassageiro] (id: %) não estão na mesma sugestao de trajeto", 
					passageiro.getId(), motorista.getId());
			
			// loggar esta exception como error no controller mas enviar para o usuário apenas a mensagem "Requisicao inválida"
			// sem dar muita descrição.
			throw new DomainSecurityException(errorMsg);
		}
		
		sugestaoPassageiro.setEstado(SugestaoTrajetoPassageiroEstado.REJEITADO_MOTORISTA);
		
		RejeicaoCarona rejeicaoCarona = new RejeicaoCarona();
		rejeicaoCarona.setUsuarioMotorista(motorista.getIntencaoCarona().getUsuario());
		rejeicaoCarona.setUsuarioPassageiro(passageiro.getIntencaoCarona().getUsuario());
		rejeicaoCarona.setSentido(RejeicaoCaronaSentido.REJEICAO_MOTORISTA);
		this.em.persist(rejeicaoCarona);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void aceitarTrajetoPassageiro(SugestaoTrajeto sugestaoTrajeto, SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro) throws DomainSecurityException {
		
		SugestaoTrajetoPassageiro passageiroBuscado = this.getPassageiro(sugestaoTrajetoPassageiro.getIntencaoCarona(), sugestaoTrajeto);
		
		if (passageiroBuscado == null) {
			String errorMsg = String.format("Passageiro [SugestaoTrajetoPassageiro] (id: %d) não é passageiro "
					+ "da sugestao de trajeto (id: %d)", 
					sugestaoTrajetoPassageiro.getId(), sugestaoTrajeto.getId());
			
			throw new DomainSecurityException(errorMsg);
		}
		
		passageiroBuscado.setEstado(SugestaoTrajetoPassageiroEstado.CONFIRMADO);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void rejeitarTrajetoPassageiro(SugestaoTrajeto sugestaoTrajeto, SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro) throws DomainSecurityException {
		
		SugestaoTrajetoPassageiro passageiroBuscado = this.getPassageiro(sugestaoTrajetoPassageiro.getIntencaoCarona(), sugestaoTrajeto);
		
		if (passageiroBuscado == null) {
			String errorMsg = String.format("Passageiro [SugestaoTrajetoPassageiro] (id: %d) não é passageiro "
					+ "da sugestao de trajeto (id: %d)", 
					sugestaoTrajetoPassageiro.getId(), sugestaoTrajeto.getId());
			
			throw new DomainSecurityException(errorMsg);
		}
		
		passageiroBuscado.setEstado(SugestaoTrajetoPassageiroEstado.REJEITADO_PASSAGEIRO);
		
		RejeicaoCarona rejeicaoCarona = new RejeicaoCarona();
		rejeicaoCarona.setUsuarioMotorista(sugestaoTrajeto.getMotorista().getIntencaoCarona().getUsuario());
		rejeicaoCarona.setUsuarioPassageiro(sugestaoTrajetoPassageiro.getIntencaoCarona().getUsuario());
		rejeicaoCarona.setSentido(RejeicaoCaronaSentido.REJEICAO_PASSAGEIRO);
		this.em.persist(rejeicaoCarona);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void visualizarSugestao(SugestaoTrajeto sugestaoTrajeto) {

		if (!sugestaoTrajeto.isVisualizada()) {
			sugestaoTrajeto.setVisualizada(true);			
		}
		
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public void substituirPassageiro(SugestaoTrajetoMotorista motorista, SugestaoTrajetoPassageiro passageiro, Usuario usuario) throws DomainSecurityException {
		
		IntencaoCarona intencaoCaronaUsuarioLogado = this.intencaoCaronaServico.findByUsuario(usuario);
		
		IntencaoCarona intencaoCaronaMotorista = motorista.getIntencaoCarona();
		
		if (!intencaoCaronaUsuarioLogado.equals(intencaoCaronaMotorista)) {
			String errorMsg = String.format("Motorista [IntencaoCarona] (id: %d) "
					+ "e usuario logado [IntencaoCarona] (id: %d) não são o mesmo", 
					intencaoCaronaMotorista.getId(), intencaoCaronaUsuarioLogado.getId());
			
			// loggar esta exception como error no controller mas enviar para o usuário apenas a mensagem "Requisicao inválida"
			// sem dar muita descrição.
			throw new DomainSecurityException(errorMsg);

		}
		// verifica se o motorista e passageiro possuem intencaoCarona ativa...
		// verifica se o motorista e passageiro estão na mesma sugestão de carona (ativa)..
		// caso algum erro mandar a exception como "RequestInvalido" no Controller (sem dar muito detalhe).
		
		SugestaoTrajeto sugestaoTrajeto = this.findSugestaoTrajetoByIntencaoCaronaMotorista(motorista.getIntencaoCarona());
		
		SugestaoTrajetoPassageiro sugestaoPassageiro = this.getPassageiro(passageiro.getIntencaoCarona(), sugestaoTrajeto);
		
		if (sugestaoPassageiro == null) {
			String errorMsg = String.format("Passageiro [SugestaoTrajetoPassageiro] (id: %d) "
					+ "e motorista [SugestaoTrajetoPassageiro] (id: %) não estão na mesma sugestao de trajeto", 
					passageiro.getId(), motorista.getId());
			
			// loggar esta exception como error no controller mas enviar para o usuário apenas a mensagem "Requisicao inválida"
			// sem dar muita descrição.
			throw new DomainSecurityException(errorMsg);
		}
		
		sugestaoPassageiro.setEstado(SugestaoTrajetoPassageiroEstado.SUBSTITUICAO);
		
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajeto findSugestaoComPassageirosEmSubstituicaoParaMotorista(IntencaoCarona intencaoCaronaMotorista) {
		
		TypedQuery<SugestaoTrajeto> query = this.em.createQuery("SELECT st FROM SugestaoTrajeto st "
				   + "INNER JOIN SugestaoTrajetoMotorista stm "
				   + "ON st.motorista = stm.id "
				   + "INNER JOIN IntencaoCarona i " 
				   + "ON stm.intencaoCarona = i.id "
				   + "INNER JOIN SugestaoTrajetoPassageiro stp "
				   + "ON st.id = stp.sugestaoTrajeto "
				   + "AND i.id = :intencaoMotoristaId "
				   + "AND stp.estado = :estadoPassageiro ", SugestaoTrajeto.class);
		
		query.setParameter("intencaoMotoristaId", intencaoCaronaMotorista.getId());
		query.setParameter("estadoPassageiro", SugestaoTrajetoPassageiroEstado.SUBSTITUICAO);
		
		List<SugestaoTrajeto> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
		
	}
	
	@Transactional(readOnly = true)
	public SugestaoTrajetoPassageiro findSugestaoTrajetoPassageiroByIntencaoCarona(IntencaoCarona intencaoCaronaPassageiro) {
		
		SugestaoTrajeto sugestaoTrajeto = this.findSugestaoTrajetoByIntencaoCaronaPassageiro(intencaoCaronaPassageiro);
		
		if (sugestaoTrajeto == null) {
			return null;
		}
		
		SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = null;
		
		for (SugestaoTrajetoPassageiro sugestaoTrajetoPassageiroAtual : sugestaoTrajeto.getPassageiros()) {
			
			if (sugestaoTrajetoPassageiroAtual.getIntencaoCarona().equals(intencaoCaronaPassageiro)) {
				sugestaoTrajetoPassageiro = sugestaoTrajetoPassageiroAtual;
				break;
			}
			
		}
		
		return sugestaoTrajetoPassageiro;
		
	}
		
}
