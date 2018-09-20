package com.caronasfei.cron;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caronasfei.cron.wrapper.PassageiroDistanciaWrapper;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.intencao.endereco.Endereco.OpcaoEndereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro.SugestaoTrajetoPassageiroEstado;
import com.caronasfei.db.sugestao.SugestaoTrajetoUsuario;
import com.caronasfei.db.sugestao.SugestaoTrajetoUsuario.SugestaoTrajetoUsuarioEstado;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;
import com.caronasfei.util.CoordenadasParaDistancia;

@Component
@EnableScheduling
public class CronFormacaoTrajeto {

	@PersistenceContext(name = "CaronasFeiPersistence")
	private EntityManager em;

	@Autowired
	private IntencaoCaronaServico intencaoService;

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoServico;

	@Scheduled(fixedRate = 30000)
	public void executar() {

		// faz a magica acontecer..
		// ad-hoc de algoritmo de formação de trajeto aqui:
		// começando pelo "algoritmo" greedy mais simples (naive) só por proximidade ao
		// motorista.
		// Faz sugestão (para os motoristas) dos usuários mais próximos dele.
		// [Na verdade mais próximos do endereço (OUTRO) espeficiado pelo motorista].
		// {Esse OUTRO pode ser tanto a origem como o destino}.
		// Apenas quando o motorista confirmar que vai pegar o(s) passageiro(s) fazer a
		// sugestão na tela para esse(s) passageiro(s)..

		List<IntencaoCarona> motoristasLivres = this.intencaoService.findAllMotoristaNotInSugestaoAtiva();

		List<IntencaoCarona> passageirosLivres = this.intencaoService.findAllPassageirosNotInSugestaoAtiva();

		if (motoristasLivres.size() > 0) {
			System.out.println("debug");
		}

		for (IntencaoCarona motorista : motoristasLivres) {

			// Estrutura de dados para armezenar as melhores combinações.
			// Pra cada motorista eu crio esse Map.
			// O tamanho da lista de passageiros deve ser um valor especificado pelo usuário
			// (como o número máximo de passageiros que ele deseja transportar em seu
			// carro). Esse valor deverá ser
			// informado a cada cadastro
			// de intenção de carona.

			// "mapeia" cada motorista com cada passageiro encontrado (dentro de um wrapper
			// com a distancia do motorista para o passageiro).

			List<PassageiroDistanciaWrapper> passageiros = new ArrayList<PassageiroDistanciaWrapper>();

			for (IntencaoCarona passageiro : passageirosLivres) {

				Endereco enderecoPartidaMotorista = motorista.getEnderecoPartida();
				Endereco enderecoDestinoMotorista = motorista.getEnderecoDestino();

				Endereco enderecoPartidaPassageiro = passageiro.getEnderecoPartida();
				Endereco enderecoDestinoPassageiro = passageiro.getEnderecoDestino();

				// endereco == null significa que é FEI_SBC

				OpcaoEndereco opcaoEnderecoPartidaMotorista = null;

				if (enderecoPartidaMotorista != null) {
					opcaoEnderecoPartidaMotorista = enderecoPartidaMotorista.getOpcaoEndereco();
				} else {
					opcaoEnderecoPartidaMotorista = OpcaoEndereco.FEI_SBC;
				}

				OpcaoEndereco opcaoEnderecoDestinoMotorista = null;

				if (enderecoDestinoMotorista != null) {
					opcaoEnderecoDestinoMotorista = enderecoDestinoMotorista.getOpcaoEndereco();
				} else {
					opcaoEnderecoDestinoMotorista = OpcaoEndereco.FEI_SBC;
				}

				OpcaoEndereco opcaoEnderecoPartidaPassageiro = null;

				if (enderecoPartidaPassageiro != null) {
					opcaoEnderecoPartidaPassageiro = enderecoPartidaPassageiro.getOpcaoEndereco();
				} else {
					opcaoEnderecoPartidaPassageiro = OpcaoEndereco.FEI_SBC;
				}

				OpcaoEndereco opcaoEnderecoDestinoPassageiro = null;

				if (enderecoDestinoPassageiro != null) {
					opcaoEnderecoDestinoPassageiro = enderecoDestinoPassageiro.getOpcaoEndereco();
				} else {
					opcaoEnderecoDestinoPassageiro = OpcaoEndereco.FEI_SBC;
				}

				if (opcaoEnderecoPartidaMotorista == OpcaoEndereco.OUTRO
						&& opcaoEnderecoPartidaPassageiro == OpcaoEndereco.OUTRO
						&& opcaoEnderecoDestinoMotorista == OpcaoEndereco.FEI_SBC
						&& opcaoEnderecoDestinoPassageiro == OpcaoEndereco.FEI_SBC) {

					double novoPassageiroDistancia = CoordenadasParaDistancia.calcular(
							enderecoPartidaMotorista.getLatitude(), enderecoPartidaMotorista.getLongitude(),
							enderecoPartidaPassageiro.getLatitude(), enderecoPartidaPassageiro.getLongitude());

					// passageiros devem ser ordenados por menor distancia com relação ao
					// endereco OUTRO (target) do motorista.

					// O tamanho da lista de passageiros deve ser um valor especificado pelo usuário
					// (como o número máximo de passageiros que ele deseja transportar em seu
					// carro). Esse valor deverá ser
					// informado a cada cadastro
					// de intenção de carona.

					// agora vou setar o tamanho máximo de passageiros para 3.
					// Mas (obviamente) isso precisa ser informado pelo usuário.
					if (passageiros.size() < 3) {
						PassageiroDistanciaWrapper passageiroDistanciaWrapper = new PassageiroDistanciaWrapper();
						passageiroDistanciaWrapper.setDistancia(novoPassageiroDistancia);
						passageiroDistanciaWrapper.setPassageiro(passageiro);
						passageiros.add(passageiroDistanciaWrapper);
					} else {

						for (int i = 0; i < passageiros.size(); i++) {

							// como a lista ordenada por distancia do endereço OUTRO do motorista, eu to
							// pegando o ultimo elemento [mais distante do endereço OUTRO do motorista].
							PassageiroDistanciaWrapper passageiroMaisDistante = passageiros.get(passageiros.size() - 1);

							if (novoPassageiroDistancia < passageiroMaisDistante.getDistancia()) {
								passageiros.remove(i);

								PassageiroDistanciaWrapper passageiroDistanciaWrapper = new PassageiroDistanciaWrapper();
								passageiroDistanciaWrapper.setDistancia(novoPassageiroDistancia);
								passageiroDistanciaWrapper.setPassageiro(passageiro);

								// vai reordenar a lista, certo? não preciso fazer isso manualmente...
								passageiros.add(passageiroDistanciaWrapper);
							}

						}

					}

				} else if (opcaoEnderecoDestinoMotorista == OpcaoEndereco.OUTRO
						&& opcaoEnderecoDestinoPassageiro == OpcaoEndereco.OUTRO
						&& opcaoEnderecoPartidaMotorista == OpcaoEndereco.FEI_SBC
						&& opcaoEnderecoPartidaPassageiro == OpcaoEndereco.FEI_SBC) {

					// vou deixar esse caso para depois...

				}

			}

			// Pronto, já tenho para o motorista atual os passageiros mais próximos dele.
			// [precisava fzr uma otimização dinamica aqui também né (critérios como amigos
			// {no de amigos em comum} no facebook,
			// ranking do sistema, preferencia de sexo etc) devem entrar
			// em consideração nessa lista]. Por enquanto só estou considerando a distancia
			// como único fator.

			// vou persistir essas sugestões de carona pra essa informação ir lá pra tela do
			// motorista, e depois pras telas dos usuários.

			SugestaoTrajetoUsuario sugestaoTrajetoMotorista = new SugestaoTrajetoUsuario();
			sugestaoTrajetoMotorista.setIntencaoCarona(motorista);
			sugestaoTrajetoMotorista.setEstado(SugestaoTrajetoUsuarioEstado.NAO_CONFIRMADO);

			SugestaoTrajeto sugestaoTrajeto = new SugestaoTrajeto();
			sugestaoTrajeto.setMotorista(sugestaoTrajetoMotorista);

			// precisa verificar se passageiros.size() > 0?
			for (PassageiroDistanciaWrapper passageiroDistanciaWrapper : passageiros) {

				SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = new SugestaoTrajetoPassageiro();
				sugestaoTrajetoPassageiro.setSugestaoTrajeto(sugestaoTrajeto);
				sugestaoTrajetoPassageiro.setIntencaoCarona(passageiroDistanciaWrapper.getPassageiro());
				sugestaoTrajetoPassageiro.setEstado(SugestaoTrajetoPassageiroEstado.NAO_CONFIRMADO);
				sugestaoTrajetoPassageiro.setDistanciaParaMotorista(passageiroDistanciaWrapper.getDistancia());

				sugestaoTrajeto.addPassageiro(sugestaoTrajetoPassageiro);

			}

			this.sugestaoTrajetoServico.criarSugestaoTrajeto(sugestaoTrajeto);

		}

	}

}
