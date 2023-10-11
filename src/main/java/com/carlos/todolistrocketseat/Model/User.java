package com.carlos.todolistrocketseat.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(unique = true)
    private String userName;
    private String email;
    private String password;

    @CreationTimestamp
    private Timestamp createdAt;
}
