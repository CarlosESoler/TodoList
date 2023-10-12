package com.carlos.todolistrocketseat.Controller;

import com.carlos.todolistrocketseat.Model.User;
import com.carlos.todolistrocketseat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){

        if(userRepository.findUserByUserName(user.getUserName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
       return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

}
