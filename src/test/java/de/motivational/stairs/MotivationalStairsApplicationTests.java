package de.motivational.stairs;

import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.repository.BeamerSetupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotivationalStairsApplicationTests {

    @Autowired
    BeamerSetupService beamerSetupService;

	@Test
	public void contextLoads() {
        Optional<BeamerSetupEntity> beamerSetupEntity = beamerSetupService.findOne(3);
        if(beamerSetupEntity.isPresent()) {
            System.out.println(beamerSetupEntity.get().getBeamerByBeamerId());
        }
	}

}
