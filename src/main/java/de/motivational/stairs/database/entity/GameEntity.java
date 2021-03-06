package de.motivational.stairs.database.entity;

import javax.persistence.*;

/**
 * Created by Florian on 27.12.2016.
 */
@Entity
@Table(name = "game", schema = "motivationalstairs")
public class GameEntity {
    private String name;
    private int gameId;
    private String subtitle;
    private String description;
    private int gameModes;
    private String imagePath;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "game_id")
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + gameId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameEntity{");
        sb.append("name='").append(name).append('\'');
        sb.append(", gameId=").append(gameId);
        sb.append('}');
        return sb.toString();
    }

    @Basic
    @Column(name = "subtitle")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "game_modes")
    public int getGameModes() {
        return gameModes;
    }

    public void setGameModes(int gameModes) {
        this.gameModes = gameModes;
    }

    @Basic
    @Column(name = "image_path")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
