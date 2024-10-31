package com.skrb.expensetracker.Repository;

import com.skrb.expensetracker.Entity.Model.Expense;

import com.skrb.expensetracker.Entity.Model.ExpenseUser;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findExpensesByBy(ExpenseUser expenseUser);


    @Query("select e from Expense e where e.date > ?1 and e.date < ?2")
    List<Expense> findExpensesForTimeRange(LocalDateTime earliest, LocalDateTime min, ExpenseUser user);

    @Query("Select e from Expense e where e.amount > ?1 and e.amount < ?2 and by = ?3")
    List<Expense> findExpensesForAmountRange(double min, double max, ExpenseUser user);

    @Query("select e.typeOfExpense, count(e)  from Expense e where e.by = ?1  group by e.typeOfExpense")
    List<Tuple> getTypeWise(ExpenseUser user);

}
