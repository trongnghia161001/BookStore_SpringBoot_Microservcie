package com.bookstore.adminsservice.command.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookstore.adminsservice.command.data.Admins;
import com.bookstore.adminsservice.command.data.AdminsRepository;
import com.bookstore.adminsservice.query.model.AdminsResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminsService {

    @Autowired
    private AdminsRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AdminsResponseModel login(String userName, String password) {
        Admins admins = repository.findByEmail(userName);
        AdminsResponseModel responseModel = new AdminsResponseModel();
        if (admins != null) {
            BeanUtils.copyProperties(admins, responseModel);
            if(passwordEncoder.matches(password, responseModel.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String access_token = JWT.create()
                        .withSubject(admins.getEmail())
                        .withClaim("role", admins.getRole())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
                        .sign(algorithm);
                String refreshtoken = JWT.create()
                        .withSubject(admins.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
                        .sign(algorithm);
                responseModel.setToken(access_token);
                responseModel.setRefreshtoken(refreshtoken);
            }
        }
        return responseModel;
    }
}
