package de.motivational.stairs.database.entity;

import javax.persistence.*;

/**
 * Created by Florian on 24.11.2016.
 */
@Entity
@Table(name = "highscore", schema = "motivationalstairs")
public class HighscoreEntity {
    private int highscoreId;
    private int highscore;
    private GameEntity gameByGameId;
    private UserEntity userByUserId;

    @Id
    @Column(name = "highscoreId")
    public int getHighscoreId() {
        return highscoreId;
    }

    public void setHighscoreId(int highscoreId) {
        this.highscoreId = highscoreId;
    }

    @Basic
    @Column(name = "highscore")
    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HighscoreEntity that = (HighscoreEntity) o;

        if (highscoreId != that.highscoreId) return false;
        if (highscore != that.highscore) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = highscoreId;
        result = 31 * result + highscore;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "gameId", referencedColumnName = "gameId", nullable = false)
    public GameEntity getGameByGameId() {
        return gameByGameId;
    }

    public void setGameByGameId(GameEntity gameByGameId) {
        this.gameByGameId = gameByGameId;
    }

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = true)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HighscoreEntity{");
        sb.append("highscoreId=").append(highscoreId);
        sb.append(", highscore=").append(highscore);
        sb.append(", gameByGameId=").append(gameByGameId);
        sb.append(", userByUserId=").append(userByUserId);
        sb.append('}');
        return sb.toString();
    }
}
