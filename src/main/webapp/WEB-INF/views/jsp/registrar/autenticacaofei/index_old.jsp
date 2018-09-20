
<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-beta.1.css" type="text/css"> </head>

<body>
  <nav class="navbar navbar-expand-md bg-primary navbar-dark">
    <div class="container">
      <a class="navbar-brand" href="../pingendo-page_index/index.html">CaronasFEI</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
      <div class="collapse navbar-collapse text-center justify-content-end" id="navbar2SupportedContent">
        <ul class="navbar-nav"></ul>
      </div>
    </div>
  </nav>
  <div class="py-4">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="">Cadastro 2/3</h1>
        </div>
      </div>
    </div>
  </div>
  <div class="py-1">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <p class="lead">Credênciais FEI</p>
          <p class="lead">Por critérios de segurança, forneça suas credenciais do site da FEI.&nbsp;</p>
          <p class="lead">Essa informação não será armezenada, apenas será utilizada para verificar se o usuário é efetivamente aluno da instituição.</p>
        </div>
      </div>
    </div>
  </div>
  <form method="POST" action="${pageContext.request.contextPath}/registrar/autenticacaofei/" id="form_autenticacao_fei">
	  <div class="py-5">
	    <div class="container">
	      <div class="row">
	        <div class="col-md-12">
	          <div class="p-0">
	            <p class="lead p-0">Usuário:&nbsp;</p>
	            <input id="usuario" type="text" class="form-control my-2" data-format="hh:mm:ss" id="usr"> </div>
	        </div>
	      </div>
	    </div>
	  </div>
	  <div class="py-5">
	    <div class="container">
	      <div class="row">
	        <div class="col-md-12">
	          <div class="p-0">
	            <p class="lead p-0">Senha:</p>
	            <input id="senha" type="text" class="form-control my-2" data-format="hh:mm:ss" id="usr"> </div>
	        </div>
	      </div>
	    </div>
	  </div>
	  <div class="py-5">
	    <div class="container">
	      <div class="row">
	        <div class="col-md-12">
	          <input type="submit" class="btn btn-primary" value="Enviar" />
	          <input class="btn mx-3 btn-danger" value="Cancelar" />
	        </div>
	      </div>
	    </div>
	  </div>
  </form>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
</body>

<script>

$(document).ready(function() {

	$('#form_autenticacao_fei').submit(function(event) {

		event.preventDefault();

		var usuarioInput = $("#usuario").val();
		var senhaInput = $("#senha").val();

		var autenticacaofei = {};
		autenticacaofei.usuario = usuarioInput;
		autenticacaofei.senha = senhaInput;

		$.ajax({
			url: "${pageContext.request.contextPath}/registro/autenticacaofei",
			type: "POST",
		    contentType: "application/json; charset=utf-8",
			data: JSON.stringify(autenticacaofei),
			success: function(result, status, xhr) {

				if (result.autenticacaoValida) {
					var nextUrl = result.proximaUrl;

					window.location = nextUrl;
				}

			}, error: function() {

			}
		});

	});

});

</script>

</html>
