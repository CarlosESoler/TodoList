package com.carlos.todolistrocketseat.Data.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;
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

    // TODO - Make the relationship between User and Task
}
