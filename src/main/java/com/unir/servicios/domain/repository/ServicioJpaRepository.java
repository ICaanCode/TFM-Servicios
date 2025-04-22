package com.unir.servicios.domain.repository;

import com.unir.servicios.domain.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicioJpaRepository extends JpaRepository<Servicio, Integer> {

  public Optional<Servicio> findByCodigo(Integer codigo);

}
