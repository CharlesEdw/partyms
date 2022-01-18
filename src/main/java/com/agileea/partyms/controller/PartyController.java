package com.agileea.partyms.controller;

import com.agileea.partyms.model.Party;
import com.agileea.partyms.model.PartyPage;
import com.agileea.partyms.model.PartySearchCriteria;
import com.agileea.partyms.service.PartyService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public ResponseEntity<Page<Party>> getParties(PartyPage partyPage,
                                            PartySearchCriteria partySearchCriteria) {
        return new ResponseEntity<>(partyService.getParties(partyPage, 
        partySearchCriteria), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Party> addParty(@RequestBody Party party) {
        return new ResponseEntity<>(partyService.addParty(party), HttpStatus.OK);
    }
}
