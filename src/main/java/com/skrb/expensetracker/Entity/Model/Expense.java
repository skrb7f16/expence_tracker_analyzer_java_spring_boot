package com.skrb.expensetracker.Entity.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long expense_id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String reason;

    private LocalDateTime date= LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TypeOfExpense typeOfExpense;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User by;
}
