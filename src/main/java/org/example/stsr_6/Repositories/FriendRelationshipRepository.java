package org.example.stsr_6.Repositories;

import org.example.stsr_6.Entities.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Integer> {
    List<FriendRelationship> findFriendRelationshipsByUsersone_Id(Integer id);
    FriendRelationship findFriendRelationshipsByUsersone_IdAndUserstwo_Id(Integer id1, Integer id2);
}