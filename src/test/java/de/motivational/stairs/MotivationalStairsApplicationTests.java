package de.motivational.stairs;

import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.database.service.BeamerSetupService;
import de.motivational.stairs.database.service.GameService;
import de.motivational.stairs.database.service.HighscoreService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MotivationalStairsApplicationTests {
/*
    @PersistenceContext
    private EntityManager entityManager;

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
        highscoreService.create(highscore,gameId, -1);
    }

    @Test
    @Repeat(10)
    @Transactional
    @Commit
    public void createHighscoresWithUser() {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(UserEntity.class);
        //criteria.add(Restrictions.eq('fieldVariable', anyValue));
        criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
        criteria.setMaxResults(1);
        List<UserEntity> users = criteria.list();
        if(users.size()>0){
            int gameId = 1;
            int highscore = new Random().nextInt(10000);
            highscoreService.create(highscore,gameId, users.get(0).getUserId());
        }
    }
*/
}

