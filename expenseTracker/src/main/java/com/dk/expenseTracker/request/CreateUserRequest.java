package com.dk.expenseTracker.request;

import com.dk.expenseTracker.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateUserRequest {
    @NotBlank(message = "user email should not blank")
    private String email;

    private String name;

    @NotNull(message = "user contact can't be null")
    private String contact;

    public User toUser() {
        return User.builder().
                name(this.name).
                email(this.email).
                contact(this.contact).
                build();
    }
}
