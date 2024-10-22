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

    private final IQueryProfesorService iQueryProfesorService;

    @CrossOrigin(origins = "http://localhost:4200") // Habilita CORS solo para este método
    @PostMapping("/login")
    public ResponseEntity<RespuestaGeneralDTO> loginProfesor(@RequestBody LoginProfesorDTO loginProfesorDTO){
        RespuestaGeneralDTO respuestaGeneralDTO = iQueryProfesorService.loginProfesor(loginProfesorDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);    }

}
