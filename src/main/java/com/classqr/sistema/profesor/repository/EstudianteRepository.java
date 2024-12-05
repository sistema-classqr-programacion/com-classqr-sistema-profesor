/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link EstudianteEntity}.
 * Este repositorio maneja las consultas y verificaciones relacionadas con los estudiantes.
 */
package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("profesor")
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {

    /**
     * Verifica si existe un estudiante con el código especificado.
     *
     * @param codigoEstudiante el código único del estudiante.
     * @return {@code true} si existe un estudiante con el código especificado, {@code false} en caso contrario.
     */
    Boolean existsByCodigoEstudiante(String codigoEstudiante);

}
