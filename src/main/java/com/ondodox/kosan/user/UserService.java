package com.ondodox.kosan.user;

import com.ondodox.kosan.helper.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            log.info("User found in the database: {}", username);
        }else {
            throw new UsernameNotFoundException("user not found in the database");
        }
        User user1 = user.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user1.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user1.getUsername(),
                user1.getPassword(),
                authorities
        );
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    public User findByUsername(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
    public Optional<User> findByUsernameOptional(String username){
        return userRepository.findByUsername(username);
    }
    public ResponseEntity<CustomResponse> login(User user) {
        CustomResponse customResponse = new CustomResponse();

        try {
            Optional<User> data = userRepository.findByUsername(user.getUsername());
            if (data.isPresent()){
                User data1 = data.get();
                if (data1.getPassword().equals(user.getPassword())){
                    customResponse.setData(data);
                    customResponse.setMessage("Login success!");
                    return new ResponseEntity<>(customResponse, HttpStatus.OK);
                }
            }else {
                customResponse.setData(data);
                customResponse.setMessage("Account not found!");
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            customResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public User registerUser(User user){
        boolean userPresent = userRepository.findByUsername(user.getUsername()).isPresent();
        if (userPresent){
            throw new RuntimeException(
                    String.format("User with email '%s' already exist!", user.getUsername())
            );
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(UserRole.ADMIN);
        return userRepository.save(user);
    }
}
