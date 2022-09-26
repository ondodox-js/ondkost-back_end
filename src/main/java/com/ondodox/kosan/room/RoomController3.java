package com.ondodox.kosan.room;

import com.ondodox.kosan.helper.ErrorResponse;
import com.ondodox.kosan.helper.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("v3/room")
public class RoomController3 {
    private final RoomService roomService;

    @Autowired
    public RoomController3(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            return new SuccessResponse(roomService.findAll(), HttpStatus.FOUND).sendResponse();
        }catch (Exception e){
            return new ErrorResponse(e, HttpStatus.NOT_FOUND).sendResponse();
        }
    }

    @GetMapping("{roomId}")
    public ResponseEntity<?> findOne(@PathVariable Long roomId){
        try{
            return new SuccessResponse(roomService.findOne(roomId), HttpStatus.FOUND).sendResponse();
        }catch (Exception e){
            return new ErrorResponse(e, HttpStatus.NOT_FOUND).sendResponse();
        }
    }

    @DeleteMapping("{roomId}")
    public ResponseEntity<?> deleteOne(@PathVariable Long roomId){
        try {
            return new SuccessResponse(roomService.deleteById(roomId), HttpStatus.OK).sendResponse();
        }catch (Exception e){
            return new ErrorResponse(e, HttpStatus.NOT_FOUND).sendResponse();
        }
    }
}
