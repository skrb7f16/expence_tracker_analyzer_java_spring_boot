package com.skrb.expensetracker.Repository;

import com.skrb.expensetracker.Entity.Model.Expense;
import com.skrb.expensetracker.Entity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findExpensesByBy(User user);

}
