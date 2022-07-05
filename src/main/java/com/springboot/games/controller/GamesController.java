package com.springboot.games.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.games.entity.Games;
import com.springboot.games.service.GamesService;

//import ch.qos.logback.classic.Logger;

@RestController //rest controller for our app
public class GamesController {
	
	@Autowired
	private GamesService gameService;
	
//	org.jboss.logging.Logger logger = LoggerFactory.logger(GamesController.class);
	Logger log = LogManager.getLogger(GamesController.class);
	
	@RequestMapping("/games")
	public List<Games> getAllGames() {
//		loggger.info("Hey I am a log");
		log.info("(Controller)Here is list of your games:- " + gameService.getAllGames());
		return gameService.getAllGames();
	}
	
	@RequestMapping("/games/{id}")
	public Optional<Games> getGame(@PathVariable int id) {
		log.info("(Controller)Getting game by specific id '" + id + "' :- "+ gameService.getGame(id));
		return gameService.getGame(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/games")
	public Games addGame(@RequestBody Games game) {
		log.info("(Controller)Adding Game- " + game + " into Db");
		 gameService.addGame(game);
		 return game; //returning game just to show on postman output - earlier return type was null
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/games/{id}")
	public Games updateGame(@RequestBody Games game) {
		log.info("(Controller)Updating Game- " + game + " in Db");
		gameService.updateGame(game);
		return game; //returning game just to show on postman output - earlier return type was null
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/games/{id}")
	public String deleteGame(@PathVariable int id) {
		log.info("(Controller)Deleting Game by Id;- " + id);
		gameService.deleteGame(id);
		return ("Deleted Game with ID:- " + id); //returning id just to show on postman output - earlier return type was null
	}

}
