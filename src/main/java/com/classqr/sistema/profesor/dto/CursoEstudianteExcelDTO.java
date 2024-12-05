/**
 * DTO (Data Transfer Object) que representa la información necesaria para
 * asignar estudiantes a un curso mediante un archivo Excel.
 */
package com.classqr.sistema.profesor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CursoEstudianteExcelDTO {

    /**
     * Código único del curso al que se asignarán los estudiantes.
     */
    private String codigoCurso;

    /**
     * Contenido del archivo Excel que contiene la información de los estudiantes.
     * El archivo se almacena en formato de arreglo de bytes.
     */
    private byte[] fileEstudiantes;

}
