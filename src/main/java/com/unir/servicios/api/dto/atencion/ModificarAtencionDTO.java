package com.unir.servicios.api.dto.atencion;

import com.unir.servicios.domain.validation.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificarAtencionDTO {

  @NotBlank(message = "Debe especificar el usuario que modifica la atención.")
  @ValidUUID(message = "El UUID que ha proporcionado para el usuario no es válido.")
  private String usuarioId;

}
