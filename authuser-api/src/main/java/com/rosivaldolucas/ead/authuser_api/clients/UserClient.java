package com.rosivaldolucas.ead.authuser_api.clients;

import com.rosivaldolucas.ead.authuser_api.dtos.CourseDTO;
import com.rosivaldolucas.ead.authuser_api.dtos.PageResponseDTO;
import com.rosivaldolucas.ead.authuser_api.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class UserClient {

  @Value("${ead.api.url.course-api}")
  private String REQUEST_URI_COURSE_API;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private UtilsService utilsService;

  public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {
    List<CourseDTO> searchResult = null;

    String url = this.REQUEST_URI_COURSE_API + this.utilsService.createUrl(userId, pageable);

    log.debug("Requesting courses from {}", url);
    log.info("Requesting courses from {}", url);

    try {
      ParameterizedTypeReference<PageResponseDTO<CourseDTO>> responseType = new ParameterizedTypeReference<>() {};

      ResponseEntity<PageResponseDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

      searchResult = Objects.requireNonNull(result.getBody()).getContent();

      log.debug("Response number of elements {}", searchResult.size());
    } catch (HttpStatusCodeException e) {
      log.error("Error while requesting courses from {}", url);
      log.error("Error {}", e.getResponseBodyAsString());
    }

    log.info("Ending requesting courses from {}", url);
    log.info("Ending requesting courses for userId {}", userId);

    return new PageImpl<>(Objects.requireNonNull(searchResult));
  }

}
