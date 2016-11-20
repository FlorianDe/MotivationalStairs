package de.motivational.stairs.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Florian on 20.11.2016.
 */
@Entity
@Table(name = "beamer", schema = "motivationalstairs")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeamerEntity {
    private int beamerId;
    private String beamerName;
    private double angle;
    private int width;
    private int height;
    private Collection<BeamerSetupEntity> beamerSetupsByBeamerId;

    @Id
    @Column(name = "beamer_id")
    public int getBeamerId() {
        return beamerId;
    }

    public void setBeamerId(int beamerId) {
        this.beamerId = beamerId;
    }

    @Basic
    @Column(name = "beamer_name")
    public String getBeamerName() {
        return beamerName;
    }

    public void setBeamerName(String beamerName) {
        this.beamerName = beamerName;
    }

    @Basic
    @Column(name = "angle")
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Basic
    @Column(name = "width")
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height")
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeamerEntity that = (BeamerEntity) o;

        return beamerId == that.beamerId;

    }

    @Override
    public int hashCode() {
        return beamerId;
    }

    @OneToMany(mappedBy = "beamerByBeamerId")
    public Collection<BeamerSetupEntity> getBeamerSetupsByBeamerId() {
        return beamerSetupsByBeamerId;
    }

    public void setBeamerSetupsByBeamerId(Collection<BeamerSetupEntity> beamerSetupsByBeamerId) {
        this.beamerSetupsByBeamerId = beamerSetupsByBeamerId;
    }
}
