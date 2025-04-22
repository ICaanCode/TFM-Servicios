package com.unir.servicios.api.dto.servicio;

import com.unir.servicios.domain.model.Servicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTO {

  private Integer codigo;
  private String nombre;
  private String descripcion;
  private Boolean activo;

  public ServicioDTO(Servicio servicio) {
    this.codigo = servicio.getCodigo();
    this.nombre = servicio.getNombre();
    this.descripcion = servicio.getDescripcion();
    this.activo = servicio.getActivo();
  }

}
