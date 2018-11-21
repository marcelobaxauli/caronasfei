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

  </head>

  <body>

    <div class="header">

      <div class="header_inner">
        CARONAS FEI
      </div>

    </div>

    <div id="mapa" style="width: 100%; height: 100%"></div>

  </body>

  <script>

    function Mapa() {

      this.sugestaoTrajetoDado = null;
      this.markers = [];
      this.mapaInicializado = false;

    }

    Mapa.prototype.inicializaMapa = function () {
      var map = L.map('mapa').setView([
        -23.5505, -46.6333
      ], 11.5555555);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'}).addTo(map);

      var waypointsArray = [];
      // considerando que o sugestaoTrajeto jah esta preenchido
      var motorista = this.sugestaoTrajetoDado.motorista;

      var motoristaLatitude = motorista.enderecoPartida.latitude;
      var motoristaLongitude = motorista.enderecoPartida.longitude;

      waypointsArray.push(L.latLng(motoristaLatitude, motoristaLongitude));
//      L.marker([motoristaLatitude, motoristaLongitude], {draggable: false}).addTo(map);

      var passageiros = this.sugestaoTrajetoDado.passageiros;

      for (var i = 0; i < passageiros.length; i++) {

        var passageiroLatitude = passageiros[i].enderecoPartida.latitude;
        var passageiroLongitude = passageiros[i].enderecoPartida.longitude;

        waypointsArray.push(L.latLng(passageiroLatitude, passageiroLongitude));
//        L.marker([passageiroLatitude, passageiroLongitude], {draggable: false}).addTo(map);
      }

      var destinoLatitude = this.sugestaoTrajetoDado.destino.latitude;
      var destinoLongitude = this.sugestaoTrajetoDado.destino.longitude;
      waypointsArray.push(L.latLng(destinoLatitude, destinoLongitude));
//      L.marker([destinoLatitude, destinoLongitude], {draggable: false}).addTo(map);

      var polyline = L.polyline(waypointsArray).addTo(map);
      var decorator = L.polylineDecorator(polyline, {
        patterns: [
          {
            offset: 30,
            repeat: 40,
            symbol: L.Symbol.arrowHead({
              pixelSize: 8,
              pathOptions: {
                stroke: true
              }
            })
          }
        ]
      }).addTo(map);


      var app = this;
      L.Routing.control({
        waypoints: waypointsArray,
        createMarker: function (waypointIndex, waypoint, numberOfWaypoints) {

          var marker = L.marker(waypoint.latLng, {draggable: false});

          var markerWrapper = {};
          if (waypointIndex == 0) {
            // motorista

            markerWrapper.item = app.sugestaoTrajetoDado.motorista;

          } else if (waypointsArray != numberOfWaypoints - 1) {
            // passageiros

            markerWrapper.item = app.sugestaoTrajetoDado.passageiros[waypointIndex];

          } else {
            // destino

            markerWrapper.item = app.sugestaoTrajetoDado.destino;

          }

          markerWrapper.marker = marker;
          app.markers.push(markerWrapper);

          return marker;

        }
      }).addTo(map);

    }

    Mapa.prototype.sincronizaInfo = function () {
      var app = this;

      $.ajax({
        url: '${pageContext.request.contextPath}/trajeto/sugestao',
        type: 'GET',
        success: function (result) {

          if (result.sucesso) {

            var sugestaoTrajeto = result.dado;

            if (app.sugestaoTrajetoDado == null || app.contemMudanca(sugestaoTrajeto)) {

              app.sugestaoTrajetoDado = sugestaoTrajeto;

              if (app.mapaInicializado == false) {
                app.inicializaMapa();
                app.mapaInicializado = true;
              }

            }

          }

        }
      });
    }

    Mapa.prototype.contemMudanca = function (sugestaoTrajeto) {

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

    Mapa.prototype.refreshView = function () {}

    Mapa.prototype.sincronizaJob = function () {

      setTimeout(function () {

        app.sincronizaInfo();
        app.sincronizaJob();

      }, 30000);

    }

    $(document).ready(function () {

      var app = new Mapa();
      app.sincronizaInfo();
      app.sincronizaJob();

      window.app = app;

    });
  </script>

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

    button.dark_purple {
      background-color: #2B4162;
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

    .painel-botao button {
      width: 45%;
      height: 80%;
      font-size: 12px;
    }

    #mapa {
      border: 1px solid black;
    }

  </style>

  <script>
    (function (global, factory) {
      typeof exports === 'object' && typeof module !== 'undefined'
        ? factory(require('leaflet'))
        : typeof define === 'function' && define.amd
          ? define(['leaflet'], factory)
          : (factory(global.L));
    }(this, (function (L$1) {
      'use strict';

      L$1 = L$1 && L$1.hasOwnProperty('default')
        ? L$1['default']
        : L$1;

      // functional re-impl of L.Point.distanceTo, with no dependency on Leaflet for easier testing
      function pointDistance(ptA, ptB) {
        var x = ptB.x - ptA.x;
        var y = ptB.y - ptA.y;
        return Math.sqrt(x * x + y * y);
      }

      var computeSegmentHeading = function computeSegmentHeading(a, b) {
        return (Math.atan2(b.y - a.y, b.x - a.x) * 180 / Math.PI + 90 + 360) % 360;
      };

      var asRatioToPathLength = function asRatioToPathLength(_ref, totalPathLength) {
        var value = _ref.value,
          isInPixels = _ref.isInPixels;
        return isInPixels
          ? value / totalPathLength
          : value;
      };

      function parseRelativeOrAbsoluteValue(value) {
        if (typeof value === 'string' && value.indexOf('%') !== -1) {
          return {
            value: parseFloat(value) / 100,
            isInPixels: false
          };
        }
        var parsedValue = value
          ? parseFloat(value)
          : 0;
        return {
          value: parsedValue,
          isInPixels: parsedValue > 0
        };
      }

      var pointsEqual = function pointsEqual(a, b) {
        return a.x === b.x && a.y === b.y;
      };

      function pointsToSegments(pts) {
        return pts.reduce(function (segments, b, idx, points) {
          // this test skips same adjacent points
          if (idx > 0 && !pointsEqual(b, points[idx - 1])) {
            var a = points[idx - 1];
            var distA = segments.length > 0
              ? segments[segments.length - 1].distB
              : 0;
            var distAB = pointDistance(a, b);
            segments.push({
              a: a,
              b: b,
              distA: distA,
              distB: distA + distAB,
              heading: computeSegmentHeading(a, b)
            });
          }
          return segments;
        }, []);
      }

      function projectPatternOnPointPath(pts, pattern) {
        // 1. split the path into segment infos
        var segments = pointsToSegments(pts);
        var nbSegments = segments.length;
        if (nbSegments === 0) {
          return [];
        }

        var totalPathLength = segments[nbSegments - 1].distB;

        var offset = asRatioToPathLength(pattern.offset, totalPathLength);
        var endOffset = asRatioToPathLength(pattern.endOffset, totalPathLength);
        var repeat = asRatioToPathLength(pattern.repeat, totalPathLength);

        var repeatIntervalPixels = totalPathLength * repeat;
        var startOffsetPixels = offset > 0
          ? totalPathLength * offset
          : 0;
        var endOffsetPixels = endOffset > 0
          ? totalPathLength * endOffset
          : 0;

        // 2. generate the positions of the pattern as offsets from the path start
        var positionOffsets = [];
        var positionOffset = startOffsetPixels;
        do {
          positionOffsets.push(positionOffset);
          positionOffset += repeatIntervalPixels;
        } while (repeatIntervalPixels > 0 && positionOffset < totalPathLength - endOffsetPixels);

        // 3. projects offsets to segments
        var segmentIndex = 0;
        var segment = segments[0];
        return positionOffsets.map(function (positionOffset) {
          // find the segment matching the offset, starting from the previous one as offsets are ordered
          while (positionOffset > segment.distB && segmentIndex < nbSegments - 1) {
            segmentIndex++;
            segment = segments[segmentIndex];
          }

          var segmentRatio = (positionOffset - segment.distA) / (segment.distB - segment.distA);
          return {
            pt: interpolateBetweenPoints(segment.a, segment.b, segmentRatio),
            heading: segment.heading
          };
        });
      }

      /**
     * Finds the point which lies on the segment defined by points A and B,
     * at the given ratio of the distance from A to B, by linear interpolation.
     */
      function interpolateBetweenPoints(ptA, ptB, ratio) {
        if (ptB.x !== ptA.x) {
          return {
            x: ptA.x + ratio * (ptB.x - ptA.x),
            y: ptA.y + ratio * (ptB.y - ptA.y)
          };
        }
        // special case where points lie on the same vertical axis
        return {
          x: ptA.x,
          y: ptA.y + (ptB.y - ptA.y) * ratio
        };
      }(function () {
        // save these original methods before they are overwritten
        var proto_initIcon = L.Marker.prototype._initIcon;
        var proto_setPos = L.Marker.prototype._setPos;

        var oldIE = (L.DomUtil.TRANSFORM === 'msTransform');

        L.Marker.addInitHook(function () {
          var iconOptions = this.options.icon && this.options.icon.options;
          var iconAnchor = iconOptions && this.options.icon.options.iconAnchor;
          if (iconAnchor) {
            iconAnchor = (iconAnchor[0] + 'px ' + iconAnchor[1] + 'px');
          }
          this.options.rotationOrigin = this.options.rotationOrigin || iconAnchor || 'center bottom';
          this.options.rotationAngle = this.options.rotationAngle || 0;

          // Ensure marker keeps rotated during dragging
          this.on('drag', function (e) {
            e.target._applyRotation();
          });
        });

        L.Marker.include({
          _initIcon: function () {
            proto_initIcon.call(this);
          },

          _setPos: function (pos) {
            proto_setPos.call(this, pos);
            this._applyRotation();
          },

          _applyRotation: function () {
            if (this.options.rotationAngle) {
              this._icon.style[L.DomUtil.TRANSFORM + 'Origin'] = this.options.rotationOrigin;

              if (oldIE) {
                // for IE 9, use the 2D rotation
                this._icon.style[L.DomUtil.TRANSFORM] = 'rotate(' + this.options.rotationAngle + 'deg)';
              } else {
                // for modern browsers, prefer the 3D accelerated version
                this._icon.style[L.DomUtil.TRANSFORM] += ' rotateZ(' + this.options.rotationAngle + 'deg)';
              }
            }
          },

          setRotationAngle: function (angle) {
            this.options.rotationAngle = angle;
            this.update();
            return this;
          },

          setRotationOrigin: function (origin) {
            this.options.rotationOrigin = origin;
            this.update();
            return this;
          }
        });
      })();

      L$1.Symbol = L$1.Symbol || {};

      /**
     * A simple dash symbol, drawn as a Polyline.
     * Can also be used for dots, if 'pixelSize' option is given the 0 value.
     */
      L$1.Symbol.Dash = L$1.Class.extend({
        options: {
          pixelSize: 10,
          pathOptions: {}
        },

        initialize: function initialize(options) {
          L$1.Util.setOptions(this, options);
          this.options.pathOptions.clickable = false;
        },

        buildSymbol: function buildSymbol(dirPoint, latLngs, map, index, total) {
          var opts = this.options;
          var d2r = Math.PI / 180;

          // for a dot, nothing more to compute
          if (opts.pixelSize <= 1) {
            return L$1.polyline([
              dirPoint.latLng, dirPoint.latLng
            ], opts.pathOptions);
          }

          var midPoint = map.project(dirPoint.latLng);
          var angle = -(dirPoint.heading - 90) * d2r;
          var a = L$1.point(midPoint.x + opts.pixelSize * Math.cos(angle + Math.PI) / 2, midPoint.y + opts.pixelSize * Math.sin(angle) / 2);
          // compute second point by central symmetry to avoid unecessary cos/sin
          var b = midPoint.add(midPoint.subtract(a));
          return L$1.polyline([
            map.unproject(a), map.unproject(b)
          ], opts.pathOptions);
        }
      });

      L$1.Symbol.dash = function (options) {
        return new L$1.Symbol.Dash(options);
      };

      L$1.Symbol.ArrowHead = L$1.Class.extend({
        options: {
          polygon: true,
          pixelSize: 10,
          headAngle: 60,
          pathOptions: {
            stroke: false,
            weight: 2
          }
        },

        initialize: function initialize(options) {
          L$1.Util.setOptions(this, options);
          this.options.pathOptions.clickable = false;
        },

        buildSymbol: function buildSymbol(dirPoint, latLngs, map, index, total) {
          return this.options.polygon
            ? L$1.polygon(this._buildArrowPath(dirPoint, map), this.options.pathOptions)
            : L$1.polyline(this._buildArrowPath(dirPoint, map), this.options.pathOptions);
        },

        _buildArrowPath: function _buildArrowPath(dirPoint, map) {
          var d2r = Math.PI / 180;
          var tipPoint = map.project(dirPoint.latLng);
          var direction = -(dirPoint.heading - 90) * d2r;
          var radianArrowAngle = this.options.headAngle / 2 * d2r;

          var headAngle1 = direction + radianArrowAngle;
          var headAngle2 = direction - radianArrowAngle;
          var arrowHead1 = L$1.point(tipPoint.x - this.options.pixelSize * Math.cos(headAngle1), tipPoint.y + this.options.pixelSize * Math.sin(headAngle1));
          var arrowHead2 = L$1.point(tipPoint.x - this.options.pixelSize * Math.cos(headAngle2), tipPoint.y + this.options.pixelSize * Math.sin(headAngle2));

          return [map.unproject(arrowHead1), dirPoint.latLng, map.unproject(arrowHead2)];
        }
      });

      L$1.Symbol.arrowHead = function (options) {
        return new L$1.Symbol.ArrowHead(options);
      };

      L$1.Symbol.Marker = L$1.Class.extend({
        options: {
          markerOptions: {},
          rotate: false
        },

        initialize: function initialize(options) {
          L$1.Util.setOptions(this, options);
          this.options.markerOptions.clickable = false;
          this.options.markerOptions.draggable = false;
        },

        buildSymbol: function buildSymbol(directionPoint, latLngs, map, index, total) {
          if (this.options.rotate) {
            this.options.markerOptions.rotationAngle = directionPoint.heading + (this.options.angleCorrection || 0);
          }
          return L$1.marker(directionPoint.latLng, this.options.markerOptions);
        }
      });

      L$1.Symbol.marker = function (options) {
        return new L$1.Symbol.Marker(options);
      };

      var isCoord = function isCoord(c) {
        return c instanceof L$1.LatLng || Array.isArray(c) && c.length === 2 && typeof c[0] === 'number';
      };

      var isCoordArray = function isCoordArray(ll) {
        return Array.isArray(ll) && isCoord(ll[0]);
      };

      L$1.PolylineDecorator = L$1.FeatureGroup.extend({
        options: {
          patterns: []
        },

        initialize: function initialize(paths, options) {
          L$1.FeatureGroup.prototype.initialize.call(this);
          L$1.Util.setOptions(this, options);
          this._map = null;
          this._paths = this._initPaths(paths);
          this._bounds = this._initBounds();
          this._patterns = this._initPatterns(this.options.patterns);
        },

        /**
       * Deals with all the different cases. input can be one of these types:
       * array of LatLng, array of 2-number arrays, Polyline, Polygon,
       * array of one of the previous.
       */
        _initPaths: function _initPaths(input, isPolygon) {
          var _this = this;

          if (isCoordArray(input)) {
            // Leaflet Polygons don't need the first point to be repeated, but we do
            var coords = isPolygon
              ? input.concat([input[0]])
              : input;
            return [coords];
          }
          if (input instanceof L$1.Polyline) {
            // we need some recursivity to support multi-poly*
            return this._initPaths(input.getLatLngs(), input instanceof L$1.Polygon);
          }
          if (Array.isArray(input)) {
            // flatten everything, we just need coordinate lists to apply patterns
            return input.reduce(function (flatArray, p) {
              return flatArray.concat(_this._initPaths(p, isPolygon));
            }, []);
          }
          return [];
        },

        // parse pattern definitions and precompute some values
        _initPatterns: function _initPatterns(patternDefs) {
          return patternDefs.map(this._parsePatternDef);
        },

        /**
       * Changes the patterns used by this decorator
       * and redraws the new one.
       */
        setPatterns: function setPatterns(patterns) {
          this.options.patterns = patterns;
          this._patterns = this._initPatterns(this.options.patterns);
          this.redraw();
        },

        /**
       * Changes the patterns used by this decorator
       * and redraws the new one.
       */
        setPaths: function setPaths(paths) {
          this._paths = this._initPaths(paths);
          this._bounds = this._initBounds();
          this.redraw();
        },

        /**
       * Parse the pattern definition
       */
        _parsePatternDef: function _parsePatternDef(patternDef, latLngs) {
          return {
            symbolFactory: patternDef.symbol,
            // Parse offset and repeat values, managing the two cases: absolute (in pixels) or relative (in percentage of the polyline length)
            offset: parseRelativeOrAbsoluteValue(patternDef.offset),
            endOffset: parseRelativeOrAbsoluteValue(patternDef.endOffset),
            repeat: parseRelativeOrAbsoluteValue(patternDef.repeat)
          };
        },

        onAdd: function onAdd(map) {
          this._map = map;
          this._draw();
          this._map.on('moveend', this.redraw, this);
        },

        onRemove: function onRemove(map) {
          this._map.off('moveend', this.redraw, this);
          this._map = null;
          L$1.FeatureGroup.prototype.onRemove.call(this, map);
        },

        /**
       * As real pattern bounds depends on map zoom and bounds,
       * we just compute the total bounds of all paths decorated by this instance.
       */
        _initBounds: function _initBounds() {
          var allPathCoords = this._paths.reduce(function (acc, path) {
            return acc.concat(path);
          }, []);
          return L$1.latLngBounds(allPathCoords);
        },

        getBounds: function getBounds() {
          return this._bounds;
        },

        /**
       * Returns an array of ILayers object
       */
        _buildSymbols: function _buildSymbols(latLngs, symbolFactory, directionPoints) {
          var _this2 = this;

          return directionPoints.map(function (directionPoint, i) {
            return symbolFactory.buildSymbol(directionPoint, latLngs, _this2._map, i, directionPoints.length);
          });
        },

        /**
       * Compute pairs of LatLng and heading angle,
       * that define positions and directions of the symbols on the path
       */
        _getDirectionPoints: function _getDirectionPoints(latLngs, pattern) {
          var _this3 = this;

          if (latLngs.length < 2) {
            return [];
          }
          var pathAsPoints = latLngs.map(function (latLng) {
            return _this3._map.project(latLng);
          });
          return projectPatternOnPointPath(pathAsPoints, pattern).map(function (point) {
            return {
              latLng: _this3._map.unproject(L$1.point(point.pt)),
              heading: point.heading
            };
          });
        },

        redraw: function redraw() {
          if (!this._map) {
            return;
          }
          this.clearLayers();
          this._draw();
        },

        /**
       * Returns all symbols for a given pattern as an array of FeatureGroup
       */
        _getPatternLayers: function _getPatternLayers(pattern) {
          var _this4 = this;

          var mapBounds = this._map.getBounds().pad(0.1);
          return this._paths.map(function (path) {
            var directionPoints = _this4._getDirectionPoints(path, pattern)
            // filter out invisible points
              .filter(function (point) {
              return mapBounds.contains(point.latLng);
            });
            return L$1.featureGroup(_this4._buildSymbols(path, pattern.symbolFactory, directionPoints));
          });
        },

        /**
       * Draw all patterns
       */
        _draw: function _draw() {
          var _this5 = this;

          this._patterns.map(function (pattern) {
            return _this5._getPatternLayers(pattern);
          }).forEach(function (layers) {
            _this5.addLayer(L$1.featureGroup(layers));
          });
        }
      });
      /*
     * Allows compact syntax to be used
     */
      L$1.polylineDecorator = function (paths, options) {
        return new L$1.PolylineDecorator(paths, options);
      };

    })));
  </script>

</html>
