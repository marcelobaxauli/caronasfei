package com.caronasfei.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.Grafo;
import com.caronasfei.service.endereco.EnderecoServico;
import com.caronasfei.service.intencao.IntencaoCaronaServico;

/**
 * 
 * Faz o match de caronas para a ida para a FEI
 * 
 * @author mbax
 *
 */

@Component
@Scope("singleton")
public class MatchCaronasIda {

	@Autowired
	private IntencaoCaronaServico intencaoServico;

	@Autowired
	private EnderecoServico enderecoServico;
	
	@Autowired
	private Grafo grafo;

	public void gerarSugestoes() {
		// roda algoritmo e grava as sugestões de carona encontradas no banco...

		List<IntencaoCarona> intencaoMotoristas = this.intencaoServico.findMotoristasDisponiveis(DirecaoCarona.IDA_FEI);
		
		for (IntencaoCarona intencaoMotorista : intencaoMotoristas) {
			
			List<IntencaoCarona> intencaoPassageiros = this.intencaoServico.findAllPassageirosDisponiveis(DirecaoCarona.IDA_FEI);

			Endereco enderecoFei = this.enderecoServico.findEnderecoById(Endereco.ID_ENDERECO_FEI_SBC);

//			this.grafo.instancia(intencaoMotorista, intencaoPassageiros, enderecoFei);
			
			
		}
		
		
	}

}
