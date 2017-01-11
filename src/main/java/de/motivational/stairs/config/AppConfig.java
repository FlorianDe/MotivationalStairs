package de.motivational.stairs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Florian on 11.01.2017.
 */
@Component
public class AppConfig {
    @Autowired
    private PongConfig pongConfig;
    @Autowired
    private GameConfig gameConfig;

    public AppConfig(){

    }

    public PongConfig getPongConfig() {
        return pongConfig;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppConfig{");
        sb.append("pongConfig=").append(pongConfig);
        sb.append(", gameConfig=").append(gameConfig);
        sb.append('}');
        return sb.toString();
    }

    @PostConstruct
    public void init() {
        System.out.println(this.toString());
    }
}
