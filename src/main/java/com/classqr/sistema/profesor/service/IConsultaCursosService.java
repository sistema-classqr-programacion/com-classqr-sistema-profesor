package com.classqr.sistema.profesor.service;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;

public interface IConsultaCursosService {

    RespuestaGeneralDTO consultarCursosAsignadosProfesor(String codigoProfesor);

}
