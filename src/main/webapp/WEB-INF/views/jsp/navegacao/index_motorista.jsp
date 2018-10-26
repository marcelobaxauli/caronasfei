<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>


 <head>
	 <meta charset="utf-8" />
	 <title>Caronas FEI - Modo navegação</title>
   <meta name="viewport" content="width=device-width, initial-scale=1">
	 <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
   <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

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

	 </style>

 </head>

	<body>


	 <div class="header">

		 <div class="header_inner">
			 CARONAS FEI
		 </div>

	 </div>

	navegacao

	</body>

</html>