package com.skrb.expensetracker.Entity.RequestBodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseFetchByAnyParams {
    public HashMap<String, String> param;
}
