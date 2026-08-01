package com.puce.backendProyectoFinal.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puce.backendProyectoFinal.model.Expense;
import com.puce.backendProyectoFinal.model.User;
import com.puce.backendProyectoFinal.model.repository.ExpenseRepository;
import com.puce.backendProyectoFinal.model.repository.UserRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;


    public List<String> getPredefinedCategories() {
        return Arrays.asList("Alimentación", "Vestimenta", "Deudas", "Arriendo", "Servicios", "Otros");
    }

    public Expense createExpense(String username, BigDecimal amount, String category, LocalDate expenseDate) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense(user, amount, category, expenseDate);
        return expenseRepository.save(expense);
    }
    
    public Expense updateExpense(Long expenseId, BigDecimal amount, String category, LocalDate expenseDate) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setExpenseDate(expenseDate);

        return expenseRepository.save(expense);
    }
    
    public List<Expense> getAllExpenses(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUser(user);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }
    
    public Map<String, List<Map<String, Object>>> getMonthlyExpenseTrendByCategory(String username) {
        List<Object[]> monthlyExpenses = expenseRepository.findMonthlyExpensesByCategory(username);

        Map<String, List<Map<String, Object>>> trend = new LinkedHashMap<>();

        for (Object[] result : monthlyExpenses) {
            int year = (int) result[0];
            int month = (int) result[1];
            String category = (String) result[2];
            BigDecimal total = (BigDecimal) result[3];

            String monthKey = String.format("%d-%02d", year, month);

            trend.computeIfAbsent(monthKey, k -> new ArrayList<>()).add(Map.of(
                "category", category,
                "total", total
            ));
        }

        return trend;
    }
    
}

