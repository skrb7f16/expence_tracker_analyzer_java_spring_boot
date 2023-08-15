package com.skrb.expensetracker.Repository;

import com.skrb.expensetracker.Entity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
     User findByUsername(String username);
}
