package com.dk.expenseTracker.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

// If there is a user he can add some data to ExpensesTypes without adding some transactions.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "expenseTypes")
public class ExpenseTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String expenseType;

    @CreationTimestamp
    private Date createdAt;

    private String createdBy;

    @OneToMany(mappedBy = "expenseTypes")
    private List<TxnDetails> txnDetails;
}
