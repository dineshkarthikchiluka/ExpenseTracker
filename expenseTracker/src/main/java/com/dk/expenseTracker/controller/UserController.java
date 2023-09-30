package com.dk.expenseTracker.controller;

import com.dk.expenseTracker.request.CreateUserRequest;
import com.dk.expenseTracker.response.CreateUserResponse;
import com.dk.expenseTracker.response.GenericResponse;
import com.dk.expenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenseTracker")
public class UserController {
    @Autowired
    private UserService userService;
    // creation part
    @PostMapping("/addUser")
    public GenericResponse<CreateUserResponse> addUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        CreateUserResponse createUserResponse = userService.addUser(createUserRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createUserResponse)
                .build();
        return genericResponse;
    }
}
