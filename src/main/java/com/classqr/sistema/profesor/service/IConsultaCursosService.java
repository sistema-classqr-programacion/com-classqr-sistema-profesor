/**
 * Interfaz para el servicio que gestiona las consultas relacionadas con los cursos asignados a profesores.
 * Proporciona un método para obtener los cursos asignados a un profesor específico.
 */
package com.classqr.sistema.profesor.service;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;

public interface IConsultaCursosService {

    /**
     * Consulta los cursos asignados a un profesor.
     *
     * @param codigoProfesor el código único del profesor.
     * @return un objeto {@link RespuestaGeneralDTO} que contiene la lista de cursos asignados al profesor.
     */
    RespuestaGeneralDTO consultarCursosAsignadosProfesor(String codigoProfesor);

}
