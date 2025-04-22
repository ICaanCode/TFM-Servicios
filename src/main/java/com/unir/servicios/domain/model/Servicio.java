package com.unir.servicios.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Entity
@Table(schema = "catalogo", name = "servicio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Check(constraints = "codigo >= 30000 AND codigo < 31000")
public class Servicio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_servicio", updatable = false, nullable = false)
  private Integer idServicio;

  @Column(name = "codigo", nullable = false, unique = true)
  private Integer codigo;

  @Column(name = "nombre", nullable = false, unique = true, length = 50)
  private String nombre;

  @Column(name = "descripcion", nullable = false, length = 255)
  private String descripcion;

  @Column(name = "activo")
  private Boolean activo = true;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_modificacion")
  private LocalDateTime fechaModificacion;

  @PrePersist
  public void onCreate() { this.fechaCreacion = LocalDateTime.now(); }

  @PreUpdate
  public void onUpdate() { this.fechaModificacion = LocalDateTime.now(); }

}
