/**
 * Controlador para gestionar las operaciones relacionadas con los profesores.
 * Proporciona un endpoint para el inicio de sesión de los profesores.
 */
package com.classqr.sistema.profesor.controller;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.profesor.dto.LoginProfesorDTO;
import com.classqr.sistema.profesor.service.IQueryProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profesor")
@RequiredArgsConstructor
public class ProfesorController {

    /**
     * Servicio para manejar las consultas y operaciones relacionadas con los profesores.
     */
    private final IQueryProfesorService iQueryProfesorService;

    /**
     * Endpoint para realizar el inicio de sesión de un profesor.
     *
     * @param loginProfesorDTO objeto que contiene las credenciales del profesor.
     * @return un objeto {@link ResponseEntity} que contiene un {@link RespuestaGeneralDTO} con el resultado del inicio de sesión.
     */
    @PostMapping("/login")
    public ResponseEntity<RespuestaGeneralDTO> loginProfesor(@RequestBody LoginProfesorDTO loginProfesorDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = iQueryProfesorService.loginProfesor(loginProfesorDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }
}
