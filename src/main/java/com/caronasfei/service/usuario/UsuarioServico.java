package com.caronasfei.service.usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.cadastro.Cadastro;
import com.caronasfei.db.cadastro.CadastroEtapa;
import com.caronasfei.db.perfil.Perfil;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.service.cadastro.CadastroService;
import com.caronasfei.service.perfil.PerfilService;

@Service
public class UsuarioServico {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Autowired
	private CadastroService cadastroService;

	@Autowired
	private PerfilService perfilService;

	@Transactional(readOnly = true)
	public Usuario login(String email, String senha) {

		TypedQuery<Usuario> query = em
				.createQuery("SELECT u FROM Usuario u where u.email = :email AND u.senha = :senha", Usuario.class);
		query.setParameter("email", email);
		query.setParameter("senha", senha);

		List<Usuario> usuarios = query.getResultList();

		if (usuarios.isEmpty()) {
			return null;
		}

		return usuarios.get(0);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void criarUsuario(Usuario novoUsuario) {

		// checar se email existe.. (lançar exceção se existir)
		// email deve ser único no sistema
		// (criar constraint unique em banco)
		// checar se nome do curso tá certo
		// checar se período do curso da certo
		// checar se cidade existe...

		if (this.emailExiste(novoUsuario.getEmail())) {
			throw new RuntimeException("Uma conta com este email já foi registrada");
		}

		// ao se cadastrar já "ganha" o perfil de passageiro
		Perfil perfilPassageiro = this.perfilService.criarPerfilPassageiro();

		// cria referencia a etapa atual de cadastro
		Cadastro cadastro = this.cadastroService.criarCadastro();

		novoUsuario.setCadastro(cadastro);
		novoUsuario.setPerfil(perfilPassageiro);

		em.persist(novoUsuario);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Usuario atualizaComoAutenticadoFei(Usuario usuario) {

		usuario = this.em.merge(usuario);
		usuario.setAutenticadoFei(true);

		Cadastro cadastro = usuario.getCadastro();

		// checar por null?

		// fechar cadastro, usuario ja ganhou perfil de passageiro ao se cadastrar
		// na etapa anterior...
		this.cadastroService.setEtapa(cadastro, CadastroEtapa.CadastroEtapaCodigo.COMPLETO);

		return usuario;

	}

	private boolean emailExiste(String email) {

		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
		query.setParameter("email", email);

		List<Usuario> usuarios = query.getResultList();

		if (usuarios.isEmpty()) {
			return false;
		}

		return true;

	}

}
