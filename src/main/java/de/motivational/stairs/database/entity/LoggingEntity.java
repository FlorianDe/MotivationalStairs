package de.motivational.stairs.database.entity;

import javax.persistence.*;

/**
 * Created by Florian on 24.01.2017.
 */
@Entity
@Table(name = "logging", schema = "motivationalstairs", catalog = "")
public class LoggingEntity {
    private int id;
    private String session;
    private long timestamp;
    private int action;
    private String comment;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "session")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Basic
    @Column(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "action")
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoggingEntity that = (LoggingEntity) o;

        if (id != that.id) return false;
        if (timestamp != that.timestamp) return false;
        if (action != that.action) return false;
        if (session != null ? !session.equals(that.session) : that.session != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + action;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
