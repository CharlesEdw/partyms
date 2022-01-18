package com.agileea.partyms.repository;

import com.agileea.partyms.model.Party;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends CrudRepository<Party, Long> {

}