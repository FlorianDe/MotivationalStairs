package de.motivational.stairs.rest.dto.setup;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.OffsetEntity;

/**
 * Created by vspadi on 20.11.16.
 */
public class OffsetDto {
    @JsonProperty("offsetId")
    private int offsetId;

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    @JsonProperty("z")
    private int z;

    @JsonProperty("yaw")
    private int yaw;

    @JsonProperty("pitch")
    private int pitch;

    @JsonProperty("roll")
    private int roll;

    public OffsetDto(){

    }

    public OffsetDto(OffsetEntity oe) {
        this.offsetId = oe.getOffsetId();
        this.x = oe.getX();
        this.y = oe.getY();
        this.z = oe.getZ();
        this.yaw = oe.getYaw();
        this.pitch = oe.getYaw();
        this.roll = oe.getRoll();
    }

    public int getOffsetId() {
        return offsetId;
    }

    public void setOffsetId(int offsetId) {
        this.offsetId = offsetId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
}
