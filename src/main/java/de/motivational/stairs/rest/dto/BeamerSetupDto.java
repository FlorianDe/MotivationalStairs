package de.motivational.stairs.rest.dto;
import de.motivational.stairs.database.entity.BeamerSetupEntity;

/**
 * Created by Florian on 20.11.2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class BeamerSetupDto {
    private int setupId;
    private String setupName;
    private int stairsId;
    private String stairsName;

    //...

    public BeamerSetupDto(BeamerSetupEntity bse){
        if(bse!=null) {
            this.setupId = bse.getSetupId();
            this.setupName = bse.getSetupName();
            this.stairsId = bse.getStairsByStairsId().getStairsId();
            this.stairsName = bse.getStairsByStairsId().getStairsName();
            //...
        }
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
}
