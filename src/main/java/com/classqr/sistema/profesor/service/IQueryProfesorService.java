package com.classqr.sistema.profesor.service;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.profesor.dto.LoginProfesorDTO;

public interface IQueryProfesorService {

    RespuestaGeneralDTO loginProfesor(LoginProfesorDTO loginProfesorDTO);

}
