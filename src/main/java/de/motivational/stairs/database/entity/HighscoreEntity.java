package de.motivational.stairs.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Florian on 27.12.2016.
 */
@Entity
@Table(name = "highscore", schema = "motivationalstairs")
public class HighscoreEntity {
    private int highscore;
    private int highscoreId;
    private Timestamp created;
    private GameEntity gameByGameId;
    private UserEntity userByUserId;

    @Basic
    @Column(name = "highscore")
    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    @Id
    @Column(name = "highscore_id")
    public int getHighscoreId() {
        return highscoreId;
    }

    public void setHighscoreId(int highscoreId) {
        this.highscoreId = highscoreId;
    }

    @Basic
    @Column(name = "created")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HighscoreEntity that = (HighscoreEntity) o;

        if (highscore != that.highscore) return false;
        if (highscoreId != that.highscoreId) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = highscore;
        result = 31 * result + highscoreId;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    public GameEntity getGameByGameId() {
        return gameByGameId;
    }

    public void setGameByGameId(GameEntity gameByGameId) {
        this.gameByGameId = gameByGameId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HighscoreEntity{");
        sb.append("highscore=").append(highscore);
        sb.append(", highscoreId=").append(highscoreId);
        sb.append(", created=").append(created);
        sb.append(", gameByGameId=").append(gameByGameId);
        sb.append(", userByUserId=").append(userByUserId);
        sb.append('}');
        return sb.toString();
    }
}
