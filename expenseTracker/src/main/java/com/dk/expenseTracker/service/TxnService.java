package com.dk.expenseTracker.service;

import com.dk.expenseTracker.model.*;
import com.dk.expenseTracker.repository.ExpenseTypeRepository;
import com.dk.expenseTracker.repository.TxnDetailsRepository;
import com.dk.expenseTracker.repository.UserRepository;
import com.dk.expenseTracker.request.CreateTxnRequest;
import com.dk.expenseTracker.response.AnalyticalResponse;
import com.dk.expenseTracker.response.CreateTxnResponse;
import com.dk.expenseTracker.response.TxnSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TxnService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private TxnDetailsRepository txnDetailsRepository;
    public CreateTxnResponse addUserTxn(CreateTxnRequest createTxnRequest) {
        // add user if not exist -> email
        // expense type -> if it will not be there
        // txn in txndetails

        User userFromDb = userRepository.findByEmail(createTxnRequest.getUserEmail());
        if(userFromDb == null){
            User user = User.builder().
                    email(createTxnRequest.getUserEmail()).
                    userStatus(UserStatus.Active).
                    build();
            userFromDb = userRepository.save(user);
        }
        // you create this if conditon in CreateTxnRequest class itself, so the length of the code in this method reduces.

        ExpenseTypes expenseTypesFromDb = expenseTypeRepository.findByExpenseType(createTxnRequest.getExpenseType());
        if(expenseTypesFromDb == null){
            ExpenseTypes expenseTypes = ExpenseTypes.builder().
                    expenseType(createTxnRequest.getExpenseType()).
                    createdBy(createTxnRequest.getUserEmail()).
                    build();
            expenseTypesFromDb = expenseTypeRepository.save(expenseTypes);
        }

        TxnDetails txnDetails = createTxnRequest.toTxnDetails(createTxnRequest);
        txnDetails.setUser(userFromDb);
        txnDetails.setExpenseTypes(expenseTypesFromDb);
        // whenever we are doing "set", we are setting the data in the Hibernate.
        TxnDetails txnDetailsFromDb = txnDetailsRepository.save(txnDetails);
        // whenever we are doing "save", only the id got saved.
        CreateTxnResponse createTxnResponse = CreateTxnResponse.builder().
                userId(userFromDb.getId()).
                expenseId(expenseTypesFromDb.getId()).
                build();
        return createTxnResponse;
    }


    public List<TxnSearchResponse> fetchUserTxnDetails(TxnFilterType txnFilterType, TxnFilterOperators txnFilterOperators, String[] values) throws ParseException {
        List<TxnSearchResponse> list = new ArrayList<>();
        List<TxnDetails> txnDetailsList = new ArrayList<>();
        // First of all I will be getting expense ids basis on expense types.
        List<ExpenseTypes> expenseIds = expenseTypeRepository.findByExpenseTypeIn(values);
//        // Create an Integer array to store the expense IDs
//        Integer[] expenseIdArray = new Integer[expenseIds.size()];
//
//        // Populate the expenseIdArray by iterating through expenseIds
//        for (int i = 0; i < expenseIds.size(); i++) {
//            expenseIdArray[i] = expenseIds.get(i).getId();
//        }

        switch (txnFilterType){
            case EXPENSE_TYPE:  //food, it uses only equals
                txnDetailsList.addAll(txnDetailsRepository.findByExpenseTypesIn(values));
//                txnDetailsList.addAll(txnDetailsRepository.findByExpenseTypesId(expenseIdArray));
                break;
            case EXPENDITURE_AMOUNT:
                switch (txnFilterOperators){
                    case EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmount(Double.valueOf(values[0])));
                        break;
//                    case LESS_THAN:
//                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountLessThan(Double.valueOf(values[0])));
//                        break;
//                    case GREATER_THAN:
//                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountGreaterThan(Double.valueOf(values[0])));
//                        break;
//                    case BETWEEN:
//                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountBetween(Double.valueOf(values[0])));
//                        break;
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountLessThanEqual(Double.valueOf(values[0])));
                        break;
//                    case GREATER_THAN_EQUALS:
//                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountGreaterThanEquals(Double.valueOf(values[0])));
//                        break;
                }
                break;
            case EXPENSE_DATE:
                switch (txnFilterOperators){
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateLessThanEqual(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                }
                break;
        }
        list = convertToSearchResponse(txnDetailsList);
        

        return list;
    }

    private List<TxnSearchResponse> convertToSearchResponse(List<TxnDetails> txnDetailsList) {
        List<TxnSearchResponse> txnSearchResponses = new ArrayList<>();
        for(int i=0;i< txnDetailsList.size();i++){
            TxnSearchResponse txnSearchResponse = TxnSearchResponse.builder().
                    user(txnDetailsList.get(i).getUser()).
                    expenditureAmount(txnDetailsList.get(i).getExpenditureAmount()).
                    expenseDate(txnDetailsList.get(i).getExpenseDate().toString()).
                    expenseType(txnDetailsList.get(i).getExpenseTypes().getExpenseType()).
                    build();
            txnSearchResponses.add(txnSearchResponse);
        }
        return txnSearchResponses;
    }

    public AnalyticalResponse fetchCalculatedResponse(String email) {
        // form the date
        LocalDate todayDate = LocalDate.now();
        LocalDate sevenDayBackDate = LocalDate.now().minusDays(7);
        //LocalDate oneMonthBackDate = LocalDate.now().minusDays(30);
        // select sum(expenditure_cost) from txnDetails where expenseDate >= Date and userId = userId
        User user = userRepository.findByEmail(email);
        Double oneDayAmount = txnDetailsRepository.getAggregatedData(todayDate, user.getId());
        Double sevenDayAmount = txnDetailsRepository.getAggregatedData(sevenDayBackDate, user.getId());
        return AnalyticalResponse.builder().
                userEmail(email).
                ondDayAmount(oneDayAmount).
                sevenDayAmount(sevenDayAmount).
                build();

    }
}
