<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>


 <head>
	 <meta charset="utf-8" />
	 <title>Login - Caronas FEI</title>
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
       margin-bottom: 0.5rem;

     }

     input[type="submit"] {
       background-color: blue;

     }

     button#cancelar {

       background-color: red;

     }

		 .login_box {
			 width: 450px;
			 min-width: 350px;
/* 			 border: 1px solid #b5b5b5; */
			 border-radius: 4px;
			 margin: 0px auto;
			 padding-bottom: 30px;
		 }

		 .login_box_title {
			 padding-top: 15px;
			 padding-bottom: 15px;
			 margin-bottom: 15px;
		 }

		 .login_box_input {
			 overflow: hidden;
			 line-height: 40px;
			 margin: 0px auto;
			 margin-top: 10px;
		 }

		 .login_box_input button {
			 overflow: hidden;
			 line-height: 40px;
			 width: 450px;
			 margin: 0px auto;
			 margin-top: 2px;
			 font-size: 16px;
		 }

		 .login_box_input button#loginbtn {
			 background-color: #009688;
			 color: white;
			 border: 0px;
		 }

		 .login_box_input button#loginbtn:hover {
			 background-color: #00695C;
			 color: white;
			 border: 0px;
		 }

		 .login_box_input button#registrarbtn {
			 background-color: #673AB7;
			 color: white;
			 border: 0px;
		 }

		 .login_box_input button#registrarbtn:hover {
			 background-color: #311B92;
			 color: white;
			 border: 0px;
		 }

		 .login_box_input input {
			 width: 100%;
			 line-height: 50px;
			 padding-left: 5px;
			 font-size: 15px;
		 }

		 .login_box_input input::-webkit-input-placeholder {
	    	font-size: 15px;
		}

		.first_button {
			margin-top: 20px;
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