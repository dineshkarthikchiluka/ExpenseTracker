package com.dk.expenseTracker.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "txnDetails")
public class TxnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double expenditureAmount;
    @ManyToOne // one user can have multiple transactions, but one transaction cant be of 2 users.
    @JoinColumn // without this annotation user data will be there in this class, but this data will not be present in database table of txnDetails.
    private User user;

    @ManyToOne
    @JoinColumn
    private ExpenseTypes expenseTypes;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private LocalDate expenseDate; //LocalDateTime is also available

    private String expenseNote;

}
