package com.bookstore.usersservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookstore.commonservice.command.UpdateAddressByUserCommand;
import com.bookstore.commonservice.model.AddressResponseCommonModel;
import com.bookstore.commonservice.model.ProvinceResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsAddressQuery;
import com.bookstore.commonservice.query.GetDetailsProvinceQuery;
import com.bookstore.commonservice.query.GetProvinceByNameQuery;
import com.bookstore.usersservice.model.UserDTO;
import com.bookstore.usersservice.model.UserRequestModel;
import com.bookstore.usersservice.query.model.UserResponseModel;
import com.bookstore.usersservice.repository.User;
import com.bookstore.usersservice.repository.UserRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private CommandGateway commandGateway;

    public User updateUser(Long userId, UserRequestModel user) {
        Optional<User> userRepositoryById = userRepository.findById(userId);
        if (userRepositoryById.isPresent()) {
            userRepositoryById.get().setId(userId);
            userRepositoryById.get().setUsername(user.getUsername());
            userRepositoryById.get().setLastName(user.getLastName());
            userRepositoryById.get().setFirstName(user.getFirstName());
            userRepositoryById.get().setPhoneNumber(user.getPhoneNumber());
            userRepositoryById.get().setEnabled(user.getEnabled());
            return userRepository.save(userRepositoryById.get());
        } else {
            return null;
        }
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("user");
        return userRepository.save(user);
    }



    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        UserDTO dto = new UserDTO();
        if(user !=null) {
            BeanUtils.copyProperties(user, dto);
            if(passwordEncoder.matches(password, dto.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withClaim("role", user.getRole())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
                        .sign(algorithm);
                String refreshtoken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
                        .sign(algorithm);
                dto.setToken(access_token);
                dto.setRefreshtoken(refreshtoken);
            }
        }
        return dto;
    }
}
