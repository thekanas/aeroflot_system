package by.stolybko.database.dao;

import by.stolybko.database.entity.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class Dao<K extends Serializable, E extends BaseEntity<K>> {

    private Class<E> clazz;

    public List<E> findAll(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<E> critQuery = builder.createQuery(clazz);
        critQuery.select(critQuery.from(clazz));

        return session.createQuery(critQuery).getResultList();
    }

    public Optional<E> findById(Session session, K id) {
        return Optional.ofNullable(session.get(clazz, id));
    }

    public Optional<E> save(Session session, E entity) {
        session.persist(entity);
        return Optional.ofNullable(entity);
    }

    public Optional<E> update(Session session, E entity) {
        session.merge(entity);
        return Optional.ofNullable(entity);
    }

    public boolean delete(Session session, K id) {
        return Optional.ofNullable(session.get(clazz, id))
                .map(entity -> {
                    session.remove(entity);
                    return true;
                })
                .orElse(false);
    }
}
