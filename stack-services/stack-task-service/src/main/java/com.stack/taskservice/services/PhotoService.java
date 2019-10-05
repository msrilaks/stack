package com.stack.taskservice.services;

import com.stack.library.model.stack.Photo;
import com.stack.library.model.stack.PhotoResponse;
import com.stack.taskservice.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            PhotoService.class.getName());
    @Autowired
    private              PhotoRepository photoRepo;

    public String addPhoto(
            String stackId, String taskId, String title, MultipartFile file)
            throws IOException {
        Photo photo = new Photo(stackId, taskId, title);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo);
        return photo.getId();
    }

    public Photo getPhoto(String id) {
        return photoRepo.findById(id).get();
    }

    public PhotoResponse getPhotoAsResponse(String id) {
        return new PhotoResponse(photoRepo.findById(id).get());
    }

    public List<Photo> getPhotos(String stackId, String taskId) {
        return photoRepo.findByStackIdAndTaskId(stackId, taskId);
    }

    public void movePhotos(String srcStackId, String srcTaskId,String destStackId,
                                  String destTaskId) {
        List<Photo> srcPhotos = photoRepo.findByStackIdAndTaskId(srcStackId, srcTaskId);
        List<Photo> destPhotos = photoRepo.findByStackIdAndTaskId(destStackId, destTaskId);
        deletePhotos(destPhotos);
        srcPhotos.forEach(photo -> {
            Photo c = photo.clone();
            c.setStackId(destStackId);
            c.setTaskId(destTaskId);
            photoRepo.save(c);
            LOGGER.info("## SRI Saved photo : " + c.getId());
        });
    }

    public List<PhotoResponse> getPhotosAsResponse(
            String stackId,
            String taskId) {
        List<Photo> photos = getPhotos(stackId, taskId);
        return photos.stream().map(PhotoResponse::new).collect(Collectors.toList());
    }

    public void deletePhoto(String photoId) {
        photoRepo.deleteById(photoId);
    }

    public List<Photo> deletePhotos(List<Photo> photos) {
        photos.forEach(photo -> {photoRepo.deleteById(photo.getId());
            LOGGER.info("## SRI Delete photo : " + photo.getId());});
        return photos;
    }
}