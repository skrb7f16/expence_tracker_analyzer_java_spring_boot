package com.skrb.expensetracker.Entity.RequestBodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpensebetweenAmountRangeRequest {
    private double min;
    private double max;
}
