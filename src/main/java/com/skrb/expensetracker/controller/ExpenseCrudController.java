package com.skrb.expensetracker.controller;

import com.skrb.expensetracker.Entity.Model.Expense;
import com.skrb.expensetracker.Entity.Model.TypeOfExpense;
import com.skrb.expensetracker.Entity.RequestBodies.ExpenseFetchByAnyParams;
import com.skrb.expensetracker.Entity.RequestBodies.ExpenseRequest;
import com.skrb.expensetracker.Entity.RequestBodies.ExpensebetweenAmountRangeRequest;
import com.skrb.expensetracker.Entity.RequestBodies.ExpensesInBetweenTimeRequest;
import com.skrb.expensetracker.services.ExpenseCrudService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/api/v1/expense")
@AllArgsConstructor
public class ExpenseCrudController {


    @Autowired
    private ExpenseCrudService expenseCrudService;

    @GetMapping("/get-all-expenses")
    public ResponseEntity<List<Expense>> getAllTheExpenses(){

        return ResponseEntity.ok(expenseCrudService.getAllExpenses());
    }

    @PostMapping("/add-an-expense")
    public ResponseEntity<String> addAnExpense(@RequestBody ExpenseRequest request){
        return ResponseEntity.ok(expenseCrudService.addAnExpense(request));
    }

    @PutMapping("/update-an-expense")
    public ResponseEntity<String>updateAnExpense(@RequestBody ExpenseRequest request){
        return ResponseEntity.ok(expenseCrudService.updateAnExpense(request));
    }

    @DeleteMapping("/delete-an-expense/{id}")
    public ResponseEntity<String>deleteAnExpense(@PathVariable long id){

        return ResponseEntity.ok(expenseCrudService.deleteAnExpense(id));
    }

    @PostMapping("/get-expense-for-time-frame")
    public ResponseEntity<List<Expense>> getAllTheExpenseBetweenTime(@RequestBody ExpensesInBetweenTimeRequest request){

        return ResponseEntity.ok(expenseCrudService.getAllExpensesInBetweenTimeFrame(request));
    }

    @PostMapping("/get-expense-for-amount-range")
    public ResponseEntity<List<Expense>> getExpenseForAmountRange(@RequestBody ExpensebetweenAmountRangeRequest request){
        return ResponseEntity.ok(expenseCrudService.getAllExpensesBetweenAmountRange(request));
    }

    @GetMapping("/get-all-expenses-type-wise")
    public ResponseEntity<HashMap<TypeOfExpense, Integer>> getExpenseTypeWise() {
        return ResponseEntity.ok(expenseCrudService.getAllExpenseTypeWise());
    }

    @PostMapping("/get-all-expenses")
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestBody ExpenseFetchByAnyParams request) {
        return ResponseEntity.ok(expenseCrudService.getExpenseBasedOnAny(request));
    }

}
