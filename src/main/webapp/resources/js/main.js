$(function() {

	$("#usuario")
			.change(
					function() {

						var successFunc = function(result) {
							var isAvailable = result.available;

							if (isAvailable) {

								console.log($("#usrnm-error").length);

								if ($("#usrnm-error").length > 0) {
									$("#usrnm-error").remove();
								}

								if ($("#usrnm-ok").length === 0) {
									$("input#usuario").addClass("sucesso");
									$(
											"<div id=\"usrnm-ok\" class=\"msg-sucess\">usuario disponivel</div>")
											.insertAfter($("input#usuario"));
								}

							} else {

								if ($("#usrnm-ok").length === 0) {
									$("#usrnm-ok").remove();
								}

								if (!$("#usrnm-error").length === 0) {
									$("input#usuario").addClass("erro");
									$(
											"<div id=\"usrnm-error\" class=\"msg-error\">usuario indisponivel</div>")
											.insertAfter($("input#usuario"));
								}

							}
						};

						var errorFunc = function() {
							alert("Erro conexao");
						};

						checkUsernameAvailability($(this).val(), successFunc,
								errorFunc);

					});

	$("#cadastro-form").on("submit", function(e) {

		e.preventDefault();
		checkPasswordMatch();
		register();

	});
});

function checkUsernameAvailability(username, successFunc, errorFunc) {
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'json',
		url : "http://localhost:8080/checkUsername",
		data : '{"username":"${username}"}',
		success : successFunc,
		error : errorFunc
	});

}

function checkPasswordMatch() {

	var password = $("#password").val();
	var passwordConfirmation = $("#password_confirmation").val();

	if (password !== passwordConfirmation) {

		var passConfMsg1Length = $("#passwd-conf1").length;
		if (passConfMsg1Length === 0) {
			$(
					"<div id=\"passwd-conf1\" class=\"msg-error\">senhas nao conferem</div>")
					.insertAfter($("#password"));
		}

		var passConfMsg2Length = $("#passwd-conf2").length;
		if (passConfMsg2Length === 0) {
			$(
					"<div id=\"passwd-conf2\" class=\"msg-error\">senhas nao conferem</div>")
					.insertAfter($("#password_confirmation"));
		}

		$("#password").addClass("erro");
		$("#password_confirmation").addClass("erro");

	}

}

function register() {
	var successFunc = function(result) {
		var isAvailable = result.available;

		if (isAvailable) {

			var nomeUsuario = $("#usuario").val();
			var email = $("#email").val();
			var senha = $("#password").val();
			var senhaConf = $("#password_confirmation").val();

			if (senha === senhaConf) {
				var newUser = {};
				newUser["usuario"] = nomeUsuario;
				newUser["email"] = email;
				newUser["senha"] = senha;

				var body = JSON.stringify(newUser);

				console.log(body);
			} else {
				console.log("senhas nao conferem..");
			}

		} else {
			$("input#usuario").addClass("erro");
			$("<div class=\"msg-error\">usuario indisponivel</div>")
					.insertAfter($("input#usuario"));

		}
	};

	var errorFunc = function() {
		alert("Erro conexao");
	};

	checkUsernameAvailability($("input#usuario").val(), successFunc, errorFunc);

}