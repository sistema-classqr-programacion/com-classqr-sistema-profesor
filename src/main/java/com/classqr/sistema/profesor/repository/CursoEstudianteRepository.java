package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.entity.CursoEstudianteEntity;
import com.classqr.sistema.commons.entity.embeddable.CursoEstudianteIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoEstudianteRepository extends JpaRepository<CursoEstudianteEntity, CursoEstudianteIdEntity> {

    Boolean existsById_CodigoEstudianteFkAndId_CodigoCursoFk(String codigoEstudiante, String codigoCurso);

}
