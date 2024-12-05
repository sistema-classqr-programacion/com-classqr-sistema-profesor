/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link ProfesorEntity}.
 * Este repositorio maneja consultas específicas relacionadas con los profesores.
 */
package com.classqr.sistema.profesor.repository;

import com.classqr.sistema.commons.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorReplicaRepository extends JpaRepository<ProfesorEntity, String> {

    /**
     * Busca un profesor en la base de datos utilizando su número de documento.
     *
     * @param numeroDocumento el número de documento único del profesor.
     * @return una instancia de {@link ProfesorEntity} correspondiente al profesor encontrado,
     *         o {@code null} si no existe un profesor con el número de documento especificado.
     */
    ProfesorEntity findByNumeroDocumento(String numeroDocumento);

}
