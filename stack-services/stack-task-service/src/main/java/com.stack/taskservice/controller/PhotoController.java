package com.stack.taskservice.controller;

import com.stack.library.model.stack.PhotoResponse;
import com.stack.taskservice.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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
    public ResponseEntity<Map<String, String>> addPhoto(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId,
            @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image)
            throws IOException {
        String id = photoService.addPhoto(stackId, taskId, title, image);
        Map<String, String> retMap = new HashMap<>();
        retMap.put("imageId", id);
        return ResponseEntity.ok(retMap);
    }

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}/photos",
                consumes = "application/json",
                produces = "application/json")
    public ResponseEntity<Map<String, PhotoResponse>> getPhotos(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId) {
        List<PhotoResponse> photos = photoService.getPhotosAsResponse(stackId, taskId);
        Map<String, PhotoResponse> retMap = photos.stream().collect(
                Collectors.toMap(x -> x.getId(), x -> x));
        return ResponseEntity.ok(retMap);
    }

    @DeleteMapping(path = "/stack/{stackId}/tasks/{taskId}/photos/{photoId}", consumes =
            "application/json",
                   produces = "application/json")
    public ResponseEntity<Map<String, String>> deletePhoto(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId,
            @PathVariable("photoId") String photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.ok(new HashMap<String, String>());
    }

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}/photos/{photoId}",
                consumes = "application/json",
                produces = "application/json")
    public ResponseEntity<String> getPhoto(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId,
            @PathVariable("photoId") String photoId) {
        PhotoResponse photo = photoService.getPhotoAsResponse(photoId);
        String im = photo.getImage();
        return ResponseEntity.ok(im);
    }
}
