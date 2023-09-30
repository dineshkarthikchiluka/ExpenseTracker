package com.dk.expenseTracker.controller;

import com.dk.expenseTracker.request.CreateExpenseTypeRequest;
import com.dk.expenseTracker.response.CreateExpenseTypeResponse;
import com.dk.expenseTracker.response.GenericResponse;
import com.dk.expenseTracker.service.ExpenseTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenseTracker")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @PostMapping("/addExpenseType")
    public GenericResponse<CreateExpenseTypeResponse> addExpenseType(@Valid @RequestBody CreateExpenseTypeRequest expenseTypeRequest){
        CreateExpenseTypeResponse createExpenseTypeResponse = expenseTypeService.addExpenseType(expenseTypeRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createExpenseTypeResponse)
                .build();
        return genericResponse;
    }
}
