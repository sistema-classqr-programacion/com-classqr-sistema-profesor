package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorReplicaRepository extends JpaRepository<ProfesorEntity, String> {

    ProfesorEntity findByNumeroDocumento(String numeroDocumento);

}
