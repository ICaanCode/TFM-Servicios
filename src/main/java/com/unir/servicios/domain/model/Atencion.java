package com.unir.servicios.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "servicio", name = "atencion")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Atencion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_atencion", columnDefinition = "UUID")
  private UUID idAtencion;

  @Column(name = "turno_id", nullable = false)
  private UUID turnoId;

  @Column(name = "usuario_id", nullable = false)
  private UUID usuarioId;

  @ManyToOne
  @JoinColumn(name = "servicio_id", referencedColumnName = "id_servicio", foreignKey = @ForeignKey(name = ""))
  private Servicio servicio;

  @Column(name = "prioridad", nullable = false)
  private Integer prioridad;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_inicio")
  private LocalDateTime fechaInicio;

  @Column(name = "fecha_finalizacion")
  private LocalDateTime fechaFinalizacion;

  @PrePersist
  public void onCreate() { this.fechaCreacion = LocalDateTime.now(); }

}
