package com.carlos.todolistrocketseat.Auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.carlos.todolistrocketseat.Model.User;
import com.carlos.todolistrocketseat.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if(path.equals("/task") && request.getMethod().equals("POST")  || request.getMethod().equals("GET")) {
            String authorization = request.getHeader("Authorization");

            String username = getUserCredentials(authorization)[0];
            String encryptedPassword = getUserCredentials(authorization)[1];

            Optional<User> user = userRepository.findUserByUserName(username);

            if (user.isEmpty() || !BCrypt.verifyer().verify(encryptedPassword.toCharArray(), user.get().getPassword()).verified) {
                response.sendError(401);
                return;
            }
            request.setAttribute("idUser", user.get().getIdUser());
        }
        filterChain.doFilter(request, response);
    }

    private String[] getUserCredentials(String authorization) {
        String authDecodedString = authorization.substring("Basic".length()).trim(); // remove "Basic" and trim
        byte[] authDecoded = Base64.getDecoder().decode(authDecodedString); // decode the string to bytes array (byte[]) using Base64 decoder
        String authAsString = new String(authDecoded); // convert the bytes array to string using String constructor
        return authAsString.split(":"); // split the string to username and password (separated by ":")
    }
}
