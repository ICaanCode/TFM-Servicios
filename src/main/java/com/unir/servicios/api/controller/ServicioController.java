package com.unir.servicios.api.controller;

import com.unir.servicios.api.response.ApiResponse;
import com.unir.servicios.application.usecases.ServicioUseCase;
import com.unir.servicios.domain.model.Servicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/servicios")
@Validated
public class ServicioController {

  private final ServicioUseCase servicioUseCase;

  @GetMapping
  public ResponseEntity<Map<String, Object>> obtenerServicios() {
    List<Servicio> servicios = servicioUseCase.obtenerServicios();
    return ApiResponse.success(servicios, HttpStatus.OK);
  }

}
