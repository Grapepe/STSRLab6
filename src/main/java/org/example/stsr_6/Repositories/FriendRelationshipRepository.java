package org.example.stsr_6.Repositories;

import org.example.stsr_6.Entities.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Integer> {
}