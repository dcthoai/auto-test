package vn.softdream.autotest.common;

import vn.softdream.autotest.exception.BaseIllegalArgumentException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class DataUtils {

    private static final Logger log = LoggerFactory.getLogger(DataUtils.class);
    private static final String ENTITY_NAME = "DataUtils";

    public static QueryBuilder createQueryBuilder(EntityManager entityManager) {
        return new QueryBuilder(entityManager);
    }

    public static class QueryBuilder {

        private final EntityManager entityManager;
        private String querySql;
        private String countQuerySql;
        private Pageable pageable;
        private Map<String, Object> params = new HashMap<>();

        public QueryBuilder(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public QueryBuilder querySql(String querySql) {
            if (StringUtils.hasText(querySql)) {
                this.querySql = querySql;
                return this;
            }

            throw new BaseIllegalArgumentException(ENTITY_NAME, "Query SQL must not be null or empty!");
        }

        public QueryBuilder countQuerySql(String countQuerySql) {
            if (StringUtils.hasText(countQuerySql)) {
                this.countQuerySql = countQuerySql;
                return this;
            }

            throw new BaseIllegalArgumentException(ENTITY_NAME, "Count query SQL must not be null or empty!");
        }

        public QueryBuilder pageable(Pageable pageable) {
            this.pageable = pageable;
            return this;
        }

        public QueryBuilder params(Map<String, Object> params) {
            if (Objects.nonNull(params)) {
                this.params = params;
            }

            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> List<T> getResultsWithDTO(Function<Tuple, T> factory) {
            if (!StringUtils.hasText(querySql)) {
                throw new BaseIllegalArgumentException(ENTITY_NAME, "Query SQL must be set before execution");
            }

            Query query = entityManager.createNativeQuery(querySql, Tuple.class);
            params.forEach(query::setParameter);

            if (Objects.nonNull(pageable) && pageable.getOffset() >= 0L && pageable.getPageSize() > 0) {
                query.setFirstResult((int) pageable.getOffset());
                query.setMaxResults(pageable.getPageSize());
            }

            List<Tuple> tuples = query.getResultList();
            return tuples.stream().map(factory).collect(Collectors.toList());
        }

        public <T> Optional<T> getSingleResultWithDTO(Function<Tuple, T> factory) {
            if (!StringUtils.hasText(querySql)) {
                throw new BaseIllegalArgumentException(ENTITY_NAME, "Query SQL must be set before execution");
            }

            Query query = entityManager.createNativeQuery(querySql, Tuple.class);
            params.forEach(query::setParameter);

            try {
                Tuple tuple = (Tuple) query.getSingleResult();
                return Optional.of(factory.apply(tuple));
            } catch (NoResultException e) {
                log.warn("[{}] - No result found for query: {}", ENTITY_NAME, querySql);
            }

            return Optional.empty();
        }

        public long countTotalRecords() {
            if (!StringUtils.hasText(countQuerySql)) {
                throw new BaseIllegalArgumentException(ENTITY_NAME, "Count query SQL must be set before execution");
            }

            Query countQuery = entityManager.createNativeQuery(countQuerySql);
            params.forEach(countQuery::setParameter);

            try {
                Object totalRecords = countQuery.getSingleResult();
                return Objects.nonNull(totalRecords) ? ((Number) totalRecords).longValue() : 0;
            } catch (Exception e) {
                log.error("[{}] - Could not execute count query. {}", ENTITY_NAME, e.getMessage());
            }

            return 0;
        }
    }
}
