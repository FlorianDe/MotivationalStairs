package de.motivational.stairs.rest.dto.setup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vspadi on 20.11.16.
 */
public class SetupsDto {

    @JsonProperty("setups")
    BeamerSetupDto[] setups;

    public SetupsDto(BeamerSetupDto[] setups) {
        this.setups = setups;
    }

    public BeamerSetupDto[] getSetups() {
        return setups;
    }

    public void setSetups(BeamerSetupDto[] setups) {
        this.setups = setups;
    }
}
