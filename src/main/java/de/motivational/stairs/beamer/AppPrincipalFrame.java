package de.motivational.stairs.beamer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Florian on 11.11.2016.
 */
@Component
public class AppPrincipalFrame extends JFrame implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        this.setVisible(true);
    }

    public String setTitleTestMethod(String title){
        this.setTitle(title);
        return title;
    }
}