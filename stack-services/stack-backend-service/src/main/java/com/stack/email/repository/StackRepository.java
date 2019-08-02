package com.stack.email.repository;

import com.stack.library.model.stack.Stack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StackRepository
        extends MongoRepository<Stack, String> {
    Stack findByUserId(String userId);
}