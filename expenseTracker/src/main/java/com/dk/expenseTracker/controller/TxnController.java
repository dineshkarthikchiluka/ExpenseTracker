package com.dk.expenseTracker.controller;

import com.dk.expenseTracker.model.TxnFilterOperators;
import com.dk.expenseTracker.model.TxnFilterType;
import com.dk.expenseTracker.request.CreateTxnRequest;
import com.dk.expenseTracker.response.AnalyticalResponse;
import com.dk.expenseTracker.response.CreateTxnResponse;
import com.dk.expenseTracker.response.GenericResponse;
import com.dk.expenseTracker.response.TxnSearchResponse;
import com.dk.expenseTracker.service.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/expenseTracker")
public class TxnController {

    @Autowired
    private TxnService txnService;
    @PostMapping("/addUserTxn")
    public GenericResponse<CreateTxnResponse> addUserTxn (@Valid @RequestBody CreateTxnRequest createTxnRequest){
        CreateTxnResponse createTxnResponse = txnService.addUserTxn(createTxnRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createTxnResponse)
                .build();
        return genericResponse;

    }

    // if I do not have any business logic (you do not need to create service class, just controller -> repository)
    // custom method: which will filter all the things
    // cost if cost == something, cost >= something, cost <= something
    // expenseType expenseType == this
    // expenseDate expenseDate == this

    @GetMapping("/fetchTxn")
    public GenericResponse<List<TxnSearchResponse>> fetchUserTxnDetails(@RequestParam("filter")TxnFilterType txnFilterType,
                                                                        @RequestParam("operator")TxnFilterOperators txnFilterOperators,
                                                                        @RequestParam("values")String value) throws ParseException {
        // I have to create an array from comma separated
        String[] values = value.split(",");
        List<TxnSearchResponse> ListOfTxnSearchResp = txnService.fetchUserTxnDetails(txnFilterType,txnFilterOperators,values);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved"). // you have to create one enum and you should pass that enum only not the static value.
                statusCode(0).data(ListOfTxnSearchResp)
                .build();
        return genericResponse;
    }

    // api -> one day expense, 7 days expense, 15 days expense, 30 days expense
    @GetMapping("/fetchCalculatedResults")
    public GenericResponse<AnalyticalResponse> fetchCalculatedResponse(@RequestParam("email")String email){
        AnalyticalResponse analyticalResponse = txnService.fetchCalculatedResponse(email);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved"). // you have to create one enum and you should pass that enum only not the static value.
                        statusCode(0).data(analyticalResponse)
                .build();
        return genericResponse;
    }
}
