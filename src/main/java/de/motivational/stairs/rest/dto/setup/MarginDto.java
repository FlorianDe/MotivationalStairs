package de.motivational.stairs.rest.dto.setup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vspadi on 20.11.16.
 */
public class MarginDto {

    @JsonProperty("left")
    private int left;

    @JsonProperty("right")
    private int right;

    @JsonProperty("top")
    private int top;

    @JsonProperty("bottom")
    private int bottom;

    public MarginDto(){}

    public MarginDto(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
