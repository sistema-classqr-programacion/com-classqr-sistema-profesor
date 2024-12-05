/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link CursoProfesorEntity}.
 * Este repositorio maneja las asociaciones entre profesores y cursos.
 */
package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.dto.CursoProfesorDTO;
import com.classqr.sistema.commons.entity.CursoEntity;
import com.classqr.sistema.commons.entity.CursoProfesorEntity;
import com.classqr.sistema.commons.entity.embeddable.CursoProfesorIdEntiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoProfesorRepository extends JpaRepository<CursoProfesorEntity, CursoProfesorIdEntiy> {

    /**
     * Obtiene una lista de todos los cursos asignados a un profesor específico.
     *
     * @param codigoProfesor el código único del profesor.
     * @return una lista de entidades {@link CursoEntity} que representan los cursos asignados al profesor.
     */
    @Query("""
        SELECT cp.codigoCursoEntityFk FROM CursoProfesorEntity cp WHERE cp.codigoProfesorEntityFk.codigoProfesor = :codigoProfesor
    """)
    List<CursoEntity> findAllByCodigoProfesor(@Param("codigoProfesor") String codigoProfesor);

}
