CREATE TABLE curso (
	
	id_curso INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	nome VARCHAR(50),
	
	codigo VARCHAR(50) UNIQUE

);


CREATE TABLE periodo (
	
	id_periodo INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	nome VARCHAR(50),
	
	codigo VARCHAR(1) UNIQUE

);

CREATE TABLE curso_periodo (
	
	id_curso_periodo INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_curso INTEGER NOT NULL,
	
	id_periodo INTEGER NOT NULL,
	
	FOREIGN KEY (id_curso) REFERENCES curso (id_curso),
	
	FOREIGN KEY (id_periodo) REFERENCES periodo (id_periodo)

);



INSERT INTO curso VALUES (NULL, 'Administração', 'ADM');
INSERT INTO curso VALUES (NULL, 'Ciência da Computação', 'CC');
INSERT INTO curso VALUES (NULL, 'Engenharia de Automação e Controle', 'EAC');
INSERT INTO curso VALUES (NULL, 'Engenharia Civil', 'EC');
INSERT INTO curso VALUES (NULL, 'Engenharia Elétrica', 'EL');
INSERT INTO curso VALUES (NULL, 'Engenharia de Materiais', 'EM');
INSERT INTO curso VALUES (NULL, 'Engenharia de Produção', 'EP');
INSERT INTO curso VALUES (NULL, 'Engenharia Química', 'EQ');
INSERT INTO curso VALUES (NULL, 'Engenharia Têxtil', 'ET');
INSERT INTO curso VALUES (NULL, 'Pós Graduação', 'POS');


INSERT INTO periodo VALUES (NULL, 'Noturno', 'N');
INSERT INTO periodo VALUES (NULL, 'Diurno', 'D');


INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Administração' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Ciência da Computação' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia de Automação e Controle' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Civil' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Elétrica' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia de Materiais' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia de Produção' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Química' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Têxtil' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Pós Graduação' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Noturno') );

INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Administração' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Ciência da Computação' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia de Automação e Controle' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Civil' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Elétrica' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia de Materiais' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia de Produção' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Química' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Engenharia Têxtil' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno') );
INSERT INTO curso_periodo VALUES (NULL, ( SELECT id_curso FROM curso WHERE nome = 'Pós Graduação' ) , ( SELECT id_periodo FROM periodo WHERE nome = 'Diurno' ) );




CREATE TABLE perfil_passageiro (
	
	id_perfil_passageiro INTEGER PRIMARY KEY AUTO_INCREMENT

);

CREATE TABLE perfil_motorista (

	id_perfil_motorista INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	modelo_carro VARCHAR(150),
	
	ano INTEGER,
	
	cor VARCHAR(20),
	
	placa VARCHAR(40)

);

CREATE TABLE perfil (

	id_perfil INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_perfil_passageiro INTEGER,
	
	id_perfil_motorista INTEGER,
	
	FOREIGN KEY (id_perfil_passageiro) REFERENCES perfil_passageiro(id_perfil_passageiro),

	FOREIGN KEY (id_perfil_motorista) REFERENCES perfil_motorista(id_perfil_motorista)
);


CREATE TABLE cadastro_etapa (

	id_cadastro_etapa INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	ds_cadastro_etapa VARCHAR(50) NOT NULL,
	
	cd_cadastro_etapa VARCHAR(10) NOT NULL

);


CREATE TABLE cadastro (

	id_cadastro INTEGER PRIMARY KEY AUTO_INCREMENT,	
	
	id_cadastro_etapa INTEGER NOT NULL,
		
	FOREIGN KEY (id_cadastro_etapa) REFERENCES cadastro_etapa (id_cadastro_etapa)
	
);


INSERT INTO cadastro_etapa VALUES (NULL, 'DADOS PESSOAIS', 'DAPE');
INSERT INTO cadastro_etapa VALUES (NULL, 'AUTENTICACAO FEI', 'AUTFEI');
INSERT INTO cadastro_etapa VALUES (NULL, 'PERFIL USUARIO', 'PRFUSU');
INSERT INTO cadastro_etapa VALUES (NULL, 'COMPLETO', 'COMPL');


CREATE TABLE usuario (

	id_usuario INTEGER PRIMARY KEY AUTO_INCREMENT,

	nome VARCHAR(300) NOT NULL,

	foto BLOB,
		
	email VARCHAR(300) NOT NULL,

	senha VARCHAR(300) NOT NULL,

	numero_celular VARCHAR(30) NOT NULL,

	cidade VARCHAR(100),

	autenticado_fei TINYINT NOT NULL,
	
	id_curso_periodo INTEGER NOT NULL,

	id_cadastro INTEGER NOT NULL,
	
	id_perfil INTEGER NOT NULL,
	
	FOREIGN KEY (id_curso_periodo) REFERENCES curso_periodo (id_curso_periodo),

	FOREIGN KEY (id_cadastro) REFERENCES cadastro (id_cadastro),
	
	FOREIGN KEY (id_perfil) REFERENCES perfil (id_perfil)
	
);


CREATE TABLE endereco (
	
	id_endereco INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	opcao_endereco VARCHAR(10) NOT NULL,
	
	rua VARCHAR(500) NOT NULL,
	
	numero INTEGER NOT NULL,
	
	bairro VARCHAR(100) NOT NULL,
	
	cidade VARCHAR(100) NOT NULL,
	
	cep VARCHAR(30) NOT NULL,
	
	estado VARCHAR(30) NOT NULL,

	latitude DOUBLE NOT NULL,
	
	longitude DOUBLE NOT NULL
	
);

INSERT INTO endereco VALUES (1, 'FEI_SBC', 'Av. Humberto de Alencar Castelo Branco', 3972, 'Assunção', 'São Bernardo do Campo', '09850-901', 'SP', -23.72633935, -46.580927320178915);


CREATE TABLE horario_tipo (
	
	id_horario_tipo INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	cd_horario_tipo VARCHAR(30) NOT NULL,
	
	ds_horario_tipo VARCHAR(60) NOT NULL

);



INSERT INTO horario_tipo VALUES (NULL, "qualquer", "Qualquer Horário");
INSERT INTO horario_tipo VALUES (NULL, "horario_fixo", "Horário Fixo");
INSERT INTO horario_tipo VALUES (NULL, "de", "De");
INSERT INTO horario_tipo VALUES (NULL, "ate", "Até");



CREATE TABLE horario (
	
	id_horario INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_horario_tipo INTEGER NOT NULL,
	
	horario DATETIME,
	
	FOREIGN KEY (id_horario_tipo) REFERENCES horario_tipo (id_horario_tipo)

);

CREATE TABLE intencao_carona (

	id_intencao_carona INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_usuario INTEGER NOT NULL,
	
	data_criacao DATETIME NOT NULL,
	
	data_cancelamento DATETIME,
	
	acao_carona VARCHAR(60) NOT NULL,
	
	direcao_carona VARCHAR(60) NOT NULL,
	
	estado VARCHAR(30) NOT NULL,
	
	numero_assentos INTEGER NOT NULL, /* somente para motorista */
	
	detour INTEGER NOT NULL,
	
	id_local_partida INTEGER NOT NULL,
	
	id_local_destino INTEGER NOT NULL,
	
	id_horario_partida INTEGER,
	
	id_horario_chegada INTEGER,
	
	FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
	
	FOREIGN KEY (id_local_partida) REFERENCES endereco (id_endereco),
	
	FOREIGN KEY (id_local_destino) REFERENCES endereco (id_endereco),
	
	FOREIGN KEY (id_horario_partida) REFERENCES horario (id_horario),
	
	FOREIGN KEY (id_horario_chegada) REFERENCES horario (id_horario)

);

CREATE TABLE intencao_carona_motorista (

	id_intencao_carona_motorista INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_intencao_carona INTEGER NOT NULL,
	
	
	FOREIGN KEY (id_intencao_carona) REFERENCES intencao_carona (id_intencao_carona)

);

CREATE TABLE sugestao_trajeto_motorista (

	id_sugestao_trajeto_motorista INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_intencao_carona INTEGER NOT NULL,
	
	estado VARCHAR(20) NOT NULL,
	
	FOREIGN KEY (id_intencao_carona) REFERENCES intencao_carona (id_intencao_carona)
	
);

CREATE TABLE sugestao_trajeto (

	id_sugestao_trajeto INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_sugestao_trajeto_motorista INTEGER NOT NULL,
	
	visualizada TINYINT NOT NULL,
	
	score INTEGER NOT NULL,
	
	FOREIGN KEY (id_sugestao_trajeto_motorista) REFERENCES sugestao_trajeto_motorista (id_sugestao_trajeto_motorista)

	-- os passageiros desta sugestao de trajeto estão na tabela de ligação sugestao_trajeto_passageiro
	-- relacionamento ONE-TO-MANY
	
);

CREATE TABLE sugestao_trajeto_passageiro (

	id_sugestao_trajeto_passageiro INTEGER PRIMARY KEY AUTO_INCREMENT,

	id_intencao_carona INTEGER NOT NULL,
	
	estado VARCHAR(20) NOT NULL,
	
	distancia DECIMAL NOT NULL,
	
	id_sugestao_trajeto INTEGER NOT NULL,
	
	FOREIGN KEY (id_intencao_carona) REFERENCES intencao_carona (id_intencao_carona),
	
	FOREIGN KEY (id_sugestao_trajeto) REFERENCES sugestao_trajeto (id_sugestao_trajeto)
	
	-- o correto seria fazer uma herança aqui... sugestao_trajeto_passageiro deveria ser uma 'subtabela' 
	-- de sugestao_trajeto_usuario! Estou praticamente copiando os atributos da sugestao_trajeto_usuario
	
);

CREATE TABLE rejeicao_carona (
	
	id_rejeicao_carona INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	id_usuario_motorista INTEGER NOT NULL,
	
	id_usuario_passageiro INTEGER NOT NULL,
	
	sentido VARCHAR(60),
	
	FOREIGN KEY (id_usuario_motorista) REFERENCES usuario (id_usuario),
	
	FOREIGN KEY (id_usuario_passageiro) REFERENCES usuario (id_usuario)

);
