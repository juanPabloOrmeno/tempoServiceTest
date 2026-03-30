package org.bank.temposervice.repository;

import org.bank.temposervice.entity.Tenpista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenpistaRepository extends JpaRepository<Tenpista, Long> {

    Optional<Tenpista> findByName(String name);
}