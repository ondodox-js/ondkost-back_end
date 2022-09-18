package com.ondodox.kosan.picture_room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureRoomService {
    PictureRoomRepository repository;

    @Autowired
    public PictureRoomService(PictureRoomRepository repository) {
        this.repository = repository;
    }

    PictureRoom insertPicture(PictureRoom pictureRoom){
        return repository.save(pictureRoom);
    }
}
