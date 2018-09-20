package com.caronasfei.assembler.intencao.endereco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.intencao.endereco.Endereco.OpcaoEndereco;
import com.caronasfei.dto.intencao.endereco.EnderecoDTO;

@Component
@Scope("prototype")
public class EnderecoAssembler {

	public Endereco toEndereco(EnderecoDTO enderecoDTO, String opcaoEndereco) {

		// busca dados do endereco numa api aí (Google Maps por exemplo).
		// grava detalhes (cep, cidade, rua, lat/long) do resultado na tabela de
		// endereco...

		Endereco endereco = new Endereco();
		endereco.setOpcaoEndereco(OpcaoEndereco.fromCodigo(opcaoEndereco));
		endereco.setRua(enderecoDTO.getRua());
		endereco.setNumero(enderecoDTO.getNumero());
		endereco.setBairro(enderecoDTO.getBairro());
		endereco.setCep(enderecoDTO.getCep());
		// verificar se o endereço é no estado de SP msm...
		endereco.setEstado("SP");
		endereco.setCidade(enderecoDTO.getCidade());
		endereco.setLatitude(enderecoDTO.getLatitude());
		endereco.setLongitude(enderecoDTO.getLongitude());

		return endereco;

	}

	public EnderecoDTO toEnderecoDTO(Endereco endereco) {

		EnderecoDTO enderecoDTO = new EnderecoDTO();
		enderecoDTO.setRua(endereco.getRua());
		enderecoDTO.setNumero(endereco.getNumero());
		enderecoDTO.setBairro(endereco.getBairro());
		enderecoDTO.setCep(endereco.getCep());
		enderecoDTO.setCidade(endereco.getCidade());
		enderecoDTO.setLatitude(endereco.getLatitude());
		enderecoDTO.setLongitude(endereco.getLongitude());

		return enderecoDTO;

	}

}
