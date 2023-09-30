package com.dk.expenseTracker.request;

import com.dk.expenseTracker.model.ExpenseTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExpenseTypeRequest {
    @NotBlank(message = "expense type can't be blank")
    String expenseType;

    @NotBlank(message = "user email can't be blank")
    String userEmail;

    public ExpenseTypes toExpenseTypes() {
        return ExpenseTypes.builder().
                expenseType(this.expenseType).
                createdBy(this.userEmail).
                build();
    }
}
