package com.skrb.expensetracker.services;


import com.skrb.expensetracker.Entity.Model.Expense;
import com.skrb.expensetracker.Entity.Model.ExpenseUser;
import com.skrb.expensetracker.Entity.Model.TypeOfExpense;
import com.skrb.expensetracker.Entity.RequestBodies.ExpenseRequest;
import com.skrb.expensetracker.Entity.RequestBodies.ExpensebetweenAmountRangeRequest;
import com.skrb.expensetracker.Entity.RequestBodies.ExpensesInBetweenTimeRequest;
import com.skrb.expensetracker.Repository.ExpenseRepository;

import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Service
@AllArgsConstructor
public class ExpenseCrudService {

    @Autowired
    private  ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return expenseRepository.findExpensesByBy((ExpenseUser) user);


    }


    public String addAnExpense(ExpenseRequest request){
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Expense expense=Expense.builder().by((ExpenseUser)user).typeOfExpense(request.getTypeOfExpense()).amount(request.getAmount())
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

    public List<Expense> getAllExpensesInBetweenTimeFrame(ExpensesInBetweenTimeRequest request) {
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Expense> expenses = expenseRepository.findExpensesForTimeRange(request.getEarliest(),request.getMin(), (ExpenseUser) user);
        System.out.println(expenses);
        return expenses;
    }

    public  List<Expense> getAllExpensesBetweenAmountRange(ExpensebetweenAmountRangeRequest request){
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return expenseRepository.findExpensesForAmountRange(request.getMin(), request.getMax(), (ExpenseUser) user);
    }

    public HashMap<TypeOfExpense, Integer> getAllExpenseTypeWise() {
        Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Tuple> results= expenseRepository.getTypeWise((ExpenseUser) user);
        HashMap<TypeOfExpense, Integer> typeWiseMap = new HashMap<>();
        for (Tuple result : results) {
            System.out.println(result.get(0));
            typeWiseMap.put((TypeOfExpense)result.get(0), ((Long) result.get(1)).intValue());
        }
        return typeWiseMap;
    }
}
