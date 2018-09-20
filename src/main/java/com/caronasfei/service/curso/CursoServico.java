package com.caronasfei.service.curso;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.curso.Curso;

@Service
public class CursoServico {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Curso> findAll() {

		TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c", Curso.class);

		return query.getResultList();

	}

	@Transactional(readOnly = true)
	public Curso findByCodigo(String codigo) {

		TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c WHERE c.codigo = :codigo", Curso.class);
		query.setParameter("codigo", codigo);
		
		Curso curso = query.getSingleResult();

		return curso;

	}

}
