/**
 * Interfaz para el servicio que gestiona las operaciones relacionadas con profesores.
 * Proporciona un método para realizar el inicio de sesión de un profesor.
 */
package com.classqr.sistema.profesor.service;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.profesor.dto.LoginProfesorDTO;

public interface IQueryProfesorService {

    /**
     * Realiza el inicio de sesión de un profesor.
     *
     * @param loginProfesorDTO un objeto {@link LoginProfesorDTO} que contiene las credenciales del profesor.
     * @return un objeto {@link RespuestaGeneralDTO} que contiene el resultado del inicio de sesión, incluyendo
     *         información adicional si el inicio de sesión fue exitoso.
     */
    RespuestaGeneralDTO loginProfesor(LoginProfesorDTO loginProfesorDTO);

}
