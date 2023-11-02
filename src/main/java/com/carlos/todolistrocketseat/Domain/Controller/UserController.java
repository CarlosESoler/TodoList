package com.carlos.todolistrocketseat.Domain.Controller;

import com.carlos.todolistrocketseat.Data.DTO.UserDTO;
import com.carlos.todolistrocketseat.Domain.Service.UserService;
import com.carlos.todolistrocketseat.Exceptions.UserNotFound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity getUser(@PathVariable UUID idUser) throws UserNotFoundException {
        return userService.getUser(idUser);
    }
}
