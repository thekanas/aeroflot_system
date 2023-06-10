package by.stolybko.database.dao;

import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PersonEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class PersonDao extends Dao<Long, PersonEntity> {

    private static final PersonDao INSTANCE = new PersonDao();

    private PersonDao() {
        super(PersonEntity.class);
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

    public List<PersonEntity> findByFilter(Session session, PersonFilter filter, Integer page) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> critQuery = builder.createQuery(PersonEntity.class);
        Root<PersonEntity> root = critQuery.from(PersonEntity.class);

        critQuery.select(root).where(collectPredicates(filter, builder, root).toArray(Predicate[]::new));

        return session.createQuery(critQuery)
                .setMaxResults(Integer.parseInt(filter.getLimit()))
                .setFirstResult(Integer.parseInt(filter.getLimit()) * (page - 1))
                .list();
    }


    public Long countAllRecordsByFilter(Session session, PersonFilter filter) {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = builder.createQuery(Long.class);
        Root<PersonEntity> root = critQuery.from(PersonEntity.class);
        critQuery.select(builder.count(root)).where(collectPredicates(filter, builder, root).toArray(Predicate[]::new));

        return session.createQuery(critQuery).getSingleResult();
    }

    public List<String> findAllPosition(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> critQuery = builder.createQuery(String.class);
        Root<PersonEntity> root = critQuery.from(PersonEntity.class);

        critQuery.select(root.get("position")).distinct(true);
        return session.createQuery(critQuery).getResultList();
    }

    private static List<Predicate> collectPredicates(PersonFilter filter, CriteriaBuilder builder, Root<PersonEntity> personRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getFullName() != null) {
            predicates.add(builder.like(personRoot.get("fullName"), "%" + filter.getFullName() + "%"));
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
