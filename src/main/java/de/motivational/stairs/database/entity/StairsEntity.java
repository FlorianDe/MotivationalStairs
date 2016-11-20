package de.motivational.stairs.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Florian on 20.11.2016.
 */
@Entity
@Table(name = "stairs", schema = "motivationalstairs")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StairsEntity {
    private int stairsId;
    private String stairsName;
    private int stepsCount;
    private int stepWidth;
    private int stepDepth;
    private int stepHeight;
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;
    private Collection<BeamerSetupEntity> beamerSetupsByStairsId;

    @Id
    @Column(name = "stairs_id")
    public int getStairsId() {
        return stairsId;
    }

    public void setStairsId(int stairsId) {
        this.stairsId = stairsId;
    }

    @Basic
    @Column(name = "stairs_name")
    public String getStairsName() {
        return stairsName;
    }

    public void setStairsName(String stairsName) {
        this.stairsName = stairsName;
    }

    @Basic
    @Column(name = "steps_count")
    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
    }

    @Basic
    @Column(name = "step_width")
    public int getStepWidth() {
        return stepWidth;
    }

    public void setStepWidth(int stepWidth) {
        this.stepWidth = stepWidth;
    }

    @Basic
    @Column(name = "step_depth")
    public int getStepDepth() {
        return stepDepth;
    }

    public void setStepDepth(int stepDepth) {
        this.stepDepth = stepDepth;
    }

    @Basic
    @Column(name = "step_height")
    public int getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(int stepHeight) {
        this.stepHeight = stepHeight;
    }

    @Basic
    @Column(name = "margin_left")
    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    @Basic
    @Column(name = "margin_right")
    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    @Basic
    @Column(name = "margin_top")
    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    @Basic
    @Column(name = "margin_bottom")
    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StairsEntity that = (StairsEntity) o;

        return stairsId == that.stairsId;
    }

    @Override
    public int hashCode() {
        return stairsId;
    }

    @OneToMany(mappedBy = "stairsByStairsId")
    public Collection<BeamerSetupEntity> getBeamerSetupsByStairsId() {
        return beamerSetupsByStairsId;
    }

    public void setBeamerSetupsByStairsId(Collection<BeamerSetupEntity> beamerSetupsByStairsId) {
        this.beamerSetupsByStairsId = beamerSetupsByStairsId;
    }

}
