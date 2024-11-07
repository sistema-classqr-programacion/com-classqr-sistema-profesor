package com.classqr.sistema.profesor.service;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.profesor.dto.CursoEstudianteExcelDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IAsignarEstudianteCursoService {

    RespuestaGeneralDTO asignarEstudiantesCursoExcel(CursoEstudianteExcelDTO cursoEstudianteExcelDTO) throws IOException;

}
