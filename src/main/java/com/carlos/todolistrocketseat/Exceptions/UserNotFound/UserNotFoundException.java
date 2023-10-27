package com.carlos.todolistrocketseat.Exceptions.UserNotFound;

import java.util.UUID;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String e, UUID idUser) {
        super(e + idUser.toString());
    }
}