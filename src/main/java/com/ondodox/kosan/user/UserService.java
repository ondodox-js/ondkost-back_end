package com.ondodox.kosan.user;

import com.ondodox.kosan.helper.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<CustomResponse> login(User user) {
        CustomResponse customResponse = new CustomResponse();

        try {
            User data = userRepository.findByUsername(user.getUsername());
            if (data.equals(user)){
                customResponse.setData(data);
                customResponse.setMessage("Login success!");
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            }else {
                customResponse.setData(data);
                customResponse.setMessage("Account not found!");
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            customResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
