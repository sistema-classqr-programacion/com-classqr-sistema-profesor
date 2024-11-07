package com.classqr.sistema.profesor.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CursoEstudianteExcelDTO {

    private String codigoCurso;

    private byte[] fileEstudiantes;

}
