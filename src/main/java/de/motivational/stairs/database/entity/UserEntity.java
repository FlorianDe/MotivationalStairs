package de.motivational.stairs.database.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Florian on 24.11.2016.
 */
@Entity
@Table(name = "user", schema = "motivationalstairs")
public class UserEntity {
    private int userId;
    private String cookie;
    private Collection<HighscoreEntity> highscoresByUserId;

    @Id
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "cookie")
    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (cookie != null ? !cookie.equals(that.cookie) : that.cookie != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (cookie != null ? cookie.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<HighscoreEntity> getHighscoresByUserId() {
        return highscoresByUserId;
    }

    public void setHighscoresByUserId(Collection<HighscoreEntity> highscoresByUserId) {
        this.highscoresByUserId = highscoresByUserId;
    }
}
