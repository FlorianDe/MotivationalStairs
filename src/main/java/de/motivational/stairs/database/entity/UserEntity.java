package de.motivational.stairs.database.entity;

import javax.persistence.*;

/**
 * Created by Florian on 27.12.2016.
 */
@Entity
@Table(name = "user", schema = "motivationalstairs")
public class UserEntity {
    private String cookie;
    private int userId;
    private String name;

    @Basic
    @Column(name = "cookie")
    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (cookie != null ? !cookie.equals(that.cookie) : that.cookie != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cookie != null ? cookie.hashCode() : 0;
        result = 31 * result + userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserEntity{");
        sb.append("cookie='").append(cookie).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
