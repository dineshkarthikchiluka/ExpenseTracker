package com.dk.expenseTracker.repository;

import com.dk.expenseTracker.model.ExpenseTypes;
import com.dk.expenseTracker.model.TxnDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseTypes, Integer> {

    public ExpenseTypes findByExpenseType(String expenseType);


    public List<ExpenseTypes> findByExpenseTypeIn(String[] expenseType);
}
