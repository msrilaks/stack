package com.stack.library.model.stack;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;


//https://www.baeldung.com/spring-boot-mongodb-upload-file
@Document(collection = "photos")
@ToString
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo implements Cloneable {
    @Id
    private String id;

    private String stackId;

    private String taskId;

    private String title;

    private Binary image;

    public Photo(String stackId, String taskId, String title) {
        this.stackId = stackId;
        this.taskId = taskId;
        this.title = title;
    }


    @Override
    public Photo clone() {
        Photo photo = Photo.builder().build();
        photo.setImage(getImage());
        photo.setTitle(getTitle());
        return photo;
    }
}
