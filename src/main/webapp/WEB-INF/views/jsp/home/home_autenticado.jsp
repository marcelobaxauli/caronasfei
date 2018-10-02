<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>

  <head>
    <meta charset="utf-8" />
    <title>Caronas FEI</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

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


        .home_content_button_space button {
          background-color: #4CAF50;
          cursor: pointer;
          line-height: 30px;
        }

        .home_content_button_space button:hover {
          background-color: #B2DFDB;
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

      <div class="content_inner">

        <div class="home_content_button_space">
          <a href="/caronasfei/cadastrointencao"><button>CRIAR INTENÇÃO DE CARONA</button></a>
        </div>

        <div class="home_content_button_space">
          <button>VERIFICAR HISTÓRICO</button>
        </div>

        <div class="home_content_button_space">
          <button>VERIFICAR PERFIL</button>
        </div>

      </div>

    </div>

  </body>

</html>
