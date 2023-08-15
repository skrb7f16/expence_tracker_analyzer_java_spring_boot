package com.skrb.expensetracker.services;


import com.skrb.expensetracker.Entity.Model.Expense;
import com.skrb.expensetracker.Entity.Model.User;
import com.skrb.expensetracker.Entity.RequestBodies.ExpenseRequest;
import com.skrb.expensetracker.Repository.ExpenseRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class ExpenseCrudService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var res=expenseRepository.findExpensesByBy((User) user);

        return res;
    }


    public String addAnExpense(ExpenseRequest request){
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Expense expense=Expense.builder().by((User)user).typeOfExpense(request.getTypeOfExpense()).amount(request.getAmount())
                .reason(request.getReason()).date(LocalDateTime.now()).build();
        expenseRepository.save(expense);
        return "Success";
    }

    public String updateAnExpense(ExpenseRequest request) {
        Expense expense=expenseRepository.findById(request.getId()).get();
        if(expense==null){
            return "NOT FOUND";
        }

        expense.setDate(LocalDateTime.now());
        expenseRepository.save(expense);
        return "updated";
    }

    public String deleteAnExpense(long id){
        expenseRepository.delete(expenseRepository.findById(id).get());
        return "Deleted";
    }
}
