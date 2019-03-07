

SELECT st.id_sugestao_trajeto AS id_sugestao_trajeto, 
	   um.id_usuario AS ID_USUARIO_MOTORISTA,
	   um.nome AS NOME_MOTORISTA,
	   up.id_usuario AS ID_PASSAGEIRO,
	   up.nome AS NOME_PASSAGEIRO,
	   stp.estado AS PASSAGEIRO_ESTADO
	FROM sugestao_trajeto st
	INNER JOIN sugestao_trajeto_motorista stm 
		ON st.id_sugestao_trajeto_motorista = stm.id_sugestao_trajeto_motorista
	INNER JOIN intencao_carona im
		ON stm.id_intencao_carona = im.id_intencao_carona
	INNER JOIN usuario um
		ON im.id_usuario = um.id_usuario
	INNER JOIN sugestao_trajeto_passageiro stp
		ON stp.id_sugestao_trajeto = st.id_sugestao_trajeto
	INNER JOIN intencao_carona ip 
		ON stp.id_intencao_carona = ip.id_intencao_carona
	INNER JOIN usuario up
		ON ip.id_usuario = up.id_usuario
			

SELECT * FROM sugestao_trajeto
		
SELECT * FROM sugestao_trajeto_passageiro