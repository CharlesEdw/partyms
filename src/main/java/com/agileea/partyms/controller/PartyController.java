package com.agileea.partyms.controller;

import com.agileea.partyms.model.Party;
import com.agileea.partyms.model.PartyPage;
import com.agileea.partyms.model.PartySearchCriteria;
import com.agileea.partyms.service.PartyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partys")
public class PartyController {
    
    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    /////////////// Find, Search, sort, page //////////////////////////
    @GetMapping
    public ResponseEntity<Page<Party>> getParties(PartyPage partyPage,
                                            PartySearchCriteria partySearchCriteria) {
        return new ResponseEntity<>(partyService.findPartiesBy(partyPage, 
        partySearchCriteria), HttpStatus.OK);
    }
    
    /////////////// Create part of [C]RUD   ////////////////////////////
    @PostMapping
    public ResponseEntity<Party> postParty(@RequestBody Party party) {
        return new ResponseEntity<>(partyService.createParty(party), HttpStatus.OK);
    }

    /////////////// Create part of C[R]UD by Id only ////////////////////
    @GetMapping("/{id}")
    public ResponseEntity<Party> getParty(@PathVariable Long id) {
        System.out.println("In Controller - get Id: "+ id);
        return new ResponseEntity<>(partyService.readParty(id), HttpStatus.OK);
    }

    /////////////// Update/Edit part of CR[U]D   ////////////////////////
    @PutMapping
    public ResponseEntity<Party> putParty(@RequestBody Party party) {
        return new ResponseEntity<>(partyService.updateParty(party), HttpStatus.OK);
    }

    @PutMapping("/multi")
    public ResponseEntity<Party[]> putParties(@RequestBody Party[] parties) {
            for(Party party: parties) {
                partyService.updateParty(party);
            }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /////////////// Delete part of CRU[D]   /////////////////////////////
    @DeleteMapping
    public ResponseEntity<Party> deleteParty(@RequestBody Party party) {
        System.out.println("Party Id in Controller is: "+party.getId());
        return new ResponseEntity<>((Party)partyService.deleteById(party), HttpStatus.OK);
    }
    
}
