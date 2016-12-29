package de.motivational.stairs.game.general;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.UserEntity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by viktorspadi on 29.12.16.
 */
public class GameTicket {

    private final String ticket;
    private final GameEntity game;
    private final List<UserEntity> users;

    public GameTicket(GameEntity game, List<UserEntity> users) {
        MessageDigest md = null;
        this.game = game;
        this.users = users;
        String t = "";
        try {
            md = MessageDigest.getInstance("SHA-256");
            String text = new StringBuilder()
                    .append(new Timestamp(System.currentTimeMillis()).toString())
                    .append(Math.random())
                    .append(game.getGameId())
                    .append(users.size())
                    .toString();
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            t = String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.ticket = t;
    }

    public String getTicket() {
        return ticket;
    }

    public GameEntity getGame() {
        return game;
    }

    public List<UserEntity> getUsers() {
        return users;
    }
}
