package com.unir.servicios.infraestructure.persistence;

import com.unir.servicios.domain.model.Servicio;
import com.unir.servicios.domain.repository.ServicioJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServicioRepositoryImpl {

  private final ServicioJpaRepository repository;

  public List<Servicio> obtenerServicios() { return repository.findAll(); }

  public Servicio obtenerServicioPorCodigo(Integer codigo) {
    return repository
        .findByCodigo(codigo)
        .orElseThrow(() -> new EntityNotFoundException(String.format("El servicio con código '%s' no existe.", codigo)));
  }

}
