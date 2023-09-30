package com.dk.expenseTracker.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTxnResponse {

    private Integer userId;
    private Integer expenseId;
}
