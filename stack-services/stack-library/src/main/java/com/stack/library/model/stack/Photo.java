package com.stack.library.model.stack;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;

//https://www.baeldung.com/spring-boot-mongodb-upload-file
@Document(collection = "photos")
public class Photo {
    @Id
    @Getter
    private String id;

    @Getter
    private String title;

    private Binary image;

    public Photo(String title) {
        this.title = title;
    }

    public void setImage(Binary binary) {
        this.image = binary;
    }

    public String getImage() {
        return Base64.getEncoder().encodeToString(image.getData());
    }
}
