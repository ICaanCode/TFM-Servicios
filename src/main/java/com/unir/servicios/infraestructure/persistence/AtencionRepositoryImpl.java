package com.unir.servicios.infraestructure.persistence;

import com.unir.servicios.domain.model.Atencion;
import com.unir.servicios.domain.model.Servicio;
import com.unir.servicios.domain.repository.AtencionJpaRepository;
import com.unir.servicios.infraestructure.utils.SearchCriteria;
import com.unir.servicios.infraestructure.utils.SearchOperation;
import com.unir.servicios.infraestructure.utils.SearchStatement;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AtencionRepositoryImpl {

  private final AtencionJpaRepository repository;

  public List<Atencion> obtenerAtenciones(UUID turnoId, Servicio servicio, Boolean enEspera, UUID usuarioId, LocalDateTime antesDe, LocalDateTime despuesDe) {
    SearchCriteria<Atencion> especificacion = new SearchCriteria<>();
    if (turnoId != null) {
      especificacion.add(new SearchStatement("turnoId", turnoId, SearchOperation.EQUAL));
    }
    if (servicio != null) {
      especificacion.add(new SearchStatement("servicio", servicio, SearchOperation.EQUAL));
    }
//  En Espera: null para consultar cualquier atención, true para consultar atenciones pendientes, false para consultar atenciones abiertas.
    if (Boolean.TRUE.equals(enEspera)) {
      especificacion.add(new SearchStatement("fechaInicio", null, SearchOperation.EQUAL));
    } else if (Boolean.FALSE.equals(enEspera)) {
      especificacion.add(new SearchStatement("fechaInicio", null, SearchOperation.NOT_EQUAL));
      especificacion.add(new SearchStatement("fechaFinalizacion", null, SearchOperation.EQUAL));
    }
    if (usuarioId != null) {
      especificacion.add(new SearchStatement("usuarioId", usuarioId, SearchOperation.EQUAL));
    }
    if (antesDe != null) {
      especificacion.add(new SearchStatement("fechaCreacion", antesDe, SearchOperation.LESS_THAN_EQUAL));
    }
    if (despuesDe != null) {
      especificacion.add(new SearchStatement("fechaCreacion", despuesDe, SearchOperation.GREATER_THAN_EQUAL));
    }

    return repository.findAll(especificacion);
  }

  public Atencion obtenerAtencion(UUID atencionId) { return repository.findById(atencionId).orElseThrow(() -> new EntityNotFoundException(String.format("La atención con UUID '%s' no existe.", atencionId))); }

  public Atencion guardarAtencion(Atencion atencion) {
    return repository.save(atencion);
  }

  private UUID convertirUUID(String criterio) {
    try {
      return UUID.fromString(criterio);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(String.format("El valor '%s' no es un UUID válido.", criterio));
    }
  }

}
