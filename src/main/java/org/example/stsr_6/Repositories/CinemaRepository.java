package org.example.stsr_6.Repositories;

import org.example.stsr_6.Entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer>
{
    List<Cinema> findByHeadnameAndYear(String headname, Integer year);
}