package de.motivational.stairs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Florian on 12.01.2017.
 */
@Component
public class GameConfig implements IUpdateable<GameConfig>{
    @Value("${app.constants.game.window.height}")
    public float gameWindowHeight;

    @Value("${app.constants.game.window.width}")
    public float gameWindowWidth;

    @Value("${app.constants.game.redeem.seconds}")
    public int gameRedeemSeconds;

    @Value("${app.constants.game.start.seconds}")
    public int gameStartSeconds;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameConfig{");
        sb.append("gameWindowHeight=").append(gameWindowHeight);
        sb.append(", gameWindowWidth=").append(gameWindowWidth);
        sb.append(", gameRedeemSeconds=").append(gameRedeemSeconds);
        sb.append(", gameStartSeconds=").append(gameStartSeconds);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void update(GameConfig newConfig) {
        this.gameWindowHeight = newConfig.gameWindowHeight;
        this.gameWindowWidth = newConfig.gameWindowWidth;
        this.gameRedeemSeconds = newConfig.gameRedeemSeconds;
        this.gameStartSeconds = newConfig.gameStartSeconds;
    }
}
