package de.motivational.stairs.database.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Florian on 24.11.2016.
 */
@Entity
@Table(name = "game", schema = "motivationalstairs")
public class GameEntity {
    private int gameId;
    private String name;
    private Collection<HighscoreEntity> highscoresByGameId;

    @Id
    @Column(name = "gameId")
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        if (gameId != that.gameId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "gameByGameId")
    public Collection<HighscoreEntity> getHighscoresByGameId() {
        return highscoresByGameId;
    }

    public void setHighscoresByGameId(Collection<HighscoreEntity> highscoresByGameId) {
        this.highscoresByGameId = highscoresByGameId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameEntity{");
        sb.append("gameId=").append(gameId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", highscoresByGameId=").append(highscoresByGameId);
        sb.append('}');
        return sb.toString();
    }
}
