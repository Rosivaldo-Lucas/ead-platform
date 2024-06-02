package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.repositories.CourseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

}
