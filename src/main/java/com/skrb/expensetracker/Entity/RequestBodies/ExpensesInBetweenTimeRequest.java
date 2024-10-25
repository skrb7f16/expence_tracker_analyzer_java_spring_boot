package com.skrb.expensetracker.Entity.RequestBodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpensesInBetweenTimeRequest {
    private LocalDateTime earliest;
    private  LocalDateTime min;
}
