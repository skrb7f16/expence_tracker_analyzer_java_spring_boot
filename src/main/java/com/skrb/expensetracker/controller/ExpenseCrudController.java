package com.skrb.expensetracker.controller;

import com.skrb.expensetracker.Entity.Model.Expense;
import com.skrb.expensetracker.Entity.RequestBodies.ExpenseRequest;
import com.skrb.expensetracker.services.ExpenseCrudService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/v1/expense")
@AllArgsConstructor
public class ExpenseCrudController {

    private final ExpenseCrudService expenseCrudService;

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
        System.out.print(id);
        return ResponseEntity.ok(expenseCrudService.deleteAnExpense(id));
    }

}
