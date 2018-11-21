package com.caronasfei.web.controller.avaliasugestao.mapa.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapaSugestaoTrajetoJSPController {

	@GetMapping("/trajeto/sugestao/mapa")
	public String getMapaPage() {
		return "avaliasugestao/mapa_geral/index_mapa_geral";
	}

}
