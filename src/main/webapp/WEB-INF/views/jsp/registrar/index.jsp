<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Cadastre-se</title>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>

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
             .form-group select {
               width: 100%;
               padding: 0.5rem;
               background-color: white;
               border: 1px solid #cacaca;
               border-radius: 0.25rem;
               font-size: 0.9rem;
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

            button.facebook {
              background-color: #3b5998
            }

            button.facebook:hover {
              background-color: #527bd2;
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

      <div class="content_s">
          <h1>Cadastro</h1>
          <h3>Selecione uma opção:</h3>

          <div class="form-group">
            <button id="entrar_celular" class="dark_gray">Entrar com celular</button>
          </div>
          <hr />
          <div class="form-group">
            <button class="facebook">Entrar com o Facebook</button>
          </div>

      </div>


      <script>

        $(document).ready(function() {

          $("#entrar_celular").click(function() {

            window.location.href = "${pageContext.request.contextPath}/registro/dadospessoais";

          });


        });

      </script>

  </body>

</html>
