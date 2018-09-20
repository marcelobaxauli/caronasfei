package com.caronasfei.service.periodo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.periodo.Periodo;

@Service
public class PeriodoServico {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Periodo> findAll() {

		TypedQuery<Periodo> query = this.em.createQuery("SELECT p FROM Periodo p", Periodo.class);

		return query.getResultList();

	}

	@Transactional(readOnly = true)
	public Periodo findByCodigo(String codigo) {

		TypedQuery<Periodo> query = this.em.createQuery("SELECT p FROM Periodo p WHERE p.codigo = :codigo", Periodo.class);
		query.setParameter("codigo", codigo);

		Periodo periodo = query.getSingleResult();

		return periodo;

	}

}
