package com.accesodatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
