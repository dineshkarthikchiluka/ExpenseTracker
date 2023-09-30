package com.dk.expenseTracker.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalResponse {
    private String userEmail;
    private Double ondDayAmount;
    private Double sevenDayAmount;
    private Double fifteenDayAmount;


}
// here we are hard-coding days whatever we wanted, but in real user has to select the days which he wanted
// so, there should be an api on which user will be selecting I want this number of days
// then there is an api that we are creating which will be returning data according to the user's wish