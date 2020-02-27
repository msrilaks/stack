package com.stack.email.repository;

import com.stack.library.model.stack.StackTaskLocation;
import org.springframework.data.repository.CrudRepository;

public interface StackTaskLocationRepository
        extends CrudRepository<StackTaskLocation, String> , StackTaskLocationCustomRepository{
}