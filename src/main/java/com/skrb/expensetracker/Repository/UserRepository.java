package com.skrb.expensetracker.Repository;

import com.skrb.expensetracker.Entity.Model.ExpenseUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ExpenseUser,Long> {
     ExpenseUser findByUsername(String username);
}
