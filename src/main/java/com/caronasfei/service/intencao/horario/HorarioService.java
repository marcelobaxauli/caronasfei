package com.caronasfei.service.intencao.horario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.horario.Horario;
import com.caronasfei.db.intencao.horario.HorarioTipo;

@Service
public class HorarioService {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	public void salvar(Horario horario) {
		this.em.persist(horario);
	}

	// Mudao horario_tipo pra opcao_horario
	@Transactional(readOnly = true)
	public HorarioTipo loadHorarioTipoByCodigo(String codigo) {

		TypedQuery<HorarioTipo> query = em.createQuery("SELECT ht FROM HorarioTipo ht where codigo = :codigo",
				HorarioTipo.class);

		query.setParameter("codigo", codigo);

		List<HorarioTipo> horarioTipoResultList = query.getResultList();

		if (horarioTipoResultList.isEmpty()) {
			return null;
		}

		return horarioTipoResultList.get(0);
	}

}
