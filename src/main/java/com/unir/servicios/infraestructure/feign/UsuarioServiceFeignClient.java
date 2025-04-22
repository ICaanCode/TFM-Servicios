package com.unir.servicios.infraestructure.feign;

import com.unir.servicios.infraestructure.dto.ApiResponse;
import com.unir.servicios.infraestructure.dto.UsuarioDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "usuarios", configuration = FeignConfiguration.class)
public interface UsuarioServiceFeignClient {

  @GetMapping("/api/usuarios/{uuid}")
  ApiResponse<UsuarioDataDTO> obtenerUsuario(@PathVariable("uuid") UUID uuid);
}
