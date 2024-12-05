/**
 * DTO (Data Transfer Object) que representa las credenciales necesarias para
 * que un profesor inicie sesión en el sistema.
 */
package com.classqr.sistema.profesor.dto;

import lombok.Data;

@Data
public class LoginProfesorDTO {

    /**
     * Número de documento del profesor que se utiliza como identificador único.
     */
    private String numeroDocumento;

    /**
     * Contraseña del profesor para autenticar el inicio de sesión.
     */
    private String password;

}
