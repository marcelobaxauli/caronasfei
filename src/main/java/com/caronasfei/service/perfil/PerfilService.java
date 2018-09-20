package com.caronasfei.service.perfil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.perfil.Perfil;
import com.caronasfei.db.perfil.PerfilPassageiro;

@Service
public class PerfilService {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRED)
	public Perfil criarPerfilPassageiro() {

		PerfilPassageiro perfilPassageiro = new PerfilPassageiro();

		Perfil perfil = new Perfil();
		perfil.setPerfilPassageiro(perfilPassageiro);

		return perfil;

	}

}
