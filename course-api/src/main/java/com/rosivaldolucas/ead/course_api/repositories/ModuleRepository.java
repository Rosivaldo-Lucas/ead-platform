package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Module;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID>, JpaSpecificationExecutor<Module> {

    @EntityGraph(attributePaths = { "course" })
    Module findByTitle(String title);

//    @Modifying PARA UPDATE, INSERT OR DELETE
    @Query(value = "SELECT * FROM MODULE WHERE ID_COURSE = :idCourse", nativeQuery = true)
    List<Module> findAllModulesIntoCourse(@Param("idCourse") UUID idCourse);

    @Query(value = "SELECT * FROM MODULE WHERE ID = :idModule AND ID_COURSE = :idCourse", nativeQuery = true)
    Optional<Module> findModuleIntoCourse(@Param("idCourse") UUID idCourse, @Param("idModule") UUID idModule);

}
