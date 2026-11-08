package org.example.stsr_6.Repositories;

import org.example.stsr_6.Entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {
}