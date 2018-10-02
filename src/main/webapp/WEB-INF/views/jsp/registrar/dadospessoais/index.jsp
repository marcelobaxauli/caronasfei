<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Caronas FEI - Dados pessoais</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/jquery.mask.js"></script>

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

         .content_s {
           margin: 0px auto;
           margin-top: 3.0rem;
           max-width: 400px;
         }

         .content_title {
           margin-bottom: 2.5rem;
         }

         h1, h2, h3, h4 {

           font-weight: normal;
           margin-bottom: 0.8rem;

         }

         h1 {
           font-size: 2.8rem;
         }

         .form-group {
           margin-top: 1.5rem;
           margin-bottom: 1.5rem;
         }

         .form-group label {
           display: block;
           margin-bottom: 0.6rem;
         }

         .form-group input[type="text"],
         .form-group input[type="password"],
         .form-group input[type="email"],
         .form-group select {
           width: 100%;
           padding: 0.5rem;
           background-color: white;
           border: 1px solid #cacaca;
           border-radius: 0.25rem;
           font-size: 0.9rem;
           line-height: 1.8rem;
        }

        .form-group input[type="text"].error,
        .form-group input[type="password"].error,
        .form-group input[type="email"].error,
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

        input[type="submit"],
        button {

          padding: 0.85rem 1.5rem;
          border-radius: 0.25rem;
          color: white;
          border: 0px;
          width: 100%;
          display: block;
          margin: 0px auto;
          margin-bottom: 0.8rem;
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
          color: red;
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

      <div class="content_title">
        <h1>Cadastro 1/3</h1>
        <h2>Dados pessoais</h2>
      </div>

      <div class="form-group">
        <label>Foto de profile:</label>
        <input id="foto" type="file" placeholder="Digite seu nome aqui" accept="image/*" />
      </div>

      <div class="form-group">
        <label>Nome:</label>
        <input id="nome" type="text" placeholder="Informe seu nome" />
      </div>


      <div class="form-group">
        <label>Email:</label>
        <input id="email" type="email" placeholder="Informe seu email" />
      </div>

      <div class="form-group">
        <label>Número de celular:</label>
        <input id="celular" type="text" placeholder="Informe seu número de celular" />
      </div>

      <div class="form-group">
        <label>Curso:</label>

        <select name="curso" class="form-control" id="curso_select">

        </select>
      </div>

      <div class="form-group">
        <label>Horário de aula:</label>

        <select name="periodo" class="form-control" id="periodo_select">
        </select>

      </div>

      <div class="form-group">
        <label>Senha:</label>
        <input id="senha" type="password" placeholder="Informe seu número de celular" />
      </div>


      <div class="form-group">
        <label>Confirmar senha:</label>
        <input id="confirmacao_senha" type="password" placeholder="Informe seu número de celular" />
      </div>

      <div class="form-group">
        <button id="enviar" class="light_green">Enviar</button>
        <button class="dark_gray">Cancelar</button>
      </div>

    </div>

    <script>

      $(document).ready(function() {

        $.ajax({
          url: '${pageContext.request.contextPath}/cursos',
          type: 'GET',
          contentType: 'application/json; charset=utf-8',
          success: function(result) {

    			var cursos = result.cursos;

    			cursos.forEach(function(curso) {

		        var option = $('<option value="' + curso.codigo + '">' + curso.nome + '</option>');

		        $("select[name='curso']").append(option);

    			});


          }
        });


       $.ajax({
         url: '${pageContext.request.contextPath}/periodos',
         type: 'GET',
         contentType: 'application/json; charset=utf-8',
         success: function(result) {

        	var periodos = result.periodos;

        	periodos.forEach(function(periodo) {

		        var option = $('<option value="' + periodo.codigo + '">' + periodo.nome + '</option>');

		        $("select[name='periodo']").append(option);

  			  });


         }
      });

        $("#celular").mask('(00) 00000-0000');

        $("#enviar").click(function() {

          var foto_in = $("#foto").val();
          var nome_in = $("#nome").val();
          var email_in = $("#email").val();
          var celular_in = $("#celular").val();
          var curso_in = $("select[name='curso'] option:selected").val();
          var horario_in = $("#select[name='periodo'] option:selected").val();
          var senha_in = $("#senha").val();
          var senha_confirmacao_in = $("#confirmacao_senha").val();

          if (foto_in == '') {

            if (!$("#foto").hasClass("error")) {
              $("#foto").addClass("error");

              var errorMsg = $('<div id="error_msg_foto" class="error_msg">Insira um foto.</div>');

              errorMsg.insertAfter($("#foto"));

            }

          } else {

            $("#foto").removeClass("error");

            $("#error_msg_foto").remove();

          }

          if (nome_in == '') {

            if (!$("#nome").hasClass("error")) {
              $("#nome").addClass("error");

              var errorMsg = $('<div id="error_msg_nome" class="error_msg">Informe seu nome.</div>"');

              errorMsg.insertAfter($("#nome"));
            }

          } else {

            if ($("#nome").hasClass("error")) {
              $("#nome").removeClass("error");

              $("#error_msg_nome").remove();
            }


          }

          var reg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
          if(reg.test(email_in) == false){

            if (!$("#email").hasClass("error")) {
              $("#email").addClass("error");

              var errorMsg = $('<div id="error_msg_email" class="error_msg">Email inválido.</div>');

              errorMsg.insertAfter($("#email"));

            }

          } else {

            if ($("#email").hasClass("error")) {
              $("#email").removeClass("error");

              $("#error_msg_email").remove();

            }


          }

          if (celular_in.length != 15) {

            if (!$("#celular").hasClass("error")) {

              $("#celular").addClass("error");

              var errorMsg = $('<div id="error_msg_celular" class="error_msg">Número de celular inválido.</div>');

              errorMsg.insertAfter($("#celular"));

            }

          } else {

            if ($("#celular").hasClass("error")) {

              $("#celular").removeClass("error");

              $("#error_msg_celular").remove();

            }

          }

          if (senha_in == '') {

            if (!$("#senha").hasClass("error")) {

              $("#senha").addClass("error");

            } else {

              if ($("#error_msg_senha_nao_confere").length > 0) {

                $("#error_msg_senha_nao_confere").remove();

              }

            }

            if ($("#error_msg_senha").length == 0) {
              var errorMsg = $('<div id="error_msg_senha" class="error_msg">Informe uma senha.</div>');

              errorMsg.insertAfter($("#senha"));
            }

          } else {

            if ($("#senha").hasClass("error")) {

              $("#senha").removeClass("erros");

              $("#error_msg_senha").remove();

            }

          }

          if (senha_confirmacao_in == '') {

            if (!$("#confirmacao_senha").hasClass("error")) {

              $("#confirmacao_senha").addClass("error");

            } else {

              if ($("#error_msg_senha_confirmacao_nao_confere").length > 0) {

                $("#error_msg_senha_confirmacao_nao_confere").remove();

              }

            }

            if ($("#error_msg_confirmacao_senha").length == 0) {
              var errorMsg = $('<div id="error_msg_confirmacao_senha" class="error_msg">Informe a confirmação da senha.</div>');
              errorMsg.insertAfter($("#confirmacao_senha"));
            }



          } else {

            if ($("#confirmacao_senha").hasClass("error")) {

              $("#confirmacao_senha").removeClass("erros");

              $("#error_msg_senha").remove();

            }

          }

          if (senha_in != '' && senha_confirmacao_in != '' && senha_in != senha_confirmacao_in) {

            if ($("#senha").hasClass("error")) {

              $("#error_msg_senha").remove();

            } else {
              $("#senha").addClass("error");

            }

            if ($("#confirmacao_senha").hasClass("error")) {

              $("#error_msg_confirmacao_senha").remove();

            } else {
              $("#senha").addClass("error");

            }

            if ($("#error_msg_senha_nao_confere").length == 0) {
              var errorMsg = $('<div id="error_msg_senha_nao_confere" class="error_msg">Senha e confirmação não conferem.</div>');
              errorMsg.insertAfter($("#senha"));
            }

            if ($("#error_msg_senha_confirmacao_nao_confere").length == 0) {
              var errorMsg = $('<div id="error_msg_senha_confirmacao_nao_confere" class="error_msg">Senha e confirmação não conferem.</div>');
              errorMsg.insertAfter($("#confirmacao_senha"));
            }

          }











          				var formNome = $('#nome').val();
          				var formEmail = $('#email').val();
          				var formCelular = $('#celular').val();
          				var formCurso = $('select[name="curso"] option:selected').val();
          				var formPeriodo = $('select[name="periodo"] option:selected').val();
          				var formSenha = $('#senha').val();

          				var novoUsuario = {};
          				novoUsuario.nome = formNome;
          				novoUsuario.email = formEmail;
          				novoUsuario.celular = formCelular;
          				novoUsuario.curso = formCurso;
          				novoUsuario.periodo = formPeriodo ;
          				novoUsuario.senha = formSenha;

          				$.ajax({
          					url: "${pageContext.request.contextPath}/registro/dadospessoais",
          					type: "POST",
          				    contentType: "application/json; charset=utf-8",
          					data: JSON.stringify(novoUsuario),
          					success: function(result, status, xhr) {

          						console.log(result);
          						console.log(status);
          						console.log(xhr);

          						if (result.sucesso) {

          							var proximaUrl = result.proximaUrl;

          							window.location.href = proximaUrl;

          						}

          					},
          					error: function(jqXHR, textStatus, errorThrown) {

          						alert("Erro de conexao: " + errorThrown);

          					}
          				})




















        });

        $("#cancelar").click(function() {

          window.location.href = '${pageContext.request.contextPath}/..';

        });

      });

    </script>

  </body>

</html>
