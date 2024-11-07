package com.classqr.sistema.profesor.service.impl;

import com.classqr.sistema.commons.dto.CursoDTO;
import com.classqr.sistema.commons.dto.CursoEstudianteDTO;
import com.classqr.sistema.commons.dto.EstudianteDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.dto.embeddable.CursoEstudianteIdDTO;
import com.classqr.sistema.commons.util.mapper.CursoEstudianteMapper;
import com.classqr.sistema.profesor.dto.CursoEstudianteExcelDTO;
import com.classqr.sistema.profesor.repository.CursoEstudianteRepository;
import com.classqr.sistema.profesor.repository.EstudianteRepository;
import com.classqr.sistema.profesor.service.IAsignarEstudianteCursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class AsignarEstudianteCursoService implements IAsignarEstudianteCursoService {


    private final EstudianteRepository estudianteRepository;

    private final CursoEstudianteRepository cursoEstudianteRepository;

    private final CursoEstudianteMapper cursoEstudianteMapper;

    public AsignarEstudianteCursoService(@Qualifier("profesor") EstudianteRepository estudianteRepository,
                                         CursoEstudianteRepository cursoEstudianteRepository,
                                         CursoEstudianteMapper cursoEstudianteMapper
    ) {
        this.estudianteRepository = estudianteRepository;
        this.cursoEstudianteRepository = cursoEstudianteRepository;
        this.cursoEstudianteMapper = cursoEstudianteMapper;
    }

    @Override
    @Transactional
    public RespuestaGeneralDTO asignarEstudiantesCursoExcel(CursoEstudianteExcelDTO cursoEstudianteExcelDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();

        try (InputStream inputStream = new ByteArrayInputStream(cursoEstudianteExcelDTO.getFileEstudiantes())) {
            // Crear el Workbook directamente desde el InputStream
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Detectar la columna "codigo-estudiante" en la primera fila (encabezado)
            Row headerRow = sheet.getRow(0);
            int codigoEstudianteIndex = -1;
            for (Cell cell : headerRow) {
                if ("codigo-estudiante".equalsIgnoreCase(cell.getStringCellValue())) {
                    codigoEstudianteIndex = cell.getColumnIndex();
                    break;
                }
            }

            // Verificar si la columna "codigo-estudiante" fue encontrada
            if (codigoEstudianteIndex == -1) {
                respuestaGeneralDTO.setMessage("Columna 'codigo-estudiante' no encontrada en el archivo.");
                respuestaGeneralDTO.setStatus(HttpStatus.BAD_REQUEST);
                return respuestaGeneralDTO;
            }

            // Leer los códigos de estudiantes y asignarlos al curso
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Empezamos en 1 para omitir el encabezado
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(codigoEstudianteIndex);
                    if (cell != null && estudianteRepository.existsByCodigoEstudiante(cell.getStringCellValue())) {
                        if(!cursoEstudianteRepository.existsById_CodigoEstudianteFkAndId_CodigoCursoFk(cell.getStringCellValue(), cursoEstudianteExcelDTO.getCodigoCurso())){
                            CursoDTO cursoDTO = new CursoDTO();
                            cursoDTO.setCodigoCurso(cursoEstudianteExcelDTO.getCodigoCurso());
                            EstudianteDTO estudianteDTO = new EstudianteDTO();
                            estudianteDTO.setCodigoEstudiante(cell.getStringCellValue());
                            CursoEstudianteIdDTO cursoEstudianteIdDTO = new CursoEstudianteIdDTO();
                            cursoEstudianteIdDTO.setCodigoEstudianteFk(cell.getStringCellValue());
                            cursoEstudianteIdDTO.setCodigoCursoFk(cursoEstudianteExcelDTO.getCodigoCurso());
                            CursoEstudianteDTO cursoEstudianteDTO = new CursoEstudianteDTO();
                            cursoEstudianteDTO.setCodigoCursoEntityFk(cursoDTO);
                            cursoEstudianteDTO.setCodigoEstudianteEntityFk(estudianteDTO);
                            cursoEstudianteDTO.setId(cursoEstudianteIdDTO);
                            // Guardar la asignación del estudiante al curso
                            cursoEstudianteRepository.save(cursoEstudianteMapper.dtoToEntity(cursoEstudianteDTO));
                        }
                    } else if (cell != null) {
                        // Acumular estudiantes que no existen en el sistema
                        stringBuilder.append(cell.getStringCellValue()).append("-");
                    }
                }
            }

            // Definir el mensaje de respuesta según los estudiantes no encontrados
            if (!stringBuilder.isEmpty()) {
                respuestaGeneralDTO.setMessage("Se asignaron los estudiantes, pero estos estudiantes no existen en el sistema: " + stringBuilder);
            } else {
                respuestaGeneralDTO.setMessage("Se asignaron los estudiantes al curso correctamente.");
            }
            respuestaGeneralDTO.setStatus(HttpStatus.CREATED);

            workbook.close();
        } catch (IOException e) {
            log.error("Error en asignar estudiantes al curso", e);
            respuestaGeneralDTO.setMessage("Error en asignar estudiantes al curso: " + e.getMessage());
            respuestaGeneralDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return respuestaGeneralDTO;
    }
}
