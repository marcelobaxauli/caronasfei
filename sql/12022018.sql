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



---------------------------------------------
---------------------------------------------


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



-- start: 25/03/2018


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


--------------------------------------------------------
--------------------------------------------------------

-- 07/04/2018:


CREATE TABLE cadastro_etapa (

	id_cadastro_etapa INTEGER PRIMARY KEY AUTO_INCREMENT,
	
	ds_cadastro_etapa VARCHAR(50) NOT NULL	

);

CREATE TABLE cadastro (

	id_cadastro INTEGER PRIMARY KEY AUTO_INCREMENT,	
	
	id_cadastro_etapa INTEGER NOT NULL,
		
	FOREIGN KEY (id_cadastro_etapa) REFERENCES cadastro_etapa (id_cadastro_etapa)
	
);





