package com.stack.email.repository;

import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.StackTaskLocation;

import java.util.List;

public interface StackTaskLocationCustomRepository {
    void saveStackTaskLocation(StackTaskLocation stackTaskLocation);

    List<StackTaskLocation> fetchByLocation(String stackId, Location location, long taskDistanceMiles);
}