<div>
    <div class="box_avaliacao_usuario">

        <div class="box_avaliacao_usuario--foto_marker">
            <img class="box_avaliacao_usuario--foto--img_marker" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQgygYGC_4TN6JA9x_FLoZyuVqfvLOC5b6uj30Op87i51mT_2iSw" />
        </div>

        <div class="box_avaliacao_usuario--detalhes_box">

            <div class="box_avaliacao_usuario--detalhes_marker">

                <div class="box_avaliacao_usuario--detalhe">
                    <span>{{nome}}</span>
                </div>

                <div class="box_avaliacao_usuario--detalhe">
                    <span>{{curso}}</span>
                </div>

                <div class="box_avaliacao_usuario--detalhe">
                    <span>{{periodo}}</span>
                </div>

                <div class="box_avaliacao_usuario--detalhe">
                    <span>{{enderecoPartida.rua}},
                        {{enderecoPartida.bairro}}.
                        {{enderecoPartida.cidade}}.
                        {{enderecoPartida.estado}}</span>
                </div>

            </div>

        </div>

        <div class="box_avaliacao_usuario--status_box">
            <div id="status_area" class="box_avaliacao_usuario--status">
                <!-- essa parte precisa ser criada dinamicamente.. -->
                <!-- representa o estado passageiro (com botão para
                     o motorista indicar que irá buscá-lo, ou estado
                     informando se passageiro já aceitou o trajeto.) -->

                {{#if (notEquals estado 'NAO_CONFIRMADO')}}
                    <span>{{estado}}</span>
                {{else}}
                    <input type="hidden" name="idPassageiro" value="{{id}}" />
                    <button class="light_green" onclick="app.aceitarPassageiro(this)">Aceitar</button>
                    <button class="red" onclick="app.rejeitarPassageiro(this)">Rejeitar</button>
                {{/if}}

            </div>
        </div>
    </div>

</div>