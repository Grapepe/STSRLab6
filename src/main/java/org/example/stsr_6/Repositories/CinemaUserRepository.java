package org.example.stsr_6.Repositories;

import org.example.stsr_6.Entities.Cinema;
import org.example.stsr_6.Entities.CinemaUser;
import org.example.stsr_6.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaUserRepository extends JpaRepository<CinemaUser, Integer> {
    // Ищет связь между конкретным фильмом и конкретным пользователем
    CinemaUser findByCinemasAndUsers(Cinema cinemas, User users);
}