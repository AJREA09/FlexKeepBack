package com.puce.backendProyectoFinal.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puce.backendProyectoFinal.model.Expense;
import com.puce.backendProyectoFinal.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/categories") //Get all categories of an expense
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = expenseService.getPredefinedCategories();
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping("/create") //Create expense
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest request) {
        try {
            Expense expense = expenseService.createExpense(
                    request.getUsername(),
                    request.getAmount(),
                    request.getCategory(),
                    request.getExpenseDate()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(expense);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/update/{expenseId}") //Update an expense of the user
    public ResponseEntity<?> updateExpense(
            @PathVariable Long expenseId,
            @RequestBody ExpenseRequest request) {
        try {
            Expense updatedExpense = expenseService.updateExpense(
                    expenseId,
                    request.getAmount(),
                    request.getCategory(),
                    request.getExpenseDate()
            );
            return ResponseEntity.ok(updatedExpense);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all/{username}") //Get all expenses of the user
    public ResponseEntity<?> getAllExpenses(@PathVariable String username) {
        try {
            List<Expense> expenses = expenseService.getAllExpenses(username);
            return ResponseEntity.ok(expenses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{expenseId}") //Delete an expense once at a time
    public ResponseEntity<String> deleteExpense(@PathVariable Long expenseId) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok("Gasto eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/monthly-trend-by-category/{username}") //Get the metrics of the flow of expenses by month
    public ResponseEntity<?> getMonthlyExpenseTrendByCategory(@PathVariable String username) {
        try {
            Map<String, List<Map<String, Object>>> trend = expenseService.getMonthlyExpenseTrendByCategory(username);
            return ResponseEntity.ok(trend);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    public static class ExpenseRequest {
        private String username;
        private BigDecimal amount;
        private String category;
        private LocalDate expenseDate;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public LocalDate getExpenseDate() { return expenseDate; }
        public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }
    }
    

}	

