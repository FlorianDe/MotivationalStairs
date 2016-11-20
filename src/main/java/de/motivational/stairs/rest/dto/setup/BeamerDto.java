package de.motivational.stairs.rest.dto.setup;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.BeamerEntity;
import de.motivational.stairs.database.entity.OffsetEntity;

/**
 * Created by vspadi on 20.11.16.
 */
public class BeamerDto {
    @JsonProperty("beamerId")
    private int beamerId;

    @JsonProperty("beamerName")
    private String beamerName;

    @JsonProperty("angle")
    private float angle;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;

    @JsonProperty("offset")
    private OffsetDto beamerOffset;

    public BeamerDto(BeamerEntity be, OffsetEntity oe) {
        this.beamerId = be.getBeamerId();
        this.beamerName = be.getBeamerName();
        this.beamerOffset = new OffsetDto(oe);
        this.angle = be.getAngle();
        this.width = be.getWidth();
        this.height = be.getHeight();
    }

    public int getBeamerId() {
        return beamerId;
    }

    public void setBeamerId(int beamerId) {
        this.beamerId = beamerId;
    }

    public String getBeamerName() {
        return beamerName;
    }

    public void setBeamerName(String beamerName) {
        this.beamerName = beamerName;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public OffsetDto getBeamerOffset() {
        return beamerOffset;
    }

    public void setBeamerOffset(OffsetDto beamerOffset) {
        this.beamerOffset = beamerOffset;
    }
}
