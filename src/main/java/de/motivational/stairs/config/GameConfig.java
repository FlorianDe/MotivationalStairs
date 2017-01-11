package de.motivational.stairs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Florian on 12.01.2017.
 */
@Component
public class GameConfig {
    @Value("${app.constants.game.window.height}")
    public float gameWindowHeight;

    @Value("${app.constants.game.window.width}")
    public float gameWindowWidth;

}
