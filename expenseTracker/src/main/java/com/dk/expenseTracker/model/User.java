package com.dk.expenseTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // we mostly use id as Integer, because indexing is always done on id
    private Integer id;  //-> If you use UUID, in this case long string sorting and indexing becomes difficult (alphanumeric and unic)

    @Column(length = 30)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 15,unique = true)
    private String contact;

    // CreationTimestamp & UpdateTimestamp is added by Hibernate
    @CreationTimestamp //this CreatedTimestamp do the task of private Date createdAt = new Date();
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    // user who is doing something wrong with my application, I want to block, active, inactive
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<TxnDetails> txnDetails;
    //txn details is not present in db
    // hibernate makes a query to keep this data


//    @OneToOne
//    private List<ExpenseTypes> expenseTypes;
}
