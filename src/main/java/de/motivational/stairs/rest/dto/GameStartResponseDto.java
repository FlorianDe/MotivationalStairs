package de.motivational.stairs.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by viktorspadi on 29.12.16.
 */
public class GameStartResponseDto {
    @JsonProperty("usersBefore")
    private String[] usersBefore;

    @JsonProperty("ticket")
    private String ticket;

    public GameStartResponseDto() {

    }

    public String[] getUsersBefore() {
        return usersBefore;
    }

    public void setUsersBefore(String[] userBefore) {
        this.usersBefore = userBefore;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
