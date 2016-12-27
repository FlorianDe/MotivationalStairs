package de.motivational.stairs;
/*
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.entity.HighscoreEntity;
import de.motivational.stairs.database.repository.BeamerSetupService;
import de.motivational.stairs.database.repository.GameService;
import de.motivational.stairs.database.repository.HighscoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotivationalStairsApplicationTests {

    @Autowired
    BeamerSetupService beamerSetupService;

    @Autowired
    HighscoreService highscoreService;

    @Autowired
    GameService gameService;


    @Test
	public void contextLoads() {
        Optional<BeamerSetupEntity> beamerSetupEntity = beamerSetupService.findOne(3);
        if(beamerSetupEntity.isPresent()) {
            System.out.println(beamerSetupEntity.get().getBeamerByBeamerId());
        }
	}

    @Test
    public void highscoreTest() {
        highscoreService.findAll().forEach(System.out::println);
    }

    @Test
    public void gameTest() {
        gameService.findAll().forEach(System.out::println);
    }

    @Test
    @Repeat(10)
    public void createHighscoresWithoutUser() {
        int gameId = 1;
        int highscore = new Random().nextInt(10000);
        highscoreService.create(highscore,gameId);
    }

}
*/
