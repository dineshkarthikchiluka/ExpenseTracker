package com.dk.expenseTracker.service;

import com.dk.expenseTracker.model.User;
import com.dk.expenseTracker.model.UserStatus;
import com.dk.expenseTracker.repository.UserRepository;
import com.dk.expenseTracker.request.CreateUserRequest;
import com.dk.expenseTracker.response.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public CreateUserResponse addUser(CreateUserRequest createUserRequest){
        // business logic and then save it in to db using some repository
        // store the user into db
        // first we should check whether the user is already present or not
        // fetching the data from user table, query on user table to fetch data from user.
        User userFromDb = userRepository.findByEmailAddressJPQL(createUserRequest.getEmail());
        if(userFromDb == null){
            User user = createUserRequest.toUser(); // converting CreateUserRequest in to User
            user.setUserStatus(UserStatus.Active);
            userFromDb = userRepository.save(user);
        }

        CreateUserResponse createUserResponse = CreateUserResponse.builder().userId(userFromDb.getId()).build();
        return createUserResponse;

    }
}
