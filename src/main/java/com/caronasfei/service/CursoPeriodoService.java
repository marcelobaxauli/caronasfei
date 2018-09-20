package com.caronasfei.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.caronasfei.db.curso.Curso;
import com.caronasfei.db.curso.CursoPeriodo;
import com.caronasfei.db.periodo.Periodo;

@Service
public class CursoPeriodoService {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	public CursoPeriodo findByCursoAndPeriodo(Curso curso, Periodo periodo) {

		TypedQuery<CursoPeriodo> query = this.em.createQuery(
				"SELECT cp FROM CursoPeriodo cp WHERE cp.curso = :curso AND cp.periodo = :periodo", CursoPeriodo.class);
		query.setParameter("curso", curso);
		query.setParameter("periodo", periodo);
		
		CursoPeriodo cursoPeriodo = query.getSingleResult();
		
		return cursoPeriodo;

	}

}
