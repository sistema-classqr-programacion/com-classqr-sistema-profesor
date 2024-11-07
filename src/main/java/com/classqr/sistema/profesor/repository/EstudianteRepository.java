package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("profesor")
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {

    Boolean existsByCodigoEstudiante(String codigoEstudiante);

}
