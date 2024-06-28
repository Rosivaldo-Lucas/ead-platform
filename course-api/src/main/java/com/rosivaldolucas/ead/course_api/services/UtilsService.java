package com.rosivaldolucas.ead.course_api.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsService {

  public String createUrl(UUID userId, Pageable pageable) {
    return "/courses?userId=" + userId +
            "&page=" + pageable.getPageNumber() +
            "&size=" + pageable.getPageSize() +
            "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
  }

}
