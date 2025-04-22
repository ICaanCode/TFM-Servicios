package com.unir.servicios.infraestructure.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;

public class CustomFeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    String serviceName = methodKey.split("#")[0];

    if (response.status() == HttpStatus.NOT_FOUND.value()) {
      return new EntityNotFoundException("Recurso no encontrado en " + serviceName);
    }
    if (response.status() >= 400 && response.status() < 500) {
      return new IllegalArgumentException("Error en la solicitud al servicio " + serviceName);
    }
    if (response.status() >= 500) {
      return new RuntimeException("Error interno en el servicio " + serviceName);
    }

    return defaultErrorDecoder.decode(methodKey, response);
  }

}
