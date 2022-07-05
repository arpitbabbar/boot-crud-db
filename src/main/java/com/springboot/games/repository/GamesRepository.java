package com.springboot.games.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.games.entity.Games;

public interface GamesRepository extends CrudRepository<Games, Integer> {

	

	
	//repository for CRUD operation 
	// it implemets CrudRepository which have all thr reqd methods
}
