<%@ page contentType="text/html; charset=UTF-8" %>


<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Caronas Fei - Autenticação FEI</title>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

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
       .form-group select {
         width: 100%;
         padding: 0.5rem;
         background-color: white;
         border: 1px solid #cacaca;
         border-radius: 0.25rem;
         font-size: 0.9rem;
         line-height: 1.8rem;
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
          <h1>Cadastro 2/3</h1>
          <h2>Credênciais FEI</h2>
          <h4>Por critérios de segurança, forneça suas credências do site da FEI.</h4>
          <h4>* Essa informação não será armazenada, apenas utilizada para verificar se o usuário é efetivamente aluno da instituição.</h4>
        </div>


        <div class="form-group">
          <label>Usuário:</label>
          <input id="usuario" type="text" placeholder="Informe seu usuário." />
        </div>

        <div class="form-group">
          <label>Senha:</label>
          <input id="senha" type="text" placeholder="Informe sua senha." />
        </div>

        <div class="form-group">
          <button id="entrar" class="light_green">Entrar</button>
          <button id="cancelar" class="dark_gray">Cancelar</button>
        </div>

      </div>

      <script>

        $(document).ready(function() {

          $("#cancelar").click(function() {
            window.location.href = "${pageContext.request.contextPath}/dadospessoais";
          })

          $("#entrar").click(function() {

            var usuario_in = $("#usuario").val();
            var senha_in = $("#senha").val();

            var ok = true;

            if (usuario_in == '') {
              alert("Preencha o campo usuário.");
              ok = false;
            }

            if ($("#senha") == '') {
              alert("Preencha o campo senha.")
              ok = false;
            }

            if (ok) {

              var autenticacaofei = {};

              autenticacaofei.usuario = usuario_in;
              autenticacaofei.senha = senha_in;

              $.ajax({
                url: '${pageContext.request.contextPath}/registro/autenticacaofei',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(autenticacaofei),
                success: function(result) {

                  var sucesso = result.sucesso;

                  if (sucesso) {
                    window.location.href = result.proximaUrl;
                  } else {
                    alert(result.mensagem);
                  }

                },
                error: function(xhr,status,error) {
                  alert("Erro interno");
                }
              });

            }

          })

        });

      </script>

  </body>

</html>
