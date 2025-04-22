package com.unir.servicios.infraestructure.utils;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class SearchCriteria<Atencion> implements Specification<Atencion> {

  private final List<SearchStatement> list = new LinkedList<>();

  public void add(SearchStatement criteria) {
    list.add(criteria);
  }

  @Override
  public Predicate toPredicate(Root<Atencion> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new LinkedList<>();

    for (SearchStatement criteria : list) {
      Path<?> path = root.get(criteria.getKey());
      Class<?> fieldType = path.getJavaType();

      Object value = criteria.getValue();

      if (fieldType.equals(LocalDateTime.class) && value instanceof String) {
        value = LocalDateTime.parse((String) value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      }

      switch (criteria.getOperation()) {
        case SearchOperation.GREATER_THAN -> {
          predicates.add(builder.greaterThan((Path<Comparable>) path, (Comparable) value));
        }
        case LESS_THAN -> {
          predicates.add(builder.lessThan((Path<Comparable>) path, (Comparable) value));
        }
        case GREATER_THAN_EQUAL -> {
          predicates.add(builder.greaterThanOrEqualTo((Path<Comparable>) path, (Comparable) value));
        }
        case LESS_THAN_EQUAL -> {
          predicates.add(builder.lessThanOrEqualTo((Path<Comparable>) path, (Comparable) value));
        }
        case NOT_EQUAL -> {
          predicates.add(value == null ? builder.isNotNull(path) : builder.notEqual(path, value));
        }
        case EQUAL -> {
          predicates.add(value == null ? builder.isNull(path) : builder.equal(path, value));
        }
        case MATCH -> {
          predicates.add(builder.like(builder.lower((Path<String>) path), "%" + value.toString() + "%"));
        }
        case MATCH_END -> {
          predicates.add(builder.like(builder.lower((Path<String>) path), value.toString() + "%"));
        }
      }
    }
    return builder.and(predicates.toArray(new Predicate[0]));
  }
}