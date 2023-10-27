package com.carlos.todolistrocketseat.Model.DTO;

import java.sql.Timestamp;
import java.util.UUID;

public record UserDTO(UUID idUser,
         String userName,
         String email,
         String password,
         Timestamp createdAt) {
}
