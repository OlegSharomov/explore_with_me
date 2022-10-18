//package ru.practicum.events.repository;
//
//import org.springframework.data.domain.Example;
//import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
//import org.springframework.data.jpa.domain.Specification;
//import ru.practicum.events.entity.Event;
//
//import javax.persistence.criteria.Predicate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class EventRepositoryImpl implements EventRepository{
//    public Specification<Event> getSpecFromDatesAndExample(LocalDateTime from, LocalDateTime to, Example<Event> example) {
//
//        return (Specification<Event>) (root, query, builder) -> {
//            final List<Predicate> predicates = new ArrayList<>();
//
//            if (from != null) {
//                predicates.add(builder.greaterThan(root.get("dateField"), from));
//            }
//            if (to != null) {
//                predicates.add(builder.lessThan(root.get("dateField"), to));
//            }
//            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
//
//            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
//        };
//    }
//}
