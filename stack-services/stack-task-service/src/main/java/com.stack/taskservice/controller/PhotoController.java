package com.stack.taskservice.controller;

import com.stack.library.model.stack.Photo;
import com.stack.taskservice.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PhotoController {

    @Autowired
    PhotoService photoService;

    //header Content-Type:multipart/form-data
    //param title:srip
    //form-data image
    @PostMapping(path = "/stack/{stackId}/tasks/{taskId}/photos")
    public ResponseEntity<String> addPhoto(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId,
            @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image)
            throws IOException {
        String id = photoService.addPhoto(stackId, taskId, title, image);
        return ResponseEntity.ok(id);
    }

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}/photos",
                consumes = "application/json",
                produces = "application/json")
    public ResponseEntity<Map<String, String>> getPhotos(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId) {
        List<Photo> photos = photoService.getPhotos(stackId, taskId);
        Map<String, String> retMap = photos.stream().collect(
                Collectors.toMap(Photo::getId, Photo::getImage));
        return ResponseEntity.ok(retMap);
    }

    @DeleteMapping(path = "/stack/{stackId}/tasks/{taskId}/photos/{photoId}", consumes =
            "application/json",
                   produces = "application/json")
    public ResponseEntity<Void> deletePhoto(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId,
            @PathVariable("photoId") String photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}/photos/{photoId}",
                consumes = "application/json",
                produces = "application/json")
    public ResponseEntity<String> getPhoto(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId,
            @PathVariable("photoId") String photoId) {
        Photo photo = photoService.getPhoto(photoId);
        String im = photo.getImage();
        return ResponseEntity.ok(im);
    }
}
