package com.stack.email.repository;

import com.stack.library.model.stack.StackLocation;
import org.springframework.data.repository.CrudRepository;

public interface StackLocationRepository
        extends CrudRepository<StackLocation, String> {
}