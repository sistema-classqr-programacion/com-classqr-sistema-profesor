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

    @Query("""
        SELECT cp.codigoCursoEntityFk FROM CursoProfesorEntity cp WHERE cp.codigoProfesorEntityFk.codigoProfesor = :codigoProfesor
    """)
    List<CursoEntity> findAllByCodigoProfesor(@Param("codigoProfesor") String codigoProfesor);

}
