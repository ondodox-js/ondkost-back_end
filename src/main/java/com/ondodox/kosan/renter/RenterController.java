package com.ondodox.kosan.renter;

import com.ondodox.kosan.renter.dto.RenterData;
import com.ondodox.kosan.room.RoomService;
import com.ondodox.kosan.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/renter")
public class RenterController {
    RenterService renterService;

    @Autowired
    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping
    public Renter newRenter(@RequestBody RenterData body){
        return renterService.save(body);
    }

    @GetMapping
    public List<Renter> allRenter(){
        return renterService.findAll();
    }

    @GetMapping("/{renterId}")
    public Map<String, Object> oneRenter(@PathVariable Long renterId){
        Map<String, Object> data = new HashMap<>();
        try {
            Renter renter = renterService.findOne(renterId);
            data.put("description", renterService.descRenter(renter));
            data.put("renter", renter);
            return data;
        }catch (Exception e){
            data.put("error", e.getMessage());
            return data;
        }
    }

    @GetMapping("/active-renter")
    public List<Renter> activeRenter(){
        return renterService.findAllByStatus(true);
    }
}
