package com.unir.servicios.api.dto.atencion;

import com.unir.servicios.domain.validation.ValidUUID;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CrearAtencionDTO {

  @NotNull(message = "Debe especificar el UUID de un turno para crear una atención.")
  @ValidUUID(message = "El UUID que ha proporcionado para el turno no es válido.")
  private String turnoId;

  @ValidUUID(message = "El UUID que ha proporcionado para el usuario no es válido.")
  private String usuarioId;

  @NotNull(message = "Debe especificar el código del turno para el que quiere crear una atención.")
  @Min(value = 30001, message = "El código del servicio debe ser mayor a 30000.")
  @Max(value = 30999, message = "El código del serivcio debe ser menor a 31000.")
  private Integer servicioId;

  @NotNull(message = "Debe especificar un nivel de prioridad para la atención.")
  @Min(value = 1, message = "El valor de la prioridad debe ser mayor o igual a 1.")
  @Max(value = 5, message = "El valor de la prioridad debe ser menor o igual a 5.")
  private Integer prioridad;

}
