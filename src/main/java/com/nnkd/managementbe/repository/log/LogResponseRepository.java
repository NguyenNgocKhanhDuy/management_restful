package com.nnkd.managementbe.repository.log;

import com.nnkd.managementbe.model.log.LogResponse;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogResponseRepository extends MongoRepository<LogResponse, String> {
    List<LogResponse> findAllByProject(ObjectId projectId);
    Page<LogResponse> findAllByProject(ObjectId projectId, Pageable pageable);
}
