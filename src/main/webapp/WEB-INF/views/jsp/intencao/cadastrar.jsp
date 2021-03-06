<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>

  <head>
  	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cadastrar intenção de carona</title>
    <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

    <!-- jQuery Modal -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />

<!-- SERVIDOR -->
    <script src="${pageContext.request.contextPath}/resources/wickedpicker/wickedpicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/wickedpicker/wickedpicker.min.css" />


<!-- LOCAL:
    <link rel="stylesheet" type="text/css" href="wickedpicker.min.css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.12/css/all.css" integrity="sha384-G0fIWCsCzJIMAVNQPfjH08cyYaUtMwjJwqiRKxxE/rx96Uroj1BtIQ6MLJuheaO9" crossorigin="anonymous">
-->

      <script src="wickedpicker.min.js"></script>

          <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.1/dist/leaflet.css"
            integrity="sha512-Rksm5RenBEKSKFjgI3a41vrjkw4EVPlJ3+OiI65vTjIdo9brlAacEuKOiQ5OFh7cOI1bkDwLqdLw3Zg0cRJAAQ=="
            crossorigin=""/>

          <script src="https://unpkg.com/leaflet@1.3.1/dist/leaflet.js"
            integrity="sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw=="
            crossorigin=""></script>


    <style>

      * {
        box-sizing: border-box;
        margin: 0;
        font-family: 'Ubuntu';
      }

      body {
        max-width: 600px;
        margin: 0px auto;
      }

		 .header {
			 width: 100%;
			 background-color: blue;
       padding: 1.2rem 1rem;
       overflow: hidden;
		 }

		 .header_inner {

			 width: 400px;
			 margin: 0px auto;
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

     h1 {
       font-size: 1.8rem;
       font-weight: normal;
       margin-bottom: 0.8rem;
     }

     .form-group {
       margin-top: 1.5rem;
       margin-bottom: 1.5rem;
       overflow: hidden;
     }

    input[type="submit"],
    button {

      padding: 0.85rem 1.5rem;
      border-radius: 0.25rem;
      color: white;
      border: 0px;
      width: 100%;
      display: block;
      margin: 0px auto;
      margin-bottom: 0.5rem;

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


    /* especifico: */
    #local {
      width: 93%;
      display: inline;
      float: left;
      cursor: pointer;
    }

    #local_botao {
      display: inline-block;
      line-height: 0.2rem;
      font-size: 2.3rem;
      text-align: right;
      padding: 0;
      margin: 0;
      float: right;
    }

    #local_area {
      overflow: hidden;
      cursor: pointer;
    }









    /* especifico modal endereco: */


            .form-group-inline {
              float: left;
              margin-right: 0.5rem;
              margin-bottom: 0rem;
            }


            button.light_green {
              background-color: #009688;
              cursor: pointer;
            }

            button.light_green:hover {
              background-color: #4DB6AC;
              cursor: pointer;
            }

            button.red {
              background-color: #f44336;
              cursor: pointer;
            }

            /* ESPECÍFICO */

            .s-65 {
              width: 65%;
            }

            .s-15 {
              width: 15%;
            }

            .form-group-s {
              display: block;
              margin-top: 1.0rem;
              margin-bottom: 0.5rem;
              overflow: hidden;
            }

            #map {
              height: 260px;
              width: 100%;
            }


                     .form-group label,
                     .form-group-inline label {
                       display: block;
                       margin-bottom: 0.6rem;
                     }

                     .form-group input[type="text"],
                     .form-group input[type="number"],
                     .form-group-inline input[type="text"],
                     .form-group select,
                     .form-group-inline select {
                       width: 100%;
                       padding: 0.5rem;
                       background-color: white;
                       border: 1px solid #cacaca;
                       border-radius: 0.25rem;
                       font-size: 0.9rem;
                    }



      #endereco_modal {
        display: none;
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

    <h1>Cadastrar Intenção</h1>
    <p>Informe seu destino, origem, horário de partida e se a intenção de pegar ou oferecer carona.</p>

    <div class="form-group">
      <label>Ação:</label>
      <select name="acao">
        <option value="pedir_carona">Pedir Carona</option>
        <option value="oferecer_carona">Oferecer Carona</option>
      </select>
    </div>

    <div class="form-group">
      <label>Partida:</label>
      <select name="opc_local_partida">
        <option value="outro">Outro</option>
        <option value="fei">FEI SBC</option>
      </select>
    </div>

    <div class="form-group">
      <label>Destino:</label>
      <select name="opc_local_destino">
        <option value="fei">FEI SBC</option>
        <option value="outro">Outro</option>
      </select>
    </div>

    <div class="form-group">
      <label id="desc_local">Local de partida:</label>
      <div id="local_area">
        <input id="local" type="text" disabled />
        <span id="local_botao"><i class="fas fa-external-link-square-alt"></i> </span>
      </div>
    </div>

    <div class="form-group">
      <label>Opção de partida:</label>
      <select name="opc_horario_partida">
        <option value="horario_fixo">Horário Fixo</option>
        <option value="qualquer">Qualquer horário</option>
        <option value="de">De</option>
        <option value="ate">AtÃ©</option>
      </select>
    </div>

    <div class="form-group">
      <label>Horário de partida:</label>
      <input id="horario_input_partida" type="text" />
    </div>

    <div class="form-group">
      <label>Opção de chegada:</label>
      <select name="opc_horario_chegada">
        <option value="horario_fixo">Horário Fixo</option>
        <option value="qualquer">Qualquer horário</option>
        <option value="de">De</option>
        <option value="ate">AtÃ©</option>
      </select>
    </div>

    <div class="form-group">
      <label>Horário de chegada:</label>
      <input id="horario_input_chegada" type="text" />
    </div>

    <div class="form-group">
      <input id="cadastrar" type="submit" value="Cadastrar" />
      <button id="cancelar">Cancelar</button>
    </div>

    <!-- MODAL: -->

    <div id="endereco_modal">

      <h1 id="titulo"></h1>

      <div class="form-group-s">
        <div class="form-group-inline s-65">
          <label>Rua:</label>
          <input id="rua" type="text" />
        </div>

        <div class="form-group-inline s-15">
          <label>Número:</label>
          <input id="numero" type="text" />
        </div>

        <div class="form-group-inline s-15">
          <label>Cidade:</label>
          <input id="cidade" type="text" />
        </div>
      </div>

      <div class="form-group-s">
        <button id="pesquisar" class="light_green">Pesquisar</button>
      </div>

      <div id="map">
      </div>

    </div>

  </div>

  <script>

    $(document).ready(function() {

        // limpa coordenadas da ultima pesquisa
        window.localStorage.clear();

        $("select[name='acao']").change(function() {

          var acao = $("select[name='acao'] option:selected").val();

          if (acao == 'oferecer_carona') {

            var formGroupDetour = $("<div class='form-group'>");
            var detourLabel = $("<label>Detour (em minutos):</label>")
            var inputDetour = $("<input id='detour' type='number'/>");

            formGroupDetour.append(detourLabel);
            formGroupDetour.append(inputDetour);

            formGroupDetour.insertAfter($("select[name='acao']").parent("div"));

            var formGroupPassageiros = $("<div class='form-group'>");
            var passageirosLabel = $("<label>Capacidade do veículo (em número de passageiros):</label>")
            var inputPassageiros = $("<input id='passageiros' type='number'/>");

            formGroupPassageiros.append(passageirosLabel);
            formGroupPassageiros.append(inputPassageiros);

            formGroupPassageiros.insertAfter($("select[name='acao']").parent("div"));

          } else {
              $("input#detour").parent("div").remove();
              $("input#passageiros").parent("div").remove();
          }

        });

        $("#local_area").click(function() {

          //window.location.href = "escolhaendereco/index.html";

          $("#endereco_modal").modal();

        })

        window.localStorage.setItem('local', 'partida');

        $("select[name='opc_local_partida']").change(function() {

          var opc_partida = $("select[name='opc_local_partida'] option:selected").val();

          if (opc_partida == 'outro') {

            window.localStorage.setItem('local', 'partida');

            $("select[name='opc_local_destino'] > option").each(function() {
              $(this).remove();
            });

            var feiOption = $("<option value='fei'>FEI SBC</option>");
            var outroOption = $("<option value='outro'>Outro</option>");

            $("select[name='opc_local_destino']").append(feiOption);
            $("select[name='opc_local_destino']").append(outroOption);

            $("#desc_local").text("");
            $("#desc_local").text("Local de partida:");

          }

          if (opc_partida == 'fei') {

            window.localStorage.setItem('local', 'destino');

            $("select[name='opc_local_destino'] > option").each(function() {
              $(this).remove();
            });

            var feiOption = $("<option value='fei'>FEI SBC</option>");
            var outroOption = $("<option value='outro'>Outro</option>");

            $("select[name='opc_local_destino']").append(outroOption);
            $("select[name='opc_local_destino']").append(feiOption);

            $("#desc_local").text("");
            $("#desc_local").text("Local de destino:");

          }

        });


        $("select[name='opc_local_destino']").change(function() {

          var opc_destino = $("select[name='opc_local_destino'] option:selected").val();

          if (opc_destino == 'outro') {

            window.localStorage.setItem('local', 'destino');

            $("select[name='opc_local_partida'] > option").each(function() {
              $(this).remove();
            });

            var feiOption = $("<option value='fei'>FEI SBC</option>");
            var outroOption = $("<option value='outro'>Outro</option>");

            $("select[name='opc_local_partida']").append(feiOption);
            $("select[name='opc_local_partida']").append(outroOption);

            $("#desc_local").text("");
            $("#desc_local").text("Local de destino:");

          }

          if (opc_destino == 'fei') {

            window.localStorage.setItem('local', 'partida');

            $("select[name='opc_local_partida'] > option").each(function() {
              $(this).remove();
            });

            var feiOption = $("<option value='fei'>FEI SBC</option>");
            var outroOption = $("<option value='outro'>Outro</option>");

            $("select[name='opc_local_partida']").append(outroOption);
            $("select[name='opc_local_partida']").append(feiOption);

            $("#desc_local").text("");
            $("#desc_local").text("Local de partida:");

          }

    });


    $("select[name='opc_horario_partida']").change(function() {

      var opcao_partida = $("select[name='opc_horario_partida'] option:selected").val();

      var parent = $("#horario_input_partida").parent();
      $("#horario_input_partida").remove();

      if (opcao_partida == 'qualquer') {

        var inputHorarioPartidaDisabled = $('<input id="horario_input_partida" type="text" class="disabled" disabled />');
        parent.append(inputHorarioPartidaDisabled);

      } else {

        var inputHorarioPartidaDisabled = $('<input id="horario_input_partida" type="text" />');
        parent.append(inputHorarioPartidaDisabled);
        $("#horario_input_partida").wickedpicker();
        $("#horario_input_partida").val("");

      }

    });


      $("select[name='opc_horario_chegada']").change(function() {

        var opcao_partida = $("select[name='opc_horario_chegada'] option:selected").val();

        var parent = $("#horario_input_chegada").parent();
        $("#horario_input_chegada").remove();

        if (opcao_partida == 'qualquer') {

          var inputHorarioPartidaDisabled = $('<input id="horario_input_chegada" type="text" class="disabled" disabled />');
          parent.append(inputHorarioPartidaDisabled);

        } else {

          var inputHorarioPartidaDisabled = $('<input id="horario_input_chegada" type="text" />');
          parent.append(inputHorarioPartidaDisabled);
          $("#horario_input_chegada").wickedpicker();
          $("#horario_input_chegada").val("");

        }

      });

    $("#horario_input_partida").wickedpicker();

    $("#horario_input_chegada").wickedpicker();

    $("#cadastrar").click(function() {

      var acao_in = $("select[name='acao'] option:selected").val();
      var opc_local_partida_in = $("select[name='opc_local_partida'] option:selected").val();
      var opc_local_destino_in = $("select[name='opc_local_destino'] option:selected").val();

      if (acao_in == 'oferecer_carona') {
        var detourValor = $("input#detour").val();
        var passageiros = $("input#passageiros").val();
      }

      var endereco_informado = window.localStorage.getItem('endereco_informado');

      if (endereco_informado == '' || !endereco_informado) {

        if ($("select[name='opc_local_partida'] option:selected").val() == 'outro') {
          alert('Informe o local de partida');
        } else {
          alert('Informe o local de destino');
        }

        return;
      }

      var endereco = {};
      endereco.rua = window.localStorage.getItem('rua');
      endereco.numero = window.localStorage.getItem('numero');
      endereco.bairro = window.localStorage.getItem('bairro');
      endereco.cidade = window.localStorage.getItem('cidade');
      endereco.cep = window.localStorage.getItem('cep');
      endereco.latitude = window.localStorage.getItem('latitude');
      endereco.longitude = window.localStorage.getItem('longitude');

      var local_in = endereco;
      var opc_horario_partida_in = $("select[name='opc_horario_partida'] option:selected").val();
      var opc_horario_chegada_in = $("select[name='opc_horario_chegada'] option:selected").val();
      var horario_partida_in;
      var horario_chegada_in;

      if (opc_horario_partida_in != "qualquer") {
        horario_partida_in = $("#horario_input_partida").val();
      } else {
        horario_partida_in = null;
      }

      if (opc_horario_chegada_in != "qualquer") {
        horario_chegada_in = $("#horario_input_chegada").val();
      } else {
        horario_chegada_in = null;
      }

      var intencao = {
        acao: acao_in,
        opc_local_partida: opc_local_partida_in,
        opc_local_destino: opc_local_destino_in,
        local: local_in,
        opc_horario_partida: opc_horario_partida_in,
        opc_horario_chegada: opc_horario_chegada_in,
        horario_partida: horario_partida_in,
        horario_chegada: horario_chegada_in
      }

      if (acao_in == 'oferecer_carona') {
        intencao.numero_assentos = passageiros;
        intencao.detour = detourValor;
      }

      console.log(intencao);

      $.ajax({
        url: "${pageContext.request.contextPath}/intencaocarona",
        type: 'POST',
        data: JSON.stringify(intencao),
        contentType: 'application/json; charset=utf-8',
        success: function(result, status, xhr) {

          var sucesso = result.sucesso;

          if (sucesso) {
            window.location.href = result.proximaUrl;
          } else {
            alert(result.mensagem);
          }

        }
      })

    });

    $("#cancelar").click(function() {

    })









    // modal endereco:



            $("#pesquisar").click(function() {

              var rua = $("#rua").val();
              var numero = $("#numero").val();
              var cidade = $("#cidade").val();

              var ok = true;

              if (rua == '') {
                alert('Informe a rua');
                ok = false;
              }

              if (numero == '') {
                alert('Informa o numero');
                ok = false;
              }

              if (cidade == '') {
                alert('Informe a cidade');
                ok = false;
              }

              if (ok) {

                var endereco = numero + '+' + rua + '+' + cidade;

                var apiUrl = 'https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/findAddressCandidates?f=json&singleLine=' + endereco;

                $.ajax({
                  url: apiUrl,
                  type: 'GET',
                  success: function(result) {

//                     var json = JSON.parse(result);

					var json = result;
                    console.log(json);

                    var location = json.candidates[0].location;

                    var endereco = json.candidates[0].address;

                    var enderecoPartes = endereco.split(',');

                    var cep = enderecoPartes[3].trim();

                    var bairro = enderecoPartes[1].trim();

                    var map = L.map('map').setView([location.y, location.x], 10);

                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    }).addTo(map);

                    var marker = L.marker([location.y, location.x]).addTo(map);

                    var latLngs = [ marker.getLatLng() ];
                    var markerBounds = L.latLngBounds(latLngs);
                    map.fitBounds(markerBounds);

                    var formGroup = $("<div class='form-group-s'>")
                    var button = $("<button id='confirmar' class='red'>Confirmar</button>")
                    button.click(function() {

                      console.log('confirmar:');
                      console.log(location);

                      var rua = $("#rua").val();
                      var numero = $("#numero").val();
                      var cidade = $("#cidade").val();

                      window.localStorage.setItem("rua", rua);
                      window.localStorage.setItem("numero", numero);
                      window.localStorage.setItem("bairro", bairro);
                      window.localStorage.setItem("cidade", cidade);
                      window.localStorage.setItem("cep", cep);
                      window.localStorage.setItem('latitude', location.y);
                      window.localStorage.setItem('longitude', location.x);
                      window.localStorage.setItem('endereco_informado', true);

                      $("#local").val(endereco);

                      $.modal.close();

                    });

                    formGroup.append(button);

                    formGroup.insertAfter($("#map"));

                  }
                });

              }

            });










  });

  </script>

  </body>

</html>
