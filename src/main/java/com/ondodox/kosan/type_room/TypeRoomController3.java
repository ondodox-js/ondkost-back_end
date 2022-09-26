package com.ondodox.kosan.type_room;

import com.ondodox.kosan.helper.ErrorResponse;
import com.ondodox.kosan.helper.SuccessResponse;
import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.room.RoomService;
import com.ondodox.kosan.room.dto.RoomDTO;
import com.ondodox.kosan.type_room.dto.TypeRoomDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController @RequestMapping("v3/type-room")
public class TypeRoomController3 {
    private final TypeRoomService typeRoomService;
    private final RoomService roomService;

    @Autowired
    public TypeRoomController3(TypeRoomService typeRoomService, RoomService roomService) {
        this.typeRoomService = typeRoomService;
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return new SuccessResponse(typeRoomService.findAll(), HttpStatus.FOUND).sendResponse();
    }

    @GetMapping("{typeId}")
    public ResponseEntity<?> findOne(@PathVariable Long typeId){
        try {
            return new SuccessResponse(typeRoomService.findOne(typeId), HttpStatus.FOUND).sendResponse();
        }catch (Exception e){
            return new ErrorResponse(e, HttpStatus.NOT_FOUND).sendResponse();
        }
    }

    @PostMapping
    public ResponseEntity<?> createOne(@Valid @RequestBody TypeRoomDTO typeRoomDTO, Errors errors){
        try {
            if (errors.hasErrors()){
                throw new Exception();
            }
            ModelMapper mapper = new ModelMapper();
            TypeRoom typeRoom = mapper.map(typeRoomDTO, TypeRoom.class);
            return new SuccessResponse(typeRoomService.save(typeRoom), HttpStatus.CREATED).sendResponse();
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse(e, HttpStatus.BAD_REQUEST);
            for (ObjectError error : errors.getAllErrors()){
                errorResponse.getError().add(error.getDefaultMessage());
            }
            return errorResponse.sendResponse();
        }
    }

    @PutMapping("{typeId}")
    public ResponseEntity<?> updateOne(@PathVariable Long typeId,@Valid @RequestBody TypeRoomDTO typeRoomDTO, Errors errors){
        try {
            if (errors.hasErrors()){
                throw new Exception();
            }
            ModelMapper mapper = new ModelMapper();
            TypeRoom typeRoom = mapper.map(typeRoomDTO, TypeRoom.class);
            typeRoom.setId(typeId);
            return new SuccessResponse(typeRoomService.save(typeRoom), HttpStatus.OK).sendResponse();
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse(e, HttpStatus.BAD_REQUEST);
            for (ObjectError error : errors.getAllErrors()){
                errorResponse.getError().add(error.getDefaultMessage());
            }
            return errorResponse.sendResponse();
        }
    }

    @DeleteMapping("{typeId}")
    public ResponseEntity<?> deleteOne(@PathVariable Long typeId){
        try {
            return new SuccessResponse(typeRoomService.deleteOne(typeId), HttpStatus.OK).sendResponse();
        }catch (Exception e){
            return new ErrorResponse(e, HttpStatus.NOT_FOUND).sendResponse();
        }
    }


    @GetMapping("{typeId}/room")
    public ResponseEntity<?> findByType(@PathVariable Long typeId){
        try{
            TypeRoom typeRoom = typeRoomService.findOne(typeId);
            return new SuccessResponse(roomService.findByTypeRoomId(typeRoom.getId()), HttpStatus.FOUND).sendResponse();
        }catch (Exception e){
            return new ErrorResponse(e, HttpStatus.NOT_FOUND).sendResponse();
        }
    }
    @PostMapping("{typeId}/room")
    public ResponseEntity<?> createOne(@PathVariable Long typeId,@Valid @RequestBody RoomDTO roomDTO, Errors errors){
        try {
            if (errors.hasErrors()){
                throw new Exception();
            }

            ModelMapper mapper = new ModelMapper();
            Room room = mapper.map(roomDTO, Room.class);
            room.setTypeRoom(typeRoomService.findOne(typeId));
            return new SuccessResponse(roomService.save(room), HttpStatus.FOUND).sendResponse();
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(e, HttpStatus.BAD_REQUEST);
            for (ObjectError error : errors.getAllErrors()){
                response.getError().add(error.getDefaultMessage());
            }

            return response.sendResponse();
        }
    }

    @PutMapping("{typeId}/room/{roomId}")
    public ResponseEntity<?> updateOne(@PathVariable Long typeId, @PathVariable Long roomId, @Valid @RequestBody RoomDTO roomDTO, Errors errors){
        try{
            if (errors.hasErrors()){
                throw new Exception();
            }

            ModelMapper mapper = new ModelMapper();
            Room room = mapper.map(roomDTO, Room.class);
            room.setId(roomId);
            room.setTypeRoom(typeRoomService.findOne(typeId));

            return new SuccessResponse(roomService.save(room), HttpStatus.OK).sendResponse();
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(e, HttpStatus.BAD_REQUEST);
            for (ObjectError error : errors.getAllErrors()){
                response.getError().add(error.getDefaultMessage());
            }

            return response.sendResponse();
        }
    }
}
