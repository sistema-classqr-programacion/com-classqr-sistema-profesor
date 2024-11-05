package com.classqr.sistema.profesor.service.impl;

import com.classqr.sistema.commons.dto.AuthResponseDTO;
import com.classqr.sistema.commons.dto.EstudianteSeguridadDTO;
import com.classqr.sistema.commons.dto.ProfesorSeguridadDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.entity.EstudianteEntity;
import com.classqr.sistema.commons.entity.ProfesorEntity;
import com.classqr.sistema.commons.service.impl.JwtService;
import com.classqr.sistema.commons.util.mapper.ProfesorMapper;
import com.classqr.sistema.profesor.dto.LoginProfesorDTO;
import com.classqr.sistema.profesor.repository.ProfesorReplicaRepository;
import com.classqr.sistema.profesor.service.IQueryProfesorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class QueryProfesorService implements IQueryProfesorService {

    private final ProfesorReplicaRepository profesorReplicaRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final ProfesorMapper profesorMapper;

    @Override
    public RespuestaGeneralDTO loginProfesor(LoginProfesorDTO loginProfesorDTO) {
        RespuestaGeneralDTO respuestaGeneralDto = new RespuestaGeneralDTO();

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginProfesorDTO.getNumeroDocumento(), loginProfesorDTO.getPassword()));
            log.error(authentication.isAuthenticated());
            ProfesorEntity profesor = profesorReplicaRepository.findByNumeroDocumento(loginProfesorDTO.getNumeroDocumento());
            UserDetails user = new ProfesorSeguridadDTO(profesorMapper.entityToDto(profesor));
            String token = jwtService.getToken(user);
            respuestaGeneralDto.setData(AuthResponseDTO.builder().token(token).build());
            respuestaGeneralDto.setMessage("Se inicio correctamente");
            respuestaGeneralDto.setStatus(HttpStatus.OK);
        }catch (Exception e){
            log.error("Error ", e);
            respuestaGeneralDto.setMessage("Hubo un problema en las credenciales");
            respuestaGeneralDto.setStatus(HttpStatus.UNAUTHORIZED);
        }
        return respuestaGeneralDto;
    }
}
