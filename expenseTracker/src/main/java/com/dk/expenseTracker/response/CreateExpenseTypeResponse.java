package com.dk.expenseTracker.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExpenseTypeResponse {

    Integer userId;
    Integer expenseId;
}
