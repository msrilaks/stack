package com.stack.email.repository;

import com.stack.library.model.stack.StackLocationSearch;
import org.springframework.data.repository.CrudRepository;

public interface StackLocationSearchRepository
        extends CrudRepository<StackLocationSearch, String> , StackLocationSearchCustomRepository{
}