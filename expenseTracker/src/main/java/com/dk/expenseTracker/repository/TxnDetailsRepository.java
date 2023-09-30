package com.dk.expenseTracker.repository;

import com.dk.expenseTracker.model.TxnDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TxnDetailsRepository extends JpaRepository<TxnDetails, Integer> {
    // I am using standard way of writing the methods

    public List<TxnDetails> findByExpenseTypesIn(String[] expenseType);
    public List<TxnDetails> findByExpenseTypesId(Integer[] expenseTypeIds);

    public List<TxnDetails> findByExpenditureAmount(Double value);
//    public List<TxnDetails> findByExpenditureAmountLessThan(Double value);
//    public List<TxnDetails> findByExpenditureAmountGreaterThan(Double value);
//    public List<TxnDetails> findByExpenditureAmountBetween(Double value);
    public List<TxnDetails> findByExpenditureAmountLessThanEqual(Double value);
//    public List<TxnDetails> findByExpenditureAmountGreaterThanEqual(Double value);
    public List<TxnDetails> findByExpenseDateLessThanEqual(Date date);

    // we do not have support for aggregated amount in jpa
    @Query(value = "select SUM(t.expenditure_amount) from txn_details t inner join user u where t.user_id = u.id and t.expense_date >= :date and u.id = :userId", nativeQuery = true)
    public Double getAggregatedData(LocalDate date, Integer userId);
}
