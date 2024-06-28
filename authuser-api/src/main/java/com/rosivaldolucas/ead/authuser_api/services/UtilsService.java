package com.rosivaldolucas.ead.authuser_api.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsService {

  private final String REQUEST_URI = "http://localhost:8080";

  public String createUrl(UUID userId, Pageable pageable) {
    return REQUEST_URI +
            "/courses?userId=" + userId +
            "&page=" + pageable.getPageNumber() +
            "&size=" + pageable.getPageSize() +
            "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
  }

}
