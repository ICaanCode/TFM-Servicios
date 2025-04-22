package com.unir.servicios.application.usecases;

import com.unir.servicios.api.dto.servicio.ServicioDTO;
import com.unir.servicios.domain.model.Servicio;
import com.unir.servicios.infraestructure.persistence.ServicioRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioUseCase {

  private final ServicioRepositoryImpl servicioRepository;

  public List<ServicioDTO> obtenerServicios() {
    return servicioRepository.obtenerServicios().stream().map(ServicioDTO::new).collect(Collectors.toList());
  }

  public Servicio obtenerServicio(Integer codigo) {
    return servicioRepository.obtenerServicioPorCodigo(codigo);
  }

}
