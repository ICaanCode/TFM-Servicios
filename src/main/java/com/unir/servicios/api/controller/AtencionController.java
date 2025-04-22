package com.unir.servicios.api.controller;

import com.unir.servicios.api.dto.atencion.AtencionDTO;
import com.unir.servicios.api.dto.atencion.CrearAtencionDTO;
import com.unir.servicios.api.dto.atencion.FiltroBusquedaAtencionDTO;
import com.unir.servicios.api.dto.atencion.ModificarAtencionDTO;
import com.unir.servicios.api.response.ApiResponse;
import com.unir.servicios.application.usecases.AtencionUseCase;
import com.unir.servicios.domain.validation.ValidUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/atenciones")
@Validated
public class AtencionController {

  private final AtencionUseCase atencionUseCase;

  @GetMapping
  public ResponseEntity<Map<String, Object>> obtenerAtenciones(@Valid FiltroBusquedaAtencionDTO filtroBusquedaAtencion) {
    List<AtencionDTO> atenciones = atencionUseCase.obtenerAtenciones(filtroBusquedaAtencion);
    return ApiResponse.success(atenciones, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> crearAtencion(@Valid @RequestBody CrearAtencionDTO atencion) {
    AtencionDTO nuevaAtencion = atencionUseCase.crearAtencion(atencion);
    return ApiResponse.success(nuevaAtencion, HttpStatus.CREATED);
  }

  @PatchMapping("/{atencionId}")
  public ResponseEntity<Map<String, Object>> modificarAtencion(@RequestBody ModificarAtencionDTO modificaciones, @PathVariable UUID atencionId) {
    AtencionDTO atencionModificada = atencionUseCase.modificarAtencion(atencionId, modificaciones);
    return ApiResponse.success(atencionModificada, HttpStatus.OK);
  }


}
