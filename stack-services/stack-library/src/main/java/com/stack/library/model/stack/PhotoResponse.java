package com.stack.library.model.stack;

import lombok.Getter;
import lombok.Setter;

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
        this.image = photo.getImage();
    }
}
