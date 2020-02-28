package com.stack.email.repository;

import com.stack.library.model.stack.StackLocationSearch;

public interface StackLocationSearchCustomRepository {
    void saveStackLocationSearch(String stackId);
    StackLocationSearch getStackLocationSearch();
}