package com.dk.expenseTracker.response;

import com.dk.expenseTracker.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnSearchResponse {
    // if you want to getters & setters only for a certain variable, then mention @Getter & @Setter inside the class on top of the respective variable.
    //@Getter
    //@Setter
    private User user;
    private Double expenditureAmount;
    private String expenseType;
    private String expenseDate;

}
