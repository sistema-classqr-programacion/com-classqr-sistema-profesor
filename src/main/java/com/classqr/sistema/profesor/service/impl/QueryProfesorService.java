/**
 * Servicio que gestiona las operaciones relacionadas con el inicio de sesión de profesores.
 * Realiza la autenticación de las credenciales del profesor, genera un token JWT y maneja los posibles errores.
 */
package com.classqr.sistema.profesor.service.impl;

import com.classqr.sistema.commons.dto.AuthResponseDTO;
import com.classqr.sistema.commons.dto.ProfesorSeguridadDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
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

    /**
     * Repositorio para realizar consultas relacionadas con los profesores.
     */
    private final ProfesorReplicaRepository profesorReplicaRepository;

    /**
     * Gestor de autenticación de Spring Security.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Servicio para la generación y manejo de tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Mapper para convertir entre entidades y DTOs de profesores.
     */
    private final ProfesorMapper profesorMapper;

    private static final String LOGIN_SUCCESS_MESSAGE = "Inicio de sesión exitoso.";
    private static final String LOGIN_ERROR_MESSAGE = "Hubo un problema con las credenciales del profesor.";
    private static final String PROFESSOR_NOT_FOUND_MESSAGE = "El profesor no fue encontrado en la base de datos.";

    /**
     * Realiza el inicio de sesión de un profesor, autenticando las credenciales y generando un token JWT.
     *
     * @param loginProfesorDTO objeto que contiene las credenciales del profesor.
     * @return un objeto {@link RespuestaGeneralDTO} con el resultado del inicio de sesión.
     */
    @Override
    public RespuestaGeneralDTO loginProfesor(LoginProfesorDTO loginProfesorDTO) {
        RespuestaGeneralDTO respuestaGeneralDto = new RespuestaGeneralDTO();

        try {
            // Autenticar credenciales
            autenticarProfesor(loginProfesorDTO);

            // Obtener datos del profesor
            ProfesorEntity profesor = obtenerProfesorPorDocumento(loginProfesorDTO.getNumeroDocumento());

            // Generar token JWT
            String token = generarToken(profesor);

            // Configurar respuesta exitosa
            respuestaGeneralDto.setData(AuthResponseDTO.builder().token(token).build());
            respuestaGeneralDto.setMessage(LOGIN_SUCCESS_MESSAGE);
            respuestaGeneralDto.setStatus(HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("Error de autenticación: {}", e.getMessage());
            respuestaGeneralDto.setMessage(LOGIN_ERROR_MESSAGE);
            respuestaGeneralDto.setStatus(HttpStatus.UNAUTHORIZED);
        } catch (IllegalStateException e) {
            log.error("Error al buscar profesor: {}", e.getMessage());
            respuestaGeneralDto.setMessage(PROFESSOR_NOT_FOUND_MESSAGE);
            respuestaGeneralDto.setStatus(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error inesperado durante el inicio de sesión: {}", e.getMessage(), e);
            respuestaGeneralDto.setMessage(LOGIN_ERROR_MESSAGE);
            respuestaGeneralDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return respuestaGeneralDto;
    }

    /**
     * Autentica las credenciales del profesor utilizando el {@link AuthenticationManager}.
     *
     * @param loginProfesorDTO objeto con las credenciales del profesor.
     * @throws IllegalArgumentException si las credenciales son inválidas.
     */
    private void autenticarProfesor(LoginProfesorDTO loginProfesorDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginProfesorDTO.getNumeroDocumento(),
                            loginProfesorDTO.getPassword()
                    )
            );

            if (!authentication.isAuthenticated()) {
                throw new IllegalArgumentException("Credenciales inválidas.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al autenticar las credenciales del profesor.", e);
        }
    }

    /**
     * Obtiene un profesor por su número de documento.
     *
     * @param numeroDocumento el número de documento único del profesor.
     * @return la entidad {@link ProfesorEntity} correspondiente.
     * @throws IllegalStateException si no se encuentra un profesor con el documento especificado.
     */
    private ProfesorEntity obtenerProfesorPorDocumento(String numeroDocumento) {
        ProfesorEntity profesor = profesorReplicaRepository.findByNumeroDocumento(numeroDocumento);

        if (profesor == null) {
            throw new IllegalStateException("Profesor no encontrado.");
        }

        return profesor;
    }

    /**
     * Genera un token JWT para un profesor autenticado.
     *
     * @param profesor la entidad {@link ProfesorEntity} del profesor autenticado.
     * @return el token JWT generado.
     */
    private String generarToken(ProfesorEntity profesor) {
        UserDetails userDetails = new ProfesorSeguridadDTO(profesorMapper.entityToDto(profesor));
        return jwtService.getToken(userDetails);
    }
}
