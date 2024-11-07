package com.classqr.sistema.profesor.controller;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.profesor.dto.CursoEstudianteExcelDTO;
import com.classqr.sistema.profesor.dto.LoginProfesorDTO;
import com.classqr.sistema.profesor.service.IAsignarEstudianteCursoService;
import com.classqr.sistema.profesor.service.IConsultaCursosService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/curso-profesor")
@RequiredArgsConstructor
public class CursoProfesorController {

    private final IAsignarEstudianteCursoService iAsignarEstudianteCursoService;

    private final IConsultaCursosService iConsultaCursosService;

    @GetMapping("/registrar-estudiante")
    public ResponseEntity<RespuestaGeneralDTO> asignarEstudiantesCurso(
            @RequestParam("file") MultipartFile file,
            @RequestParam("codigoCurso") String codigoCurso) throws IOException {
        CursoEstudianteExcelDTO cursoEstudianteExcelDTO = CursoEstudianteExcelDTO.builder()
                .codigoCurso(codigoCurso)
                .fileEstudiantes(file.getBytes())
                .build();
        RespuestaGeneralDTO respuestaGeneralDTO = iAsignarEstudianteCursoService.asignarEstudiantesCursoExcel(cursoEstudianteExcelDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }

    @GetMapping("/asignados")
    public ResponseEntity<RespuestaGeneralDTO> consultarCursosAsignados(@RequestParam String codigoProfesor) {
        RespuestaGeneralDTO respuestaGeneralDTO = iConsultaCursosService.consultarCursosAsignadosProfesor(codigoProfesor);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }


}
