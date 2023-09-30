package com.dk.expenseTracker.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse<T> {  // T is nothing but generic type that you are passing while creating the class of Generic Response
    private Integer statusCode; // 0 -> Successful 1 -> Failure
    private String message;
    private Integer code;
    private T data; //data will vary according to the response from different apis

}
