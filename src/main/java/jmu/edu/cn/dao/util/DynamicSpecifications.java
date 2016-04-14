package jmu.edu.cn.dao.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

public final class DynamicSpecifications {
    private DynamicSpecifications() {
    }

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (!filters.isEmpty()) {

                    List<Predicate> predicates = Lists.newArrayList();
                    for (SearchFilter filter : filters) {
                        boolean multiProperty = StringUtils.contains(filter.fieldName, SearchFilter.OR_SEPARATOR);
                        if (multiProperty) {
                            String[] fieldNames = StringUtils.split(filter.fieldName, SearchFilter.OR_SEPARATOR);
                            List<Predicate> orPredicates = Lists.newArrayList();
                            for (String fieldName : fieldNames) {
                                orPredicates.add(buildPredicate(fieldName, filter.value, filter.operator, root, builder));
                            }
                            Predicate[] predicateArr = new Predicate[orPredicates.size()];
                            predicates.add(builder.or(orPredicates.toArray(predicateArr)));
                        } else {
                            predicates.add(buildPredicate(filter.fieldName, filter.value, filter.operator, root, builder));
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (predicates.size() > 0) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }

            @SuppressWarnings({"unchecked", "rawtypes"})
            private Predicate buildPredicate(String fieldName, Object value, SearchFilter.Operator operator, Root<T> root, CriteriaBuilder builder) {
                Predicate predicate = null;
                // nested path translate, 如Task的名为"user.name"的filedName,
                // 转换为Task.user.name属性
                String[] names = StringUtils.split(fieldName, ".");
                Path expression = root.get(names[0]);
                for (int i = 1; i < names.length; i++) {
                    expression = expression.get(names[i]);
                }
                // logic operator
                switch (operator) {
                    case EQ:
                        predicate = builder.equal(expression, value);
                        break;
                    case NEQ:
                        predicate = builder.notEqual(expression, value);
                        break;
                    case LIKE:
                        predicate = builder.like(expression, "%" + value + "%");
                        break;
                    case GT:
                        predicate = builder.greaterThan(expression, (Comparable) value);
                        break;
                    case LT:
                        predicate = builder.lessThan(expression, (Comparable) value);
                        break;
                    case GTE:
                        predicate = builder.greaterThanOrEqualTo(expression, (Comparable) value);
                        break;
                    case LTE:
                        predicate = builder.lessThanOrEqualTo(expression, (Comparable) value);
                        break;
                    case IN:
                        predicate = expression.in((List) value);
                        break;
                    case NIN:
                        predicate = builder.not(expression.in((List) value));
                        break;
                    default:
                        break;
                }
                return predicate;
            }
        };
    }
}
