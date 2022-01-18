package com.agileea.partyms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.agileea.partyms.model.Party;
import com.agileea.partyms.model.PartyPage;
import com.agileea.partyms.model.PartySearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class PartyCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PartyCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Party> findAllWithFilters(PartyPage partyPage,
                                            PartySearchCriteria partySearchCriteria) {
        CriteriaQuery<Party> criteriaQuery = criteriaBuilder.createQuery(Party.class);
        Root<Party> partyRoot = criteriaQuery.from(Party.class);
        Predicate predicate = getPredicate(partySearchCriteria, partyRoot);
        criteriaQuery.where(predicate);
        setOrder(partyPage, criteriaQuery, partyRoot);

        TypedQuery<Party> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(partyPage.getPageNumber() * partyPage.getPageSize());
        typedQuery.setMaxResults(partyPage.getPageSize());

        Pageable pageable = getPageable(partyPage);

        long partiesCount = getPartiesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, partiesCount);
    }

    private Predicate getPredicate(PartySearchCriteria partySearchCriteria, Root<Party> partyRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(partySearchCriteria.getFirstname())) {
            predicates.add(criteriaBuilder.like(partyRoot.get("firstname"), 
            "%" + partySearchCriteria.getFirstname() + "%"));
        }
        if (Objects.nonNull(partySearchCriteria.getSurname())) {
            predicates.add(criteriaBuilder.like(partyRoot.get("surname"),
            "%" + partySearchCriteria.getSurname() + "%"));
        }
        if (Objects.nonNull(partySearchCriteria.getOrgname())) {
            predicates.add(criteriaBuilder.like(partyRoot.get("orgname"),
            "%" + partySearchCriteria.getOrgname() + "%"));
        }
        if (Objects.nonNull(partySearchCriteria.getId())) {
            predicates.add(criteriaBuilder.like(partyRoot.get("id"),
            "%" + partySearchCriteria.getId() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(  PartyPage partyPage, 
                            CriteriaQuery<Party> criteriaQuery, 
                            Root<Party> partyRoot) {
        if (partyPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(partyRoot.get(partyPage.getSortBy())));
        }
        else {
            criteriaQuery.orderBy(criteriaBuilder.desc(partyRoot.get(partyPage.getSortBy())));
        }
   }
   private Pageable getPageable(PartyPage partyPage) {
       Sort sort = Sort.by(partyPage.getSortDirection(), partyPage.getSortBy());
    return PageRequest.of(partyPage.getPageNumber(), partyPage.getPageSize(), sort);
}

    private long getPartiesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Party> countRoot = countQuery.from(Party.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
