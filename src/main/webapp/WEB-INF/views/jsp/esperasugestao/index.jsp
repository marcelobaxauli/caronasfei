<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Caronas FEI - Aguardar sugestao</title>

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

      .header_right {
        float: right;
      }

      .header_right span {
        color: white;
        cursor: pointer;
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
        width: 5.5rem;
      }

      .left {
        float: left;
      }

      button.red {
        background-color: #f44336;
        cursor: pointer;
      }



      #intencao_carona_area p {
        margin-top: 0.4rem;
        margin-bottom: 0.4rem;
      }


    </style>

  </head>

  <body>

    <div class="header">

      <div class="header_inner">
        CARONAS FEI
      </div>

      <div class="header_right">
        <span onclick="app.logoff()">logoff</a>
      </div>

    </div>

    <div class="content_s">

      <div class="form-group">
        <h2>Estamos trabalhando para te encaixar em uma carona...</h2>
        <h3>Aguarde por favor...</h3>
      </div>

      <hr/>

      <div class="form-group">
        <h4>Sua intenção de carona registrada:</h4>

        <div id="intencao_carona_area"></div>

        <div class="form-group">
          <button class="light_green left">Alterar</button>
          <button onclick="app.cancelaIntencaoCarona()" class="red left">Cancelar</button>
        </div>

      </div>

    </div>

    <script id="template_intencao_carona" type="text/x-handlebars-template">
      <div>
        <p>Tipo de ação:
          {{#if (equals acao 'pedir_carona')}}
            <span>Pedir carona</span>.
          {{else if (equals acao 'oferecer_carona')}}
            <span>Oferecer carona</span>.
          {{/if}}
        </p>
        <p>Local de partida:
          {{#if (equals opc_local_partida 'outro')}}
            <span>{{local.formatado}}</span>
          {{else}}
            <span>Fei São Bernardo do Campo</span>
          {{/if}}
        </p>
        <p>Local de destino:
          {{#if (equals opc_local_destino 'outro')}}
            <span>{{local.formatado}}</span>
          {{else}}
            <span>Fei São Bernardo do Campo</span>
          {{/if}}
        </p>
        <p>horario de partida:
          <span>{{opc_horario_partida}}</span>
        </p>
        {{#if (notEquals opc_horario_partida 'qualquer')}}
          <p>:
            <span>{{horario_partida}}</span>
          </p>
        {{/if}}
        <p>horario de chegada:
          <span>{{opc_horario_chegada}}</span>
          {{#if (notEquals opc_horario_destino 'qualquer')}}
              <span>{{horario_chegada}}</span>
            </p>
          {{else}}
            </p>
        {{/if}}
      </div>
    </script>

    <script>

      function AppEsperaSugestao() {

        this.sugestaoTrajetoInterno = null;

      }

      AppEsperaSugestao.prototype.init = function () {

        this.registraHelpers();
        this.buscaIntencaoCarona();
        this.sincronizaJob();

      }

      AppEsperaSugestao.prototype.logoff = function() {

        $.ajax({
          url: "${pageContext.request.contextPath}/logoff",
          type: "POST",
          success: function(result, status, xhr) {

            if (result.sucesso) {
              window.location = result.proximaUrl;
            } else {
              alert('usuario nao encontrado');
            }

          }
        });

      }

      AppEsperaSugestao.prototype.registraHelpers = function () {
        Handlebars.registerHelper("equals", function (input1, input2) {
          return input1 == input2;
        });

        Handlebars.registerHelper("notEquals", function (input1, input2) {
          return input1 != input2;
        });
      }

      AppEsperaSugestao.prototype.sincroniza = function () {

        $.ajax({
          url: '${pageContext.request.contextPath}/trajeto/sugestao',
          type: 'GET',
          success: function (resultado) {

            if (resultado.sucesso) {

              var sugestaoTrajeto = resultado.dado;

              if (sugestaoTrajeto != '') {
                // seria melhor pegar a nextUrl do resultado, certo?
                window.location.href = '${pageContext.request.contextPath}/trajeto/avalia';
              }

            }

          }
        });

      }

      AppEsperaSugestao.prototype.buscaIntencaoCarona = function () {

        var app = this;

        $.ajax({
          url: '${pageContext.request.contextPath}/intencaocarona',
          type: 'GET',
          success: function (resultado) {

            if (resultado.sucesso) {

              app.intencaoCaronaDado = resultado.dado;
              console.log(resultado.dado);
              app.refreshPage();

            }

          }
        });

      }

      AppEsperaSugestao.prototype.cancelaIntencaoCarona = function() {

    	  var app = this;

    	  var intencaoCaronaId = app.intencaoCaronaDado.id;

        var deletarIntencaoCaronaJson = {};
        deletarIntencaoCaronaJson.id = intencaoCaronaId;

    	  $.ajax({

    		  url: '${pageContext.request.contextPath}/intencaocarona',
    		  type: 'DELETE',
    		  dataType: 'json',
          contentType: 'application/json',
			    data: JSON.stringify(deletarIntencaoCaronaJson),
    		  success: function (resultado) {

    			  if (resultado.sucesso) {

    				  window.location.href = resultado.proximaUrl;

    			  }

    		  }

    	  });

      }

      AppEsperaSugestao.prototype.refreshPage = function () {

        $("#intencao_carona_area").empty();
        var source = $("#template_intencao_carona").html();
        var template = Handlebars.compile(source);
        var html = template(this.intencaoCaronaDado);
        $('#intencao_carona_area').html(html);

      }

      AppEsperaSugestao.prototype.sincronizaJob = function () {

        var app = this;
        setTimeout(function () {

          app.sincroniza();
          app.sincronizaJob();

        }, 3000);

      }

      $(document).ready(function () {

        var app = new AppEsperaSugestao();
        app.init();

        window.app = app;

      });
    </script>

  </body>

</html>
