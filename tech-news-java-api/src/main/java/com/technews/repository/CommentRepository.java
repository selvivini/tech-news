package com.technews.repository;

import com.technews.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
List<Comment> findAllCommentsByPostId(int postId) throws Exception;
}

