package com.stack.library.model.ping;

import com.stack.library.model.stack.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class PingResponse {
    Map<String, Task> tasksNearMe;
    Map<String, Task> tasksRecent;
}
