package com.stack.taskservice.repository;

import com.stack.taskservice.model.Stack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StackRepository extends MongoRepository<Stack, String> {
    Stack findByUserId(String userId);
}