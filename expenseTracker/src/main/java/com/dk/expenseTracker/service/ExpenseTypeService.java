package com.dk.expenseTracker.service;

import com.dk.expenseTracker.model.ExpenseTypes;
import com.dk.expenseTracker.model.User;
import com.dk.expenseTracker.model.UserStatus;
import com.dk.expenseTracker.repository.ExpenseTypeRepository;
import com.dk.expenseTracker.repository.UserRepository;
import com.dk.expenseTracker.request.CreateExpenseTypeRequest;
import com.dk.expenseTracker.response.CreateExpenseTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private UserRepository userRepository;
    public CreateExpenseTypeResponse addExpenseType(CreateExpenseTypeRequest expenseTypeRequest) {
        ExpenseTypes expenseTypesFromDB = expenseTypeRepository.findByExpenseType(expenseTypeRequest.getExpenseType());
        if(expenseTypesFromDB==null){
            ExpenseTypes expenseTypes = expenseTypeRequest.toExpenseTypes();
            expenseTypesFromDB =  expenseTypeRepository.save(expenseTypes);
        }
        // handle -> user is not present -> say -> then we will create an entry
        User userFromDb = userRepository.findByEmail(expenseTypesFromDB.getCreatedBy());
        if(userFromDb==null){
            User user = User.builder().
                    email(expenseTypeRequest.getUserEmail()).
                    userStatus(UserStatus.Active).
                    build();
            userFromDb =  userRepository.save(user);
        }
        CreateExpenseTypeResponse createExpenseTypeResponse = CreateExpenseTypeResponse.builder().
                expenseId(expenseTypesFromDB.getId()).
                userId(userFromDb.getId()).
                build();

        return createExpenseTypeResponse;
    }

    //cost >= 200 filter
    // expenseDate yes filter
    // notes -> yes filter
    // expenseTypes

    // 2 ways to achieves these search criterias
    // 1 option I should create different method for all (this is not a good approach as there are many end points)
    // by creating enums ( less endpoints, easy debugging, less code)

}
