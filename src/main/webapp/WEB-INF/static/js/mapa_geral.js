function Mapa() {

  this.baseUrl = "http://localhost:8080/caronasfei"
  this.sugestaoTrajetoDado = null;
  this.markers = [];
  this.mapaInicializado = false;

}

Mapa.prototype.loadTemplates = function(templates) {

  for (var i = 0; i < templates.length; i++) {

    var template = templates[i];

    $.ajax({
      url: this.baseUrl + '/resources/template/' + template,
      type: 'GET',
      cache: true,
      success: function(data) {

        $('body').append(data);

      }
    });

  }

}

Mapa.prototype.inicializaMapa = function() {

  Handlebars.registerHelper("equals", function(input1, input2) {
    return input1 == input2;
  });

  Handlebars.registerHelper("notEquals", function(input1, input2) {
    return input1 != input2;
  });

  // não precisa mais deste helper mas é interessante manter aqui pra ver o
  // funcionamento
  Handlebars.registerHelper("peloMenosUmPassageiroConfirmado", function(
    passageirosElement) {

    var passageiros = passageirosElement.data.root;

    var peloMenosUmaConfirmacao = false;
    for (i = 0; i < passageiros.length; i++) {
      var passageiro = passageiros[i];

      if (passageiro.estado == 'CONFIRMADO') {
        peloMenosUmaConfirmacao = true;
        break;
      }
    }

    return peloMenosUmaConfirmacao;
  })

  var map = L.map('mapa').setView([-23.5505, -46.6333], 11.5555555);

  L
    .tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(map);

  var waypointsArray = [];
  // considerando que o sugestaoTrajeto jah esta preenchido
  var motorista = this.sugestaoTrajetoDado.motorista;

  var motoristaLatitude = motorista.enderecoPartida.latitude;
  var motoristaLongitude = motorista.enderecoPartida.longitude;

  waypointsArray.push(L.latLng(motoristaLatitude, motoristaLongitude));
  // L.marker([motoristaLatitude, motoristaLongitude], {draggable:
  // false}).addTo(map);

  var passageiros = this.sugestaoTrajetoDado.passageiros;

  for (var i = 0; i < passageiros.length; i++) {

    var passageiroLatitude = passageiros[i].enderecoPartida.latitude;
    var passageiroLongitude = passageiros[i].enderecoPartida.longitude;

    waypointsArray.push(L.latLng(passageiroLatitude, passageiroLongitude));
    // L.marker([passageiroLatitude, passageiroLongitude], {draggable:
    // false}).addTo(map);
  }

  var destinoLatitude = this.sugestaoTrajetoDado.destino.latitude;
  var destinoLongitude = this.sugestaoTrajetoDado.destino.longitude;
  waypointsArray.push(L.latLng(destinoLatitude, destinoLongitude));
  // L.marker([destinoLatitude, destinoLongitude], {draggable:
  // false}).addTo(map);

  var polyline = L.polyline(waypointsArray).addTo(map);
  var decorator = L.polylineDecorator(polyline, {
    patterns: [{
      offset: 30,
      repeat: 40,
      symbol: L.Symbol.arrowHead({
        pixelSize: 8,
        pathOptions: {
          stroke: true
        }
      })
    }]
  }).addTo(map);

  var app = this;
  app.map = map;
  L.Routing
    .control({
      waypoints: waypointsArray,
      createMarker: function(waypointIndex, waypoint,
        numberOfWaypoints) {

        var marker = L.marker(waypoint.latLng, {
          draggable: false
        });

        var markerWrapper = {};
        if (waypointIndex == 0) {
          // motorista

          markerWrapper.item = app.sugestaoTrajetoDado.motorista;

        } else if (waypointsArray != numberOfWaypoints - 1) {
          // passageiros

          markerWrapper.item = app.sugestaoTrajetoDado.passageiros[waypointIndex - 1];

        } else {
          // destino

          markerWrapper.item = app.sugestaoTrajetoDado.destino;

        }

        var source = null;
        if (waypointIndex == 0) {
          // TODO: precisa verificar se o cara ta partindo da FEI ou de outro endereco dele
          source = $("#mapa_marker_voce").html();
        } else if (waypointIndex == numberOfWaypoints - 1) {
          // TODO: precisa verificar se o destino dele é a fei realmente
          source = $("#mapa_marker_fei").html();
        } else {
          source = $("#mapa_marker_usuario_resumo_info").html();
        }
        var template = Handlebars.compile(source);
        var html = template(markerWrapper.item);

        marker.bindPopup(html).openPopup();

        markerWrapper.marker = marker;
        app.markers.push(markerWrapper);

        return marker;

      }
    }).addTo(map);

}


Mapa.prototype.aceitarPassageiro = function (idPassageiro) {

  var aceitarPassageiroDado = {};
  // só tem um motorista por sugestao de trajeto
  aceitarPassageiroDado.idMotorista = this.sugestaoTrajetoDado.motorista.id;
  aceitarPassageiroDado.idPassageiro = Number.parseInt(idPassageiro);

  var app = this;

  $.ajax({
    url: this.baseUrl + '/motorista/aceitarpassageiro',
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(aceitarPassageiroDado),
    success: function (result) {

      if (result.sucesso) {
        app.sincronizaInfo();
      } else {
        // exibir erro?
      }

    }
  });
}

Mapa.prototype.rejeitarPassageiro = function (idPassageiro) {

  var rejeitarPassageiroDado = {};
  // só tem um motorista por sugestao de trajeto
  rejeitarPassageiroDado.idMotorista = this.sugestaoTrajetoDado.motorista.id;
  rejeitarPassageiroDado.idPassageiro = Number.parseInt(idPassageiro);

  var app = this;

  $.ajax({
    url: this.baseUrl + '/motorista/rejeitarpassageiro',
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(rejeitarPassageiroDado),
    success: function (result) {

      if (result.sucesso) {
        app.sincronizaInfo();
      } else {
        // exibir erro?
      }

    }
  });
}

Mapa.prototype.substituirPassageiro = function (idPassageiro) {

  var substituirPassageiroDado = {};
  // só tem um motorista por sugestao de trajeto
  substituirPassageiroDado.idMotorista = this.sugestaoTrajetoDado.motorista.id;
  substituirPassageiroDado.idPassageiro = Number.parseInt(idPassageiro);

  var app = this;

  $.ajax({
    url: this.baseUrl + '/motorista/substituirpassageiro',
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(substituirPassageiroDado),
    success: function (result) {

      if (result.sucesso) {
        app.sincronizaInfo();
      } else {
        // exibir erro?
      }

    }
  });
}



Mapa.prototype.sincronizaInfo = function() {
  var app = this;

  $.ajax({
    url: this.baseUrl + '/trajeto/sugestao',
    type: 'GET',
    success: function(result) {

      if (result.sucesso) {

        var sugestaoTrajeto = result.dado;

        if (app.sugestaoTrajetoDado == null ||
          app.contemMudanca(sugestaoTrajeto)) {

          app.sugestaoTrajetoDado = sugestaoTrajeto;

          if (app.mapaInicializado == false) {
            app.inicializaMapa();
            app.mapaInicializado = true;
          } else {
            app.refreshView();
          }

        }

      }

    }
  });
}

Mapa.prototype.contemMudanca = function(sugestaoTrajeto) {

  var motoristaRemoto = sugestaoTrajeto.motorista;
  var motoristaLocal = this.sugestaoTrajetoDado.motorista;

  if (motoristaRemoto.estado != motoristaLocal.estado) {
    return true;
  }

  var passageirosRemoto = sugestaoTrajeto.passageiros;
  var passageirosLocal = this.sugestaoTrajetoDado.passageiros;

  if (passageirosRemoto.length != passageirosLocal.length) {
    return true;
  }

  for (var i = 0; i < passageirosLocal.length; i++) {

    var passageiroRemoto = passageirosLocal[i];
    var passageiroLocal = passageirosRemoto[i];

    if (passageiroRemoto.estado != passageiroLocal.estado) {
      return true;
    }

  }

  return false;

}

Mapa.prototype.refreshView = function() {

  var passageirosRemoto = app.sugestaoTrajetoDado.passageiros;
  var passageirosLocal = app.markers;

  for (var i = 1; i < app.markers.length - 1; i++) {

    var passageiroLocal = app.markers[i].item;
    var passageiroRemoto = passageirosRemoto[i - 1];

    if (passageiroRemoto.estado != passageiroLocal.estado) {

      source = $("#mapa_marker_usuario_resumo_info").html();

      var template = Handlebars.compile(source);
      var html = template(passageiroRemoto);

      app.markers[i].marker.bindPopup(html).openPopup();

      app.markers[i].item = passageiroRemoto;

    }

  }

}

Mapa.prototype.sincronizaJob = function() {

  setTimeout(function() {

    app.sincronizaInfo();
    app.sincronizaJob();

  }, 5000);

}
