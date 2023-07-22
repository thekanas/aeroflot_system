package by.stolybko.database.repository;

import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryExtensionImpl implements PersonRepositoryExtension {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<PersonEntity> findByFilter(PersonFilter filter,  Integer page) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> critQuery = builder.createQuery(PersonEntity.class);
        Root<PersonEntity> root = critQuery.from(PersonEntity.class);
        critQuery.select(root).where(collectPredicates(filter, builder, root).toArray(Predicate[]::new));

        return entityManager.createQuery(critQuery)
                .setMaxResults(Integer.parseInt(filter.getLimit()))
                .setFirstResult(Integer.parseInt(filter.getLimit()) * (page - 1))
                .getResultList();
    }

    @Override
    public Long countAllRecordsByFilter(PersonFilter filter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = builder.createQuery(Long.class);
        Root<PersonEntity> root = critQuery.from(PersonEntity.class);
        critQuery.select(builder.count(root)).where(collectPredicates(filter, builder, root).toArray(Predicate[]::new));

        return entityManager.createQuery(critQuery).getSingleResult();
    }

    private static List<Predicate> collectPredicates(PersonFilter filter, CriteriaBuilder builder, Root<PersonEntity> personRoot) {

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getFullName() != null) {
            predicates.add(builder.like(builder.upper(personRoot.get("fullName")), "%" + filter.getFullName().toUpperCase() + "%"));
        }
        if (filter.getPosition() != null) {
            predicates.add(builder.like(personRoot.get("position"), "%" + filter.getPosition() + "%"));
        }
        if (filter.getBirthDayFrom() != null && filter.getBirthDayTO() != null) {
            predicates.add(builder.between(personRoot.get("birthDay"), Date.valueOf(filter.getBirthDayFrom()), Date.valueOf(filter.getBirthDayTO())));
        }

        return predicates;
    }
}
