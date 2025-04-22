package com.unir.servicios.api.dto.atencion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtencionDTO {

  private UUID idAtencion;
  private UUID turnoId;
  private UUID usuarioId;
  private Integer servicio;
  private Integer prioridad;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinalizacion;

}
