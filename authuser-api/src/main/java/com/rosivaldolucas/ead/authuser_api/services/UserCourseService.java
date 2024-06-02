package com.rosivaldolucas.ead.authuser_api.services;

import com.rosivaldolucas.ead.authuser_api.repositories.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;

}
