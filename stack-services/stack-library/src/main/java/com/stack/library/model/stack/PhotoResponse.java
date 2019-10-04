package com.stack.library.model.stack;

import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
public class PhotoResponse {
    private String id;
    private String stackId;
    private String taskId;
    private String title;
    private String image;

    public PhotoResponse(Photo photo) {
        this.id = photo.getId();
        this.stackId = photo.getStackId();
        this.taskId = photo.getTaskId();
        this.title = photo.getTitle();
        this.image = Base64.getEncoder().encodeToString(photo.getImage().getData());
    }
}
