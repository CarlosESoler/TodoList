package com.carlos.todolistrocketseat.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUser;

    @Column(unique = true)
    private String userName;
    private String email;
    private String password;

    @CreationTimestamp
    private Timestamp createdAt;

}
