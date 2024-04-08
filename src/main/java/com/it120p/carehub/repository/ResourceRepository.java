package com.it120p.carehub.repository;

import com.it120p.carehub.model.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, String> {

    @Query("SELECT res FROM Resource res WHERE res.resourceId = ?1 AND res.resourceType = ?2")
    Optional<Resource> getResourceByIdAndType(String resourceId, String resourceType);
}
