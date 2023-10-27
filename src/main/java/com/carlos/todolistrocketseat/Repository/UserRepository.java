package com.carlos.todolistrocketseat.Repository;

import com.carlos.todolistrocketseat.Exceptions.UserNotFoundException;
import com.carlos.todolistrocketseat.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUserName(String userName);
    Optional<User> findUserByIdUser(UUID idUser) throws UserNotFoundException;


}
