package com.dk.expenseTracker.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse {
    private Integer userId;
}
