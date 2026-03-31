package org.bank.temposervice.repository;

import org.bank.temposervice.entity.Tempista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempistaRepository extends JpaRepository<Tempista, Long> {

    Optional<Tempista> findByName(String name);
}