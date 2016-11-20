package de.motivational.stairs.rest.dto.setup;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.BeamerSetupEntity;

/**
 * Created by Florian on 20.11.2016.
 */
public class BeamerSetupDto {

    @JsonProperty("setupId")
    private int setupId;

    @JsonProperty("setupName")
    private String setupName;

    @JsonProperty("beamer")
    private BeamerDto beamer;

    @JsonProperty("stairs")
    private StairsDto stairs;

    public BeamerSetupDto(BeamerSetupEntity bse){
        this.setupId = bse.getSetupId();
        this.setupName = bse.getSetupName();
        this.beamer = new BeamerDto(bse.getBeamerByBeamerId(),bse.getOffsetByBeamerOffsetId());
        this.stairs = new StairsDto(bse.getStairsByStairsId(),bse.getOffsetByStairsOffsetId());
    }

    public int getSetupId() {
        return setupId;
    }

    public void setSetupId(int setupId) {
        this.setupId = setupId;
    }

    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName;
    }

    public BeamerDto getBeamer() {
        return beamer;
    }

    public void setBeamer(BeamerDto beamer) {
        this.beamer = beamer;
    }

    public StairsDto getStairs() {
        return stairs;
    }

    public void setStairs(StairsDto stairs) {
        this.stairs = stairs;
    }
}
