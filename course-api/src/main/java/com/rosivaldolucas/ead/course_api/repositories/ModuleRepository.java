package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Module;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {

    @EntityGraph(attributePaths = { "course" })
    Module findByTitle(String title);

//    @Modifying PARA UPDATE, INSERT OR DELETE
    @Query(value = "SELECT * FROM MODULE WHERE ID_COURSE = :idCourse", nativeQuery = true)
    List<Module> findAllModulesIntoCourse(@Param("idCourse") UUID idCourse);

}
