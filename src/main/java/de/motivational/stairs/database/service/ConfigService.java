package de.motivational.stairs.database.service;

import de.motivational.stairs.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Florian on 12.01.2017.
 */
@Service
public class ConfigService {
    @Autowired
    AppConfig appConfig;

    public void update(AppConfig appConfig){
        this.appConfig.update(appConfig);
    }

    public AppConfig getAppConfig(){
        return appConfig;
    }
}
