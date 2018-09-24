package com.caronasfei.service.endereco;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.endereco.Endereco;

@Service
public class EnderecoServico {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager entityManager;
	
	@Transactional(readOnly = true)
	public Endereco findEnderecoById(int id) {
		
		Endereco endereco = entityManager.find(Endereco.class, id);
		
		return endereco;
		
	}
	
}
