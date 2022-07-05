package com.springboot.games.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.games.controller.GamesController;
import com.springboot.games.entity.Games;
import com.springboot.games.repository.GamesRepository;

//service for our app where all request functions are made
@Service
public class GamesService {

	//linking aur repo with app
	@Autowired
	private GamesRepository gameRepo;
	Logger log = LogManager.getLogger(GamesService.class);
	
	
	//getting list of all the games from DB
	public List<Games> getAllGames() {
		List<Games> games = new ArrayList<>();
		gameRepo.findAll()
		.forEach(games::add);
		log.info("(Service)Here is list of your games:- " + games);
		
		return games;
	}

	//getting a single game by specific ID
	public Optional<Games> getGame(int id) {
		log.info("(Service)Here is your Game at Id = '"+id+"' ->" + gameRepo.findById(id));
		return gameRepo.findById(id);
	}

	//adding a game into DB
	public void addGame(Games game) {
		log.info("(Service)Saving your Game = " + game + " into Database");
		gameRepo.save(game);
	}

	//updating the game
	public void updateGame(Games game) {
		log.info("(Service)Updating your Game = " + game + " into Database");
		gameRepo.save(game);
	}

	public void deleteGame(int id) {
		log.info("(Service)Deleting your game by ID = '"+id+"' from Database.");
		gameRepo.deleteById(id);
	}
	
	

}
