package com.dk.expenseTracker.request;

import com.dk.expenseTracker.model.ExpenseTypes;
import com.dk.expenseTracker.model.TxnDetails;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTxnRequest {

    @NotBlank(message = "expense type can't be blank")
    private String userEmail;

    @NotBlank(message = "expense type can't be blank")
    private String expenseType;

    private Double expenditureCost;

    private LocalDate expenseDate;

    private String expenseNote;


    public TxnDetails toTxnDetails(CreateTxnRequest createTxnRequest) {
        return TxnDetails.builder().
                expenditureAmount(this.expenditureCost).
                expenseDate(this.expenseDate).
                expenseNote(this.expenseNote).
                build();
    }
}
