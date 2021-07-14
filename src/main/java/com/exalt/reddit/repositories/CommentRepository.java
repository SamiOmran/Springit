package com.exalt.reddit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exalt.reddit.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
