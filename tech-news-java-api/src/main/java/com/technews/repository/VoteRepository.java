package com.technews.repository;

import com.technews.models.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT COUNT(*) FROM vote v WHERE v.postId = :id ")
    int countVotesByPostId(@Param("id") Integer id ) ;

}
