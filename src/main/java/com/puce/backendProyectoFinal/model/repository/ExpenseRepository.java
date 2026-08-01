package com.puce.backendProyectoFinal.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.puce.backendProyectoFinal.model.Expense;
import com.puce.backendProyectoFinal.model.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    boolean existsByUserAndCategory(User user, String category);

    @Query("SELECT YEAR(e.expenseDate) AS year, MONTH(e.expenseDate) AS month, e.category, SUM(e.amount) AS total " +
           "FROM Expense e " +
           "WHERE e.user.username = :username " +
           "GROUP BY YEAR(e.expenseDate), MONTH(e.expenseDate), e.category " +
           "ORDER BY year ASC, month ASC, total DESC")
    List<Object[]> findMonthlyExpensesByCategory(@Param("username") String username);
}