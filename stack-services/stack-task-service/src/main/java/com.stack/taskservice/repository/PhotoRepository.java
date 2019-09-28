package com.stack.taskservice.repository;

import com.stack.library.model.stack.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhotoRepository extends MongoRepository<Photo, String> {
    List<Photo> findByStackIdAndTaskId(String stackId, String taskId);
}