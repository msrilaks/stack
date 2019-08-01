package com.stack.taskservice.repository;

import com.stack.library.model.stack.Stack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StackRepository
        extends MongoRepository<Stack, String>, StackCustomRepository {
    Stack findByUserId(String userId);
}