package com.rosivaldolucas.ead.course_api.clients;

import com.rosivaldolucas.ead.course_api.dtos.PageResponseDTO;
import com.rosivaldolucas.ead.course_api.dtos.UserDTO;
import com.rosivaldolucas.ead.course_api.services.UtilsService;
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
public class AuthUserClient {

  @Value("${ead.api.url.authuser}")
  private String REQUEST_URI_AUTHUSER;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private UtilsService utilsService;

  public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
    List<UserDTO> searchResult = null;

    String url = this.REQUEST_URI_AUTHUSER + this.utilsService.createUrl(courseId, pageable);

    log.debug("Requesting courses from {}", url);
    log.info("Requesting courses from {}", url);

    try {
      ParameterizedTypeReference<PageResponseDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};

      ResponseEntity<PageResponseDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

      searchResult = Objects.requireNonNull(result.getBody()).getContent();

      log.debug("Response number of elements {}", searchResult.size());
    } catch (HttpStatusCodeException e) {
      log.error("Error while requesting users from {}", url);
      log.error("Error {}", e.getResponseBodyAsString());
    }

    log.info("Ending requesting users from {}", url);
    log.info("Ending requesting users for courseId {}", courseId);

    return new PageImpl<>(Objects.requireNonNull(searchResult));
  }

  public ResponseEntity<UserDTO> getOneUserById(UUID userId) {
    String url = this.REQUEST_URI_AUTHUSER + "/users/" + userId;

    return this.restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
  }

}
