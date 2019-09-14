package com.stack.taskservice.repository;

import com.stack.library.model.stack.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {}