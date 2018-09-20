package com.caronasfei.assembler.intencao.horario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.assembler.exception.ValidacaoException;
import com.caronasfei.db.intencao.horario.Horario;
import com.caronasfei.db.intencao.horario.HorarioTipo;
import com.caronasfei.service.intencao.horario.HorarioService;

@Component
@Scope("prototype")
public class HorarioAssembler {

	private static final Logger logger = LogManager.getLogger(HorarioAssembler.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");

	@Autowired
	private HorarioService horarioService;

	@Transactional(propagation = Propagation.REQUIRED)
	public Horario toHorario(String opcaoHorarioRaw, String horarioRaw) throws ValidacaoException {

		HorarioTipo horarioTipo = this.horarioService.loadHorarioTipoByCodigo(opcaoHorarioRaw);

		if (horarioTipo == null) {
			throw new ValidacaoException("Tipo de horario inválido.");
		}

		// fazer regex pra verificar horario ?
		Date horarioDate = null;
		if (horarioRaw != null && !"".equals(horarioRaw)) {
			try {
				horarioRaw = horarioRaw.trim();
				horarioRaw = horarioRaw.replace(" ", "");
				horarioDate = sdf.parse(horarioRaw);
			} catch (ParseException e) {

				logger.error("Erro de parse no horario: " + horarioRaw, e);

				throw new ValidacaoException("Horário inválido.");

			}
		} else if (horarioTipo.getCodigo().equals(HorarioTipo.HorarioTipoCodigo.QUALQUER_HORARIO.getCodigo())) {
			horarioDate = null;
		} else {
			throw new ValidacaoException("Horario ou Tipo de horario inválido.");
		}

		Horario horario = new Horario();
		horario.setHorario(horarioDate);
		horario.setHorarioTipo(horarioTipo);

		return horario;

	}

}
