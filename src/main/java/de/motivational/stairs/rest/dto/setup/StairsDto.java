package de.motivational.stairs.rest.dto.setup;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.OffsetEntity;
import de.motivational.stairs.database.entity.StairsEntity;

/**
 * Created by vspadi on 20.11.16.
 */
public class StairsDto {
    @JsonProperty("stairsId")
    private int stairsId;

    @JsonProperty("stairsName")
    private String stairsName;

    @JsonProperty("stepsCount")
    private int stepsCount;

    @JsonProperty("stepWidth")
    private int stepWidth;

    @JsonProperty("stepDepth")
    private int stepDepth;

    @JsonProperty("stepHeight")
    private int stepHeight;

    @JsonProperty("margin")
    private MarginDto margin;

    @JsonProperty("offset")
    private OffsetDto stairsOffset;

    public StairsDto(StairsEntity se, OffsetEntity oe) {
        this.stairsId = se.getStairsId();
        this.stairsName = se.getStairsName();
        this.stairsOffset = new OffsetDto(oe);
        this.stepsCount = se.getStepsCount();
        this.stepWidth = se.getStepWidth();
        this.stepDepth = se.getStepDepth();
        this.stepHeight = se.getStepHeight();

        this.margin = new MarginDto(
                se.getMarginLeft(),
                se.getMarginRight(),
                se.getMarginTop(),
                se.getMarginBottom());
    }

    public int getStairsId() {
        return stairsId;
    }

    public void setStairsId(int stairsId) {
        this.stairsId = stairsId;
    }

    public String getStairsName() {
        return stairsName;
    }

    public void setStairsName(String stairsName) {
        this.stairsName = stairsName;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
    }

    public int getStepWidth() {
        return stepWidth;
    }

    public void setStepWidth(int stepWidth) {
        this.stepWidth = stepWidth;
    }

    public int getStepDepth() {
        return stepDepth;
    }

    public void setStepDepth(int stepDepth) {
        this.stepDepth = stepDepth;
    }

    public int getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(int stepHeight) {
        this.stepHeight = stepHeight;
    }

    public MarginDto getMargin() {
        return margin;
    }

    public void setMargin(MarginDto margin) {
        this.margin = margin;
    }

    public OffsetDto getStairsOffset() {
        return stairsOffset;
    }

    public void setStairsOffset(OffsetDto stairsOffset) {
        this.stairsOffset = stairsOffset;
    }
}
