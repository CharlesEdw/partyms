package com.agileea.partyms.service;

import com.agileea.partyms.model.Party;
import com.agileea.partyms.model.PartyPage;
import com.agileea.partyms.model.PartySearchCriteria;
import com.agileea.partyms.repository.PartyCriteriaRepository;
import com.agileea.partyms.repository.PartyRepository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PartyService {
    
    private final PartyRepository partyRepository;
    private final PartyCriteriaRepository partyCriteriaRepository;

    public PartyService(PartyRepository partyRepository, 
                        PartyCriteriaRepository partyCriteriaRepository) {
        this.partyRepository = partyRepository;
        this.partyCriteriaRepository = partyCriteriaRepository;
    }

    public Page<Party> findPartiesBy(PartyPage partyPage,
                                    PartySearchCriteria partySearchCriteria) {
        return partyCriteriaRepository.findAllWithFilters(partyPage, 
                                                            partySearchCriteria);
    }

    public Party readParty(Long id) {
        return partyRepository.getById(id);
    }

    public Party createParty(Party party) {
        return partyRepository.save(party);
    }

    public Party updateParty(Party party) {
        return partyRepository.saveAndFlush(party);
    }

    public Object deleteById(Party party) {
        System.out.println("Party Id in Service is: "+party.getId());
        partyRepository.deleteById(party.getId());
        return null;
    }


    
}
