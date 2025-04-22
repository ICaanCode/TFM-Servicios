package com.unir.servicios.api.dto.atencion;

import com.unir.servicios.domain.validation.ValidUUID;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FiltroBusquedaAtencionDTO {

  @ValidUUID(message = "El UUID que ha proporcionado para el turno no es válido.")
  private String turnoId;

  @Min(value = 30001, message = "El valor del código del servicio debe estar entre 30001 - 30999.")
  @Max(value = 30999, message = "El valor del código del servicio debe estar entre 30001 - 30999.")
  private Integer codigoServicio;

  private Boolean enEspera;

  @ValidUUID(message = "El UUID que ha proporcionado para el usuario no es válido.")
  private String usuarioId;

  private LocalDateTime despuesDe;

  private LocalDateTime antesDe;

}
