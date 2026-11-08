package org.example.stsr_6.Repositories;

import org.example.stsr_6.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Spring автоматически поймет, что нужно сделать SELECT * FROM users WHERE login = ?
    User findByLogin(String login);
    User findByEmail(String email);
}