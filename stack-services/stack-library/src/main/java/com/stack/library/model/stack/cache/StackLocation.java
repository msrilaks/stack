package com.stack.library.model.stack.cache;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RedisHash("StackLocation")
public class StackLocation implements Serializable {
    private String deviceId;
    private String stackId;
    private String userId;
    private double lat;
    private double lng;
    private String taskIdsNearLoc;
    private Date lastLocationSearchDate;
}