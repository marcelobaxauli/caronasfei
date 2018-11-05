<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8"/>
    <title>Caronas FEI - Modo navegação</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
      <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>

        <style>
          * {
            box-sizing: border-box;
            margin: 0;
            font-family: 'Ubuntu';
          }

          body {
            max-width: 600px;
            margin: 0 auto;
          }

          .header {
            width: 100%;
            background-color: blue;
            padding: 1.2rem 1rem;
            overflow: hidden;
          }

          .header_inner {

            width: 400px;
            margin: 0 auto;
            color: white;
            font-size: 1.15rem;
            font-weight: normal;
            float: left;
          }

          .content {
            margin-top: 3.0rem;
            margin-left: 1.0rem;
            margin-right: 1.0rem;
          }

          .content_s {
            margin: 3.0rem auto 0;
            max-width: 400px;
          }

          .content_title {
            margin-bottom: 2.5rem;
          }

          h1,
          h2,
          h3,
          h4 {

            font-weight: normal;
            margin-bottom: 0.8rem;
          }

          h1 {
            font-size: 2.8rem;
          }

          .form-group {
            margin-top: 1.5rem;
            margin-bottom: 1.5rem;
            overflow: hidden;
          }

          .form-group label {
            display: block;
            margin-bottom: 0.6rem;
          }

          .form-group input[type="email"],
          .form-group input[type="password"],
          .form-group input[type="text"],
          .form-group select {
            width: 100%;
            padding: 0.5rem;
            background-color: white;
            border: 1px solid #cacaca;
            border-radius: 0.25rem;
            font-size: 0.9rem;
            line-height: 1.8rem;
          }

          .form-group input[type="email"].error,
          .form-group input[type="password"].error,
          .form-group input[type="text"].error,
          .form-group select.error {
            width: 100%;
            padding: 0.5rem;
            background-color: white;
            border: 1px solid red;
            border-radius: 0.25rem;
            font-size: 0.9rem;
            line-height: 1.8rem;
          }

          .form-group input.error {
            border: 1px solid red;
          }

          button,
          input[type="submit"] {

            padding: 0.85rem 1.5rem;
            border-radius: 0.25rem;
            color: white;
            border: 0;
            width: 100%;
            display: block;
            margin: 0 auto 0.8rem;
            font-size: 0.9rem;
          }

          input[type="submit"] {
            background-color: blue;
          }

          button#cancelar {

            background-color: red;
          }

          input[type="text"].disabled {
            background-color: #e9ecef !important;
            cursor: not-allowed;
          }

          button.dark_gray {
            background-color: #343a40;
          }
          button.dark_gray:hover {
            background-color: #757575;
            cursor: pointer;
          }

          button.light_green {
            background-color: #009688;
            cursor: pointer;
          }
          button.light_green:hover {
            background-color: #4DB6AC;
            cursor: pointer;
          }

          .error_msg {
            margin-top: 0.5rem;
            font-size: 0.8rem;
            color: r4ed;
          }

          button.small_button {
            width: 9.5rem;
          }

          .left {
            float: left;
          }

          button.red {
            background-color: #f44336;
            cursor: pointer;
          }

          button.light_blue {
            background-color: #0facf3;
            cursor: pointer;
          }
          button:disabled {
            background-color: #d6d6d6;
            cursor: not-allowed;
          }
          /*
              BOX DE AVALIAÇÃO E CONFIRMAÇÃO DE USUÁRIO / MOTORISTA-PASSAGEIRO
            */

          .box_avaliacao_usuario {
            width: 100%;
            border: 1px solid black;
            padding: 5px;
            margin-bottom: 0.5rem;
            overflow: hidden;
            display: flex;
          }

          .box_avaliacao_usuario--foto {
            margin: 0 auto;
            padding: 10px;
            display: inline;
            float: left;
            width: 20%;
          }

          .box_avaliacao_usuario--foto--img {

            width: 100px;
            height: 100px;
            display: block;

            margin: 0 auto;
          }

          .box_avaliacao_usuario--detalhes_box {
            width: 50%;
            float: left;
            display: flex;
            align-items: center;
          }

          .box_avaliacao_usuario--detalhes,
          .box_avaliacao_usuario--status {
            font-size: 0.8rem;
          }

          .box_avaliacao_usuario--detalhe {
            padding: 3px 8px;
          }

          .box_avaliacao_usuario--status_box {
            width: 25%;
            float: left;
            display: flex;
            align-items: center;
          }

          .box_avaliacao_usuario--status {
            margin: 0 auto;
          }

          .box_avaliacao_usuario--botao_modo_navegacao {
            margin-top: 1.5rem;
          }

          .painel {
            display: flex;
          }

          .painel-info-pagina {
            float: left;
            width: 60%;
          }

          .painel-botao {
            float: right;
            width: 40%;
            display: flex;
            align-items: center;
            justify-content: flex-end;
          }


		  .icon-desc {
		  	margin-left: 8px;
		  	font-size: 18px;
		  	line-height: 28px;
		  	display: inline-block;
		  }

		  .nav i {
		  	font-size: 22px;
		  }

        </style>

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">

      </head>

      <body>

        <div class="header">

          <div class="header_inner">
            CARONAS FEI
          </div>

        </div>

        <div class="content">

          <div class="form-group nav">
          	<a href="${pageContext.request.contextPath}/trajeto/avalia">
				<i class="fas fa-arrow-left icon-large"></i><div class="icon-desc">Voltar</div>
			</a>
          </div>

          <div class="form-group">
            <div class="painel">
              <div class="painel-info-pagina">
                <h2>Modo navegação</h2>

                <h4>seu perfil: motorista</h4>
              </div>

              <div class="painel-botao">
                <button class="light_green">Ver no mapa</button>
              </div>
            </div>
          </div>

          <h2>seu horário de saída: <span id="horario_saida_motorista"></span></h2>


          <div class="form-group">
            <h3>Passageiro a buscar:</h3>
            <div id="passageiro_area"></div>
            <h3>Distância atual: <span id="distancia_atual_motorista_passageiro">...</span></h3>
          </div>

        </div>

        <script id="box_passageiro_template" type="text/x-handlebars-template">
          <div>

            <div class="box_avaliacao_usuario">

              <div class="box_avaliacao_usuario--foto">
                <img class="box_avaliacao_usuario--foto--img" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQgygYGC_4TN6JA9x_FLoZyuVqfvLOC5b6uj30Op87i51mT_2iSw"/>
              </div>

              <div class="box_avaliacao_usuario--detalhes_box">

                <div class="box_avaliacao_usuario--detalhes">

                  <div class="box_avaliacao_usuario--detalhe">
                    <span>{{nome}}</span>
                  </div>

                  <div class="box_avaliacao_usuario--detalhe">
                    <span>{{curso}}</span>
                  </div>

                  <div class="box_avaliacao_usuario--detalhe">
                    <span>{{periodo}}</span>
                  </div>

                  <div class="box_avaliacao_usuario--detalhe">
                    <span>{{enderecoPartida.rua}},
                      {{enderecoPartida.bairro}}.
                      {{enderecoPartida.cidade}}.
                      {{enderecoPartida.estado}}</span>
                  </div>

                </div>

              </div>

              <div class="box_avaliacao_usuario--status_box">
                <div id="status_area" class="box_avaliacao_usuario--status">
                  <!-- essa parte precisa ser criada dinamicamente.. -->
                  <!-- representa o estado passageiro (com botão para
                   o motorista indicar que irá buscá-lo, ou estado
                   informando se passageiro já aceitou o trajeto.) -->

                  {{#if (notEquals estado 'NAO_CONFIRMADO')}}
                    <span>{{estado}}</span>
                  {{else}}
                    <input type="hidden" name="idPassageiro" value="{{id}}"/>
                    <button class="light_green" onclick="app.aceitarPassageiro(this)">Aceitar</button>
                    <button class="red" onclick="app.rejeitarPassageiro(this)">Rejeitar</button>
                  {{/if}}

                </div>
              </div>

            </div>

          </div>

        </script>

		<script>
			
			// TODO: mover para arquivo próprio?
			
			function CoordenadasParaDistancia() {
				
			}
					
			CoordenadasParaDistancia.prototype.degreesToRadians = function(degrees) {
			  return degrees * Math.PI / 180;
			}
			
			CoordenadasParaDistancia.prototype.distancia = function(lat1, lon1, lat2, lon2) {
			  var earthRadiusKm = 6371;
			
			  var dLat = this.degreesToRadians(lat2-lat1);
			  var dLon = this.degreesToRadians(lon2-lon1);
			
			  var lat1 = this.degreesToRadians(lat1);
			  var lat2 = this.degreesToRadians(lat2);
			
			  var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			          Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
			  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
			  return earthRadiusKm * c;
			}
		
		</script>

        <script>

          function NavegacaoApp() {

            this.passageiro = null;
            this.motorista = null;
            this.coordenadasParaDistancia = new CoordenadasParaDistancia();

          }

          NavegacaoApp.prototype.init = function () {

            // register helpers pra usar como condicional na constracao do template/tela

            Handlebars.registerHelper("equals", function (input1, input2) {
              return input1 == input2;
            });

            Handlebars.registerHelper("notEquals", function (input1, input2) {
              return input1 != input2;
            });

            this.sincronizaInfo();
            this.sincronizaJob();

            // parte estática

          }

          NavegacaoApp.prototype.renderTemplate = function () {

            var source = $("#box_passageiro_template").html();
            var template = Handlebars.compile(source);
            var html = template(this.passageiro);
            $("#passageiro_area").html(html);

          }

          NavegacaoApp.prototype.atualizaPassageirosView = function () {

            $("#passageiro_area").empty();
            this.renderTemplate();

          }

          NavegacaoApp.prototype.sincronizaInfo = function () {

            var app = this;

            $.ajax({
              url: '${pageContext.request.contextPath}/trajeto/sugestao',
              type: 'GET',
              success: function (result) {

                console.log("result:");
                console.log(result);
                if (result.sucesso) {
                  var sugestaoTrajeto = result.dado;

                  if (this.passageiro == null || this.passageiro != sugestaoTrajeto.passageiros[0]) {

                    console.log("sugestaoTrajeto:");
                    console.log(sugestaoTrajeto);

                    app.passageiro = sugestaoTrajeto.passageiros[0];

                    // parte estática
                    // TODO: onde colocar isso de forma mais organizada?
                    if (app.motorista == null) {
                      app.motorista = sugestaoTrajeto.motorista;
                      $("#horario_saida_motorista").text(sugestaoTrajeto.motorista.horarioPartida);
                    }
                    app.atualizaPassageirosView();

                  }

                }

              }

            });

          }

          NavegacaoApp.prototype.contemMudanca = function (passageiroNovo) {

            var passageirosAntes = this.passageiro.passageiros;
            var passageirosDepois = passageiroNovo.passageiros;

            if (passageirosAntes.length != passageirosDepois.length) {
              return true;
            }

            for (var i = 0; i < passageirosAntes.length; i++) {

              var encontrou = false;
              for (var j = 0; j < passageirosDepois.length && !encontrou; j++) {

                if (passageirosAntes[i].id == passageirosDepois[j].id) {
                  encontrou = true;
                  if (passageirosAntes[i].estado != passageirosDepois[i].estado) {
                    return true;
                  }
                }

              }

              if (!encontrou) {
                // erro?
              }

            }

            // as duas condições
            return false;

          }

          NavegacaoApp.prototype.atualizaLocalizacao = function() {

            var app = this;

            navigator.geolocation.getCurrentPosition(function(position) {
              var coords = position.coords;
              var motoristaLatitude = coords.latitude;
              var motoristaLongitude = coords.longitude;

              // compara essas coordenadas obtidas com as
              // coordenadas do passageiro (app.passageiro)
              // atualiza span com informação
			  var passageiroLatitude = app.passageiro.enderecoPartida.latitude;
			  var passageiroLongitude = app.passageiro.enderecoPartida.longitude;
              
			  var distancia = this.app.coordenadasParaDistancia.distancia(motoristaLatitude, motoristaLongitude, passageiroLatitude, passageiroLongitude);
			  
			  $("#distancia_atual_motorista_passageiro").text(distancia.toFixed(2) + ' km');
			  
            }, function() {alert('erro, sem acesso a localizacao')})

          }

          NavegacaoApp.prototype.sincronizaJob = function () {

            setTimeout(function () {

              app.sincronizaInfo();
              app.sincronizaJob();
              app.atualizaLocalizacao();

            }, 3000);

          }

          $(document).ready(function () {

            var app = new NavegacaoApp();
            app.init();

            window.app = app;

          });
        </script>

      </body>

    </html>
