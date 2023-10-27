package com.carlos.todolistrocketseat.Controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.carlos.todolistrocketseat.Model.DTO.UserDTO;
import com.carlos.todolistrocketseat.Model.User;
import com.carlos.todolistrocketseat.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDTO userDTO) {

        if (userRepository.findUserByUserName(userDTO.userName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(hashingPassword(user.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity getUser(@PathVariable UUID idUser) {

        User user = userRepository.findUserByIdUser(idUser).orElse(null);
        if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    private String hashingPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

}
