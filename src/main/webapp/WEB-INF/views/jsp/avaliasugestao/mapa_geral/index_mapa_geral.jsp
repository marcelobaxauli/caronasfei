<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE>

<html>

  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <title>Caronas FEI - mapa</title>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.2.0/dist/leaflet.css"/>
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css"/>
    <script src="https://unpkg.com/leaflet@1.2.0/dist/leaflet.js"></script>
    <script src="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">

	<link rel="stylesheet" href="http://localhost:8080/caronasfei/resources/css/caronasfei.css" />

    <script src="http://localhost:8080/caronasfei/resources/js/mapa_geral.js"></script>
    <script src="http://localhost:8080/caronasfei/resources/js/leaflet_polyline_decorator_plugin.js"></script>

</head>
    <body>

      <div class="header">

        <div class="header_inner">
          CARONAS FEI
        </div>

      </div>

      <div class="form-group nav">
        <a href="${pageContext.request.contextPath}/trajeto/avalia">
          <i class="fas fa-arrow-left icon-large"></i>
          <div class="icon-desc">Voltar</div>
        </a>
      </div>

      <div id="mapa" style="width: 100%; height: calc(100% - 87.38px)"></div>

    </body>

    <script>

      $(document).ready(function () {

        var app = new Mapa();
        app.loadTemplates(['mapa_marker_usuario_resumo_info.html',
                           'mapa_marker_voce.html',
                           'mapa_marker_fei.html']);
        app.sincronizaInfo();
        app.sincronizaJob();

        window.app = app;

      });
    </script>

  </html>
