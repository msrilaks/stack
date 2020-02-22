package com.stack.library.model.stack;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RedisHash("StackLocation")
@Getter
@Setter
@ToString
public class StackLocation implements Serializable {
    @Id
    private String stackId;
    private String deviceId;
    private String userId;
    private double lat;
    private double lng;
    private String taskIdsNearLoc;
    private Date lastLocationSearchDate;
    private Date lastLocationSharedDate;
}