/**
 * Controlador para gestionar las operaciones relacionadas con los cursos asignados a profesores.
 * Proporciona un endpoint para consultar los cursos asignados a un profesor específico.
 */
package com.classqr.sistema.profesor.controller;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.profesor.service.IConsultaCursosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curso-profesor")
@RequiredArgsConstructor
public class CursoProfesorController {

    /**
     * Servicio para consultar los cursos asignados a un profesor específico.
     */
    private final IConsultaCursosService iConsultaCursosService;

    /**
     * Endpoint para consultar los cursos asignados a un profesor.
     *
     * @param codigoProfesor el código único del profesor.
     * @return un objeto {@link ResponseEntity} que contiene un {@link RespuestaGeneralDTO} con la lista de cursos asignados al profesor.
     */
    @GetMapping("/asignados")
    public ResponseEntity<RespuestaGeneralDTO> consultarCursosAsignados(@RequestParam String codigoProfesor) {
        RespuestaGeneralDTO respuestaGeneralDTO = iConsultaCursosService.consultarCursosAsignadosProfesor(codigoProfesor);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }
}
