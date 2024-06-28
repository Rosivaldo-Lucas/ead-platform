package com.rosivaldolucas.ead.course_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsService {

    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI;

    public String createUrl(UUID userId, Pageable pageable) {
        return REQUEST_URI +
                "/courses?userId=" + userId +
                "&page=" + pageable.getPageNumber() +
                "&size=" + pageable.getPageSize() +
                "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }

}
