/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link CursoEstudianteEntity}.
 * Este repositorio maneja las asociaciones entre estudiantes y cursos.
 */
package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.entity.CursoEstudianteEntity;
import com.classqr.sistema.commons.entity.embeddable.CursoEstudianteIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoEstudianteRepository extends JpaRepository<CursoEstudianteEntity, CursoEstudianteIdEntity> {

    /**
     * Verifica si existe una relación entre un estudiante y un curso específico.
     *
     * @param codigoEstudiante el código único del estudiante.
     * @param codigoCurso      el código único del curso.
     * @return {@code true} si existe la relación entre el estudiante y el curso, {@code false} en caso contrario.
     */
    Boolean existsById_CodigoEstudianteFkAndId_CodigoCursoFk(String codigoEstudiante, String codigoCurso);

}
