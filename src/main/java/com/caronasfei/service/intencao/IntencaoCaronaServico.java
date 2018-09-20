package com.caronasfei.service.intencao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.IntencaoCaronaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajetoUsuario.SugestaoTrajetoUsuarioEstado;
import com.caronasfei.db.usuario.Usuario;

@Service
public class IntencaoCaronaServico {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	public void cadastrarIntencao(IntencaoCarona intencao) {

		this.em.persist(intencao);

	}

	@Transactional(readOnly = true)
	public IntencaoCarona findByUsuario(Usuario usuario) {

		TypedQuery<IntencaoCarona> query = this.em
				.createQuery("SELECT i FROM IntencaoCarona i WHERE i.usuario = :usuario", IntencaoCarona.class);

		query.setParameter("usuario", usuario);
		
		List<IntencaoCarona> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		
		return resultList.get(0);
		
	}
	
	@Transactional(readOnly = true)
	public boolean temIntencaoCadastrada(Usuario usuario) {

		TypedQuery<IntencaoCarona> query = this.em
				.createQuery("SELECT i FROM IntencaoCarona i WHERE i.usuario = :usuario", IntencaoCarona.class);

		query.setParameter("usuario", usuario);
		
		List<IntencaoCarona> resultList = query.getResultList();
		
		if (resultList.isEmpty()) {
			return false;
		}
		
		return true;
		
	}

	@Transactional(readOnly = true)
	public AcaoCarona getAcaoCaronaByCodigo(String acaoCodigo) {

		TypedQuery<AcaoCarona> query = this.em.createQuery("SELECT a FROM AcaoCarona WHERE codigo = :codigo",
				AcaoCarona.class);
		query.setParameter("codigo", acaoCodigo);

		List<AcaoCarona> resultList = query.getResultList();

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList.get(0);

	}

	@Transactional(readOnly = true)
	public List<IntencaoCarona> findAllMotoristaNotInSugestaoAtiva() {

		// Ou seja, busca todos os motoristas que ainda não foram encaixados
		// em nenhuma sugestão de trajeto (ou que já recusaram a ultima sugestão).

		// Um usuário que cadastrou uma intenção para pegar carona
		// pode cadastrar uma intenção pra oferecer carona? sem cancelar a
		// primeira..? (Por tela eu consigo bloquear, agora tenho que
		// lembrar de bloquear por API também.)

		TypedQuery<IntencaoCarona> query = em.createQuery("SELECT i " 
				+ "FROM IntencaoCarona i "
				+ "WHERE i.acaoCarona = :intencao_acao " 
				+ "AND i.estado = :estado " 				
				+ "AND ( " 
				+ "		i NOT IN ( "
				+ "					SELECT stu.intencaoCarona " 
				+ "						FROM SugestaoTrajeto s "
				+ "						INNER JOIN SugestaoTrajetoUsuario stu "
				+ "						ON s.motorista = stu.id "
				+ "						WHERE stu.intencaoCarona = i.id "
				+ "						AND stu.estado != :sugestao_estado " 
				+ "		) " 
				+ "	) "
				+ ") ORDER BY i.dataCriacao ASC ", IntencaoCarona.class);

		query.setParameter("intencao_acao", AcaoCarona.OFERECER_CARONA);
		query.setParameter("estado", IntencaoCaronaEstado.ATIVA);
		query.setParameter("sugestao_estado", SugestaoTrajetoUsuarioEstado.REJEITADO);

		return query.getResultList();

	}

	@Transactional(readOnly = true)
	public List<IntencaoCarona> findAllPassageirosNotInSugestaoAtiva() {

		/*
		 * 
		 * SELECT i.id_usuario FROM intencao_carona i WHERE i.acao_carona =
		 * 'PEDIR_CARONA' AND (
		 * 
		 * i.id_usuario NOT IN (
		 * 
		 * SELECT i.id_usuario FROM intencao_carona i INNER JOIN
		 * sugestao_trajeto_usuario stu ON i.id_usuario = stu.id_usuario INNER JOIN
		 * sugestao_trajeto_passageiros stp ON stp.id_sugestao_trajeto_passageiro =
		 * stu.id_sugestao_trajeto_usuario )
		 * 
		 * ) OR (
		 * 
		 * id.id_usuario IN (
		 * 
		 * SELECT i.id_usuario FROM intencao_carona i INNER JOIN
		 * sugestao_trajeto_usuario stu ON i.id_usuario = stu.id_usuario INNER JOIN
		 * sugestao_trajeto_passageiros stp ON stp.id_sugestao_trajeto_passageiro =
		 * stu.id_sugestao_trajeto_usuario WHERE stu.estado = 'REJEITADO';
		 * 
		 * )
		 * 
		 * )
		 * 
		 */

		Query query = em.createNativeQuery("SELECT * " 
				+ "FROM intencao_carona i "
				+ "WHERE i.acao_carona = 'PEDIR_CARONA' " 
				+ "AND i.estado = 'ATIVA' " 				
				+ "AND ( " 
				+ "		i.id_intencao_carona NOT IN ( "
				+ "					SELECT i.id_intencao_carona " 
				+ "						FROM intencao_carona i  "
				+ "						INNER JOIN sugestao_trajeto_passageiro stp "
				+ "						ON i.id_intencao_carona = stp.id_intencao_carona "
				+ "						WHERE stp.estado != 'REJEITADO' "
				+ "		) " 
				+ ") ORDER BY i.data_criacao ASC ", IntencaoCarona.class);

		return query.getResultList();

	}

}
