package com.exalt.reddit.repositories;

import com.exalt.reddit.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

}
