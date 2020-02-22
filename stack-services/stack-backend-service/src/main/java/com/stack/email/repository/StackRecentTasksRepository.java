package com.stack.email.repository;

import com.stack.library.model.stack.StackRecentTasks;
import org.springframework.data.repository.CrudRepository;

public interface StackRecentTasksRepository
        extends CrudRepository<StackRecentTasks, String> {
}