package com.dk.expenseTracker.model;

public enum UserStatus {
    Active(1),
    Inactive(0),
    Blocked(2);

    private Integer userVal;
    UserStatus(Integer userVal){
        this.userVal = userVal;
    }
}
