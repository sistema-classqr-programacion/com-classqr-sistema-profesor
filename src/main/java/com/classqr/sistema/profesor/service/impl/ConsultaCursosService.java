/**
 * Servicio que gestiona las consultas relacionadas con los cursos asignados a profesores.
 * Proporciona un método para obtener la lista de cursos asignados a un profesor específico.
 */
package com.classqr.sistema.profesor.service.impl;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.util.mapper.CursoMapper;
import com.classqr.sistema.profesor.repository.CursoProfesorRepository;
import com.classqr.sistema.profesor.service.IConsultaCursosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultaCursosService implements IConsultaCursosService {

    /**
     * Repositorio para realizar consultas relacionadas con los cursos asignados a profesores.
     */
    private final CursoProfesorRepository cursoProfesorRepository;

    /**
     * Mapper para convertir entre entidades de cursos y sus DTOs.
     */
    private final CursoMapper cursoMapper;

    private static final String SUCCESS_MESSAGE = "Se consultaron correctamente los cursos asignados.";
    private static final String ERROR_MESSAGE = "Ocurrió un error durante la consulta de los cursos asignados.";

    /**
     * Consulta los cursos asignados a un profesor específico.
     *
     * @param codigoProfesor el código único del profesor.
     * @return un objeto {@link RespuestaGeneralDTO} con la lista de cursos asignados o información del error.
     */
    @Override
    public RespuestaGeneralDTO consultarCursosAsignadosProfesor(String codigoProfesor) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();

        try {
            // Obtener los cursos asignados y mapearlos a DTOs
            respuestaGeneralDTO.setData(obtenerCursosAsignados(codigoProfesor));
            respuestaGeneralDTO.setStatus(HttpStatus.OK);
            respuestaGeneralDTO.setMessage(SUCCESS_MESSAGE);

        } catch (Exception ex) {
            log.error("Error al consultar cursos asignados para el profesor con código {}: {}", codigoProfesor, ex.getMessage(), ex);
            respuestaGeneralDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            respuestaGeneralDTO.setMessage(ERROR_MESSAGE);
        }

        return respuestaGeneralDTO;
    }

    /**
     * Obtiene y mapea la lista de cursos asignados a un profesor.
     *
     * @param codigoProfesor el código único del profesor.
     * @return una lista de DTOs de cursos asignados.
     */
    private Object obtenerCursosAsignados(String codigoProfesor) {
        return cursoMapper.listEntityTolistDto(cursoProfesorRepository.findAllByCodigoProfesor(codigoProfesor));
    }
}
