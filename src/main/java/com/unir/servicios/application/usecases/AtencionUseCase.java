package com.unir.servicios.application.usecases;

import com.unir.servicios.api.dto.atencion.AtencionDTO;
import com.unir.servicios.api.dto.atencion.CrearAtencionDTO;
import com.unir.servicios.api.dto.atencion.FiltroBusquedaAtencionDTO;
import com.unir.servicios.api.dto.atencion.ModificarAtencionDTO;
import com.unir.servicios.domain.model.Atencion;
import com.unir.servicios.domain.model.Servicio;
import com.unir.servicios.infraestructure.feign.UsuarioServiceFeignClient;
import com.unir.servicios.infraestructure.persistence.AtencionRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtencionUseCase {

  private final AtencionRepositoryImpl atencionRepository;
  private final ServicioUseCase servicioUseCase;
  private final UsuarioServiceFeignClient usuarioService;

  public List<AtencionDTO> obtenerAtenciones(FiltroBusquedaAtencionDTO filtroBusquedaAtencion) {
    Servicio servicio = null;
    if (filtroBusquedaAtencion.getCodigoServicio() != null) {
      servicio = servicioUseCase.obtenerServicio(filtroBusquedaAtencion.getCodigoServicio());
    }
    UUID turnoId = filtroBusquedaAtencion.getTurnoId() != null ? UUID.fromString(filtroBusquedaAtencion.getTurnoId()) : null;
    UUID usuarioId = filtroBusquedaAtencion.getUsuarioId() != null ? UUID.fromString(filtroBusquedaAtencion.getUsuarioId()) : null;
    if (Boolean.TRUE.equals(filtroBusquedaAtencion.getEnEspera()) && filtroBusquedaAtencion.getUsuarioId() != null) {
      List<Atencion> atencionesPendientes = atencionRepository.obtenerAtenciones(null, null, Boolean.FALSE, usuarioId, null, null);
      if (atencionesPendientes.size() == 0) {
        atencionesPendientes = atencionRepository.obtenerAtenciones(turnoId, servicio, Boolean.TRUE, null, filtroBusquedaAtencion.getAntesDe(), filtroBusquedaAtencion.getDespuesDe());
      }
      return atencionesPendientes.stream().map(this::formatearAtencion).collect(Collectors.toList());
    }

    List<Atencion> atenciones = atencionRepository.obtenerAtenciones(turnoId, servicio, filtroBusquedaAtencion.getEnEspera(), usuarioId, filtroBusquedaAtencion.getAntesDe(), filtroBusquedaAtencion.getDespuesDe());
    return atenciones.stream().map(this::formatearAtencion).collect(Collectors.toList());

  }

  public AtencionDTO crearAtencion(CrearAtencionDTO nuevaAtencionDTO) {
    Servicio servicio = servicioUseCase.obtenerServicio(nuevaAtencionDTO.getServicioId());
    UUID turnoId = UUID.fromString(nuevaAtencionDTO.getTurnoId());
    Atencion nuevaAtencion = Atencion.builder().turnoId(turnoId).servicio(servicio).prioridad(nuevaAtencionDTO.getPrioridad()).build();
    if (nuevaAtencionDTO.getUsuarioId() != null) {
      UUID usuarioId = UUID.fromString(nuevaAtencionDTO.getUsuarioId());
      comprobarUsuario(usuarioId);
      nuevaAtencion.setUsuarioId(usuarioId);
    }
    atencionRepository.guardarAtencion(nuevaAtencion);
    return formatearAtencion(nuevaAtencion);
  }

  public AtencionDTO modificarAtencion(UUID atencionId, ModificarAtencionDTO modificaciones) {
    Atencion atencion = atencionRepository.obtenerAtencion(atencionId);
    if (atencion.getFechaFinalizacion() != null) {
      throw new IllegalArgumentException(String.format("La atención con UUID '%s' ya ha finalizado y no puede ser modificada.", atencionId));
    }
    if (atencion.getUsuarioId() == null) {
      atencion.setUsuarioId(UUID.fromString(modificaciones.getUsuarioId()));
      atencion.setFechaInicio(LocalDateTime.now());
    } else {
      UUID usuarioId = UUID.fromString(modificaciones.getUsuarioId());
      if (!atencion.getUsuarioId().equals(usuarioId)) {
        throw new IllegalArgumentException(String.format("El usuario con UUID '%s' no puede modificar la atención con UUID '%s' porque no le pertenece.", usuarioId, atencionId));
      }
      atencion.setFechaFinalizacion(LocalDateTime.now());
    }
    atencionRepository.guardarAtencion(atencion);
    return formatearAtencion(atencion);
  }

  private AtencionDTO formatearAtencion(Atencion atencion) {
    return new AtencionDTO(atencion.getIdAtencion(), atencion.getTurnoId(), atencion.getUsuarioId(), atencion.getServicio().getCodigo(), atencion.getPrioridad(), atencion.getFechaCreacion(), atencion.getFechaInicio(), atencion.getFechaFinalizacion());
  }

  private void comprobarUsuario(UUID usuarioUUID) {
    usuarioService.obtenerUsuario(usuarioUUID);
  }

}
