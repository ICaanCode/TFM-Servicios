package com.unir.servicios.domain.repository;


import com.unir.servicios.domain.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface AtencionJpaRepository extends JpaRepository<Atencion, UUID>, JpaSpecificationExecutor<Atencion> {}
