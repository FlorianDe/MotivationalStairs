package de.motivational.stairs.rest;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.beamer.AppPrincipalFrame;
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.repository.BeamerSetupService;
import de.motivational.stairs.rest.dto.BeamerSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value="/api/beamer")
public class BeamerSettingService {

    @Autowired
    AppPrincipalFrame appPrincipalFrame;

    @Autowired
    BeamerSetupService beamerSetupService;

    @RequestMapping(value="/{beamerId}", method= RequestMethod.GET)
    @ResponseBody BeamerSetupDto getBeamerById(@PathVariable int beamerId) {
        Optional<BeamerSetupDto> beamerSetupDto = beamerSetupService.findOne(beamerId).map(BeamerSetupDto::new);
        return beamerSetupDto.isPresent()?beamerSetupDto.get():null;
    }

    @RequestMapping(value="/{strTitle}/{strColor}", method= RequestMethod.GET)
    public void createConnection(@PathVariable String strTitle, @PathVariable String strColor) {
        //appPrincipalFrame.setTestMethod(strTitle, strColor);
    }
}