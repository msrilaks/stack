package com.stack.email.repository;

import com.stack.library.model.stack.StackTasksNearLocation;
import org.springframework.data.repository.CrudRepository;

public interface StackTasksNearLocationRepository
        extends CrudRepository<StackTasksNearLocation, String> {
}