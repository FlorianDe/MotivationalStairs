package de.motivational.stairs.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by Florian on 20.11.2016.
 */
@Entity
@Table(name = "beamer_setup", schema = "motivationalstairs")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeamerSetupEntity {
    private int setupId;
    private String setupName;
    private StairsEntity stairsByStairsId;
    private OffsetEntity offsetByStairsOffsetId;
    private BeamerEntity beamerByBeamerId;
    private OffsetEntity offsetByBeamerOffsetId;

    @Id
    @Column(name = "setup_id")
    public int getSetupId() {
        return setupId;
    }

    public void setSetupId(int setupId) {
        this.setupId = setupId;
    }

    @Basic
    @Column(name = "setup_name")
    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeamerSetupEntity that = (BeamerSetupEntity) o;

        return setupId == that.setupId;

    }

    @Override
    public int hashCode() {
        return setupId;
    }

    @ManyToOne
    @JoinColumn(name = "stairs_id", referencedColumnName = "stairs_id", nullable = false)
    public StairsEntity getStairsByStairsId() {
        return stairsByStairsId;
    }

    public void setStairsByStairsId(StairsEntity stairsByStairsId) {
        this.stairsByStairsId = stairsByStairsId;
    }

    @ManyToOne
    @JoinColumn(name = "stairs_offset_id", referencedColumnName = "offset_id", nullable = false)
    public OffsetEntity getOffsetByStairsOffsetId() {
        return offsetByStairsOffsetId;
    }

    public void setOffsetByStairsOffsetId(OffsetEntity offsetByStairsOffsetId) {
        this.offsetByStairsOffsetId = offsetByStairsOffsetId;
    }

    @ManyToOne
    @JoinColumn(name = "beamer_id", referencedColumnName = "beamer_id", nullable = false)
    public BeamerEntity getBeamerByBeamerId() {
        return beamerByBeamerId;
    }

    public void setBeamerByBeamerId(BeamerEntity beamerByBeamerId) {
        this.beamerByBeamerId = beamerByBeamerId;
    }

    @ManyToOne
    @JoinColumn(name = "beamer_offset_id", referencedColumnName = "offset_id", nullable = false)
    public OffsetEntity getOffsetByBeamerOffsetId() {
        return offsetByBeamerOffsetId;
    }

    public void setOffsetByBeamerOffsetId(OffsetEntity offsetByBeamerOffsetId) {
        this.offsetByBeamerOffsetId = offsetByBeamerOffsetId;
    }

}
