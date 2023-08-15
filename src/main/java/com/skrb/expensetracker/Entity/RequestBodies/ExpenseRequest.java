package com.skrb.expensetracker.Entity.RequestBodies;

import com.skrb.expensetracker.Entity.Model.TypeOfExpense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {

    private  long id;
    private double amount;
    private String reason;
    private TypeOfExpense typeOfExpense;
}
