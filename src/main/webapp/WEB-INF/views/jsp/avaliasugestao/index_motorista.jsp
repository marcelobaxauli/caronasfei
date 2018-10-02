<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Caronas FEI - Avaliar sugestao</title>

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
        width: 5.5rem;
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

      .box_avaliacao_usuario--botao_finalizar {
        margin-top: 1.5rem;
      }

    </style>

  </head>

  <body>

    <div class="header">

      <div class="header_inner">
        CARONAS FEI
      </div>

    </div>

    <div class="content">

      <div class="form-group">
        <h2>Sugestao de carona</h2>

        <h4>seu perfil: motorista</h4>
      </div>

      <div class="form-group">
        <h4>Passageiros:</h4>

        <div id="passageiros">

          <!-- Conteúdo irá ser gerado dinamicamente pela aplicação.
             usando template do Handlebars -->

        </div>

      </div>

    </div>

    <script id="box_usuario_template" type="text/x-handlebars-template">
      <div>
        {{#each this}}
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
                  <span>{{local}}</span>
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

          {{#if peloMenosUmPassageiroConfirmado}}
            <div class="box_avaliacao_usuario--botao_finalizar">
              <button class="light_blue" onclick="app.finalizaSugestao()">Finalizar carona</button>
            </div>
          {{else}}
            <div class="box_avaliacao_usuario--botao_finalizar">
              <button class="light_blue" disabled>Finalizar carona</button>
            </div>
          {{/if}}
        {{/each}}
      </div>

    </script>

    <script>
      "use strict";

      function AppAvaliaSugestaoMotorista() {
        this.sugestaoTrajetoDado = null;
      }

      AppAvaliaSugestaoMotorista.prototype.init = function () {
        Handlebars.registerHelper("equals", function (input1, input2) {
          return input1 == input2;
        });

        Handlebars.registerHelper("notEquals", function (input1, input2) {
          return input1 != input2;
        });

        Handlebars.registerHelper("peloMenosUmPassageiroConfirmado", function (passageirosElement) {

          var passageiros = passageirosElement.data.root;

          var peloMenosUmaConfirmacao = false;
          for (i = 0; i < passageiros.length; i++) {
            var passageiro = passageiros[i];

            if (passageiro.estado == 'CONFIRMADO') {
              peloMenosUmaConfirmacao = true;
              break;
            }
          }

          return peloMenosUmaConfirmacao;
        })

        this.sincronizaInfo();
        this.sincronizaJob();
      }

      AppAvaliaSugestaoMotorista.prototype.renderTemplate = function () {
        var source = $("#box_usuario_template").html();
        var template = Handlebars.compile(source);
        var html = template(this.sugestaoTrajetoDado.passageiros);
        $('#passageiros').html(html);
      }

      AppAvaliaSugestaoMotorista.prototype.refreshPassageiros = function () {
        $("#passageiros").empty();
        this.renderTemplate();
      }

      AppAvaliaSugestaoMotorista.prototype.aceitarPassageiro = function (botao) {
        var idPassageiro = $(botao).siblings("input[name='idPassageiro']").val();

        var aceitarPassageiroDado = {};
        // só tem um motorista por sugestao de trajeto
        aceitarPassageiroDado.idMotorista = this.sugestaoTrajetoDado.motorista.id;
        aceitarPassageiroDado.idPassageiro = Number.parseInt(idPassageiro);

        var app = this;

        $.ajax({
          url: '${pageContext.request.contextPath}/motorista/aceitarpassageiro',
          type: 'POST',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(aceitarPassageiroDado),
          success: function (result) {

            if (result.sucesso) {
              app.sincronizaInfo();
            } else {
              // exibir erro?
            }

          }
        });
      }

      AppAvaliaSugestaoMotorista.prototype.rejeitarPassageiro = function (botao) {
        var idPassageiro = $(botao).siblings("input[name='idPassageiro']").val();

        var rejeitarPassageiroDado = {};
        // só tem um motorista por sugestao de trajeto
        confirmacaoPassageiroDado.idMotorista = this.sugestaoTrajetoDado.motorista.id;
        confirmacaoPassageiroDado.idPassageiro = Number.parseInt(idPassageiro);

        var app = this;

        $.ajax({
          url: '${pageContext.request.contextPath}/motorista/rejeitarpassageiro',
          type: 'POST',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(rejeitarPassageiroDado),
          success: function (result) {

            if (result.sucesso) {
              app.sincronizaInfo();
            } else {
              // exibir erro?
            }

          }
        });
      }

      AppAvaliaSugestaoMotorista.prototype.sincronizaInfo = function () {
        var app = this;

        $.ajax({
          url: '${pageContext.request.contextPath}/trajeto/sugestao',
          type: 'GET',
          success: function (result) {

            console.log("result:");
            console.log(result);
            if (result.sucesso) {
              var sugestaoTrajeto = result.dado;

              if (app.sugestaoTrajetoDado == null || app.contemMudanca(sugestaoTrajeto)) {
                console.log("sugestaotrajeto:");
                console.log(sugestaoTrajeto);

                app.sugestaoTrajetoDado = sugestaoTrajeto;
                app.refreshPassageiros();
              }

            }

          }
        });
      }

      AppAvaliaSugestaoMotorista.prototype.sincronizaJob = function () {

        setTimeout(function() {

          app.sincronizaInfo();
          app.sincronizaJob();

        }, 3000);

      }

      AppAvaliaSugestaoMotorista.prototype.finalizaSugestao = function () {
        // fecha intenção (com apenas os passageiros que já confirmaram).

        var payload = {}
        payload.idMotorista = idMotorista;

        var idMotorista = this.sugestaoTrajetoDado.motorista.id;

        $.ajax({
          url: '${pageContext.request.contextPath}/trajeto/finalizar',
          type: 'POST',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(payload),
          success: function (result) {

            if (result.sucesso) {

              window.location.href = result.proximaUrl;

            }

          }
        });
      }

      AppAvaliaSugestaoMotorista.prototype.contemMudanca = function (sugestaoTrajetoDadoNovo) {

        var passageirosAntes = this.sugestaoTrajetoDado.passageiros;
        var passageirosDepois = sugestaoTrajetoDadoNovo.passageiros;

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

      $(document).ready(function () {

        var app = new AppAvaliaSugestaoMotorista();
        app.init();

        window.app = app;

      });
    </script>

  </body>

</html>
