package com.ondodox.kosan.type_room;

import com.ondodox.kosan.picture_room.PictureRoom;
import com.ondodox.kosan.room.RoomRepository;
import com.ondodox.kosan.room.RoomService;
import com.ondodox.kosan.type_room.dto.TypeRoomCard;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("type-room")
public class TypeRoomController {
    TypeRoomService typeRoomService;
    RoomService roomService;

    TypeRoomRepository typeRoomRepository;
    RoomRepository roomRepository;

    @Autowired
    public TypeRoomController(TypeRoomService typeRoomService, RoomService roomService, TypeRoomRepository typeRoomRepository, RoomRepository roomRepository) {
        this.typeRoomService = typeRoomService;
        this.roomService = roomService;
        this.typeRoomRepository = typeRoomRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping
    Iterable<TypeRoom> index(){
        return typeRoomService.findAll();
    }

    @GetMapping("/{typeId}")
    TypeRoom show(@PathVariable Long typeId){
        return typeRoomService.findOne(typeId);
    }

    @GetMapping("/{typeId}/left-room-count")
    Map<String, Object> countLeftRoom(@PathVariable Long typeId){
        Map<String, Object> response = new HashMap<>();
        response.put("id", typeId);
        response.put("leftRoomCount", roomService.leftRoom(typeId));
        return response;
    }

    @PostMapping
    TypeRoom store(@RequestBody TypeRoom type){
        return typeRoomService.createType(type);
    }

    @PostMapping("/new-type")
        TypeRoom store2(TypeRoom typeRoom, @RequestParam("picture")MultipartFile picture) throws IOException {
        TypeRoom savedType = typeRoomService.createType(typeRoom);
        String photoName = savedType.getTypeName() + "-" + Instant.now().toEpochMilli() + ".jpg";

        PictureRoom pictureRoom = new PictureRoom();
        pictureRoom.setPictureName(photoName);
        pictureRoom.setTypeRoom(savedType);

        typeRoomService.insertPicture(pictureRoom);

        Path pathDirectories = Paths.get("src/main/resources/static/typeRoom/");
        if (!Files.exists(pathDirectories)){
            Files.createDirectories(pathDirectories);
        }

        Path path = Paths.get("src/main/resources/static/typeRoom/" + photoName + ".jpg");
        Files.write(path, picture.getBytes());

        BufferedImage inputImage = ImageIO.read(path.toFile());

        int size, x = 0, y = 0;
        if (inputImage.getWidth() > inputImage.getHeight()) {
            size = inputImage.getHeight();
            x = (inputImage.getWidth() - size) / 2;
        } else {
            size = inputImage.getWidth();
            y = (inputImage.getHeight() - size) / 2;
        }

        BufferedImage croppedImage = Scalr.crop(inputImage, x, y, size, size);
        inputImage.flush();

        BufferedImage resizedImage = Scalr.resize(croppedImage, 512);
        croppedImage.flush();

        ImageIO.write(resizedImage, "jpg", path.toFile());
        resizedImage.flush();



        return typeRoom;
    }

    @GetMapping("/all-id")
    public List<Long> allId(){
        return typeRoomRepository.findAllId();
    }

    @GetMapping("/{typeId}/card")
    public Map<String, Object> cardType(@PathVariable Long typeId){
        Map<String, Object> type = new HashMap<>();
        type.put("typeRoom", typeRoomService.findOne(typeId));
        type.put("leftRoomCount", roomRepository.countByTypeRoom_IdAndStatusFalse(typeId));
        type.put("roomCount", roomRepository.countByTypeRoom_Id(typeId));

        return type;
    }
}
