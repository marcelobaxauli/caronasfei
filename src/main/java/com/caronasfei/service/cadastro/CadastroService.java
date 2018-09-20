package com.caronasfei.service.cadastro;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.cadastro.Cadastro;
import com.caronasfei.db.cadastro.CadastroEtapa;
import com.caronasfei.db.cadastro.CadastroEtapa.CadastroEtapaCodigo;

@Service
public class CadastroService {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRED)
	public Cadastro criarCadastro() {

		CadastroEtapa proximaEtapa = this.getEtapa(CadastroEtapa.CadastroEtapaCodigo.AUTENTICACAO_FEI);

		Cadastro cadastro = new Cadastro();
		cadastro.setCadastroEtapa(proximaEtapa);

		return cadastro;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void setEtapa(Cadastro cadastro, CadastroEtapaCodigo cadastroEtapaCodigo) {

		// próxima etapa
		TypedQuery<CadastroEtapa> query = this.entityManager.createQuery(
				"SELECT c FROM CadastroEtapa c where c.codigo = :cadastro_etapa_codigo", CadastroEtapa.class);

		query.setParameter("cadastro_etapa_codigo", cadastroEtapaCodigo.getCodigo());

		CadastroEtapa cadastroEtapa = query.getSingleResult();

		cadastro.setCadastroEtapa(cadastroEtapa);

	}

	@Transactional(readOnly = true)
	public CadastroEtapa getEtapa(CadastroEtapaCodigo cadastroEtapaCodigo) {

		// próxima etapa
		TypedQuery<CadastroEtapa> query = this.entityManager.createQuery(
				"SELECT c FROM CadastroEtapa c where c.codigo = :cadastro_etapa_codigo", CadastroEtapa.class);

		query.setParameter("cadastro_etapa_codigo", cadastroEtapaCodigo.getCodigo());

		CadastroEtapa cadastroEtapa = query.getSingleResult();

		return cadastroEtapa;

	}

}
