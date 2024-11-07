package com.classqr.sistema.profesor.service.impl;

import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.util.mapper.CursoMapper;
import com.classqr.sistema.commons.util.mapper.CursoProfesorMapper;
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

    private final CursoProfesorRepository cursoProfesorRepository;

    private final CursoMapper cursoMapper;

    @Override
    public RespuestaGeneralDTO consultarCursosAsignadosProfesor(String codigoProfesor) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        try{
            respuestaGeneralDTO.setData(cursoMapper.listEntityTolistDto(cursoProfesorRepository.findAllByCodigoProfesor(codigoProfesor)));
            respuestaGeneralDTO.setStatus(HttpStatus.OK);
            respuestaGeneralDTO.setMessage("Se consultaron correctamente los cursos");
        }catch (Exception ex){
            log.error("Error ", ex);
            respuestaGeneralDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            respuestaGeneralDTO.setMessage("Error en la consulta de cursos");
        }
        return respuestaGeneralDTO;
    }
}
