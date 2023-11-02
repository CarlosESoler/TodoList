package com.carlos.todolistrocketseat.Domain.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.carlos.todolistrocketseat.Data.DTO.UserDTO;
import com.carlos.todolistrocketseat.Data.Model.User;
import com.carlos.todolistrocketseat.Domain.Repository.UserRepository;
import com.carlos.todolistrocketseat.Exceptions.UserNotFound.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {

        // TODO - Refactor this to throw a custom exception
        if (userRepository.findUserByUserName(userDTO.userName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(hashingPassword(user.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    public ResponseEntity getUser(@PathVariable UUID idUser) throws UserNotFoundException {

        User user = userRepository.findUserByIdUser(idUser).orElse(null);
        if(user == null) throw new UserNotFoundException("User not found: ", idUser);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    private String hashingPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
