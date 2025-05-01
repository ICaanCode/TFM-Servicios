package com.unir.servicios.application.usecases;

import com.unir.servicios.domain.model.Servicio;
import com.unir.servicios.infraestructure.persistence.ServicioRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioUseCase {

  private final ServicioRepositoryImpl servicioRepository;

  public List<Servicio> obtenerServicios() {
    return servicioRepository.obtenerServicios();
  }

  public Servicio obtenerServicio(Integer codigo) {
    return servicioRepository.obtenerServicioPorCodigo(codigo);
  }

}
