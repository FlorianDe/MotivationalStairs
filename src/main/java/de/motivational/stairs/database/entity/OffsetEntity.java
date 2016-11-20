package de.motivational.stairs.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Florian on 20.11.2016.
 */
@Entity
@Table(name = "offset", schema = "motivationalstairs")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OffsetEntity {
    private int offsetId;
    private int x;
    private int y;
    private int z;
    private int yaw;
    private int pitch;
    private int roll;
    private Collection<BeamerSetupEntity> beamerSetupsByOffsetId;
    private Collection<BeamerSetupEntity> beamerSetupsByOffsetId_0;

    @Id
    @Column(name = "offset_id")
    public int getOffsetId() {
        return offsetId;
    }

    public void setOffsetId(int offsetId) {
        this.offsetId = offsetId;
    }

    @Basic
    @Column(name = "x")
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Basic
    @Column(name = "z")
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Basic
    @Column(name = "yaw")
    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    @Basic
    @Column(name = "pitch")
    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    @Basic
    @Column(name = "roll")
    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OffsetEntity that = (OffsetEntity) o;

        return offsetId == that.offsetId;

    }

    @Override
    public int hashCode() {
        return offsetId;
    }

    // TODO warum is das genau hier? Sieht mir aus als würde es alle beamer setups für eine offset_id zurückgeben, brauchen wir eig nicht

    @OneToMany(mappedBy = "offsetByStairsOffsetId")
    public Collection<BeamerSetupEntity> getBeamerSetupsByOffsetId() {
        return beamerSetupsByOffsetId;
    }

    public void setBeamerSetupsByOffsetId(Collection<BeamerSetupEntity> beamerSetupsByOffsetId) {
        this.beamerSetupsByOffsetId = beamerSetupsByOffsetId;
    }

    @OneToMany(mappedBy = "offsetByBeamerOffsetId")
    public Collection<BeamerSetupEntity> getBeamerSetupsByOffsetId_0() {
        return beamerSetupsByOffsetId_0;
    }

    public void setBeamerSetupsByOffsetId_0(Collection<BeamerSetupEntity> beamerSetupsByOffsetId_0) {
        this.beamerSetupsByOffsetId_0 = beamerSetupsByOffsetId_0;
    }

}
