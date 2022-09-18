package com.ondodox.kosan.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("user")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> index(){
        return service.getAll();
    }

    @GetMapping("{username}")
    public User show(@PathVariable String username){
        return service.findByUsername(username);
    }
}
