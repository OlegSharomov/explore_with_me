package ru.practicum.events.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.categories.entity.Category;
import ru.practicum.events.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public final class EventFindSpecification {

    public static Specification<Event> specificationForPublicSearch(String text, List<Category> categories, Boolean paid,
                                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                                    Boolean onlyAvailable) {
        return Specification
                .where(annotationContains(text).or(descriptionContains(text)))
                .and(categoryContainsIn(categories))
                .and(isPaid(paid))
                .and(afterDate(rangeStart))
                .and(beforeDate(rangeEnd))
                .and(isAvailable(onlyAvailable));
    }

    public static Specification<Event> annotationContains(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("annotation"), "%" + text + "%");
    }

    public static Specification<Event> descriptionContains(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), "%" + text + "%");
    }

    public static Specification<Event> categoryContainsIn(List<Category> categories) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("category")).value(categories);
    }

    public static Specification<Event> isPaid(Boolean paid) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paid"), paid);
    }

    public static Specification<Event> afterDate(LocalDateTime rangeStart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> beforeDate(LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }

    public static Specification<Event> isAvailable(Boolean onlyAvailable) {
        if (onlyAvailable) {  // TODO реализовать поиска не только по значению 0, но и когда не исчерпан лимит запросов на участие
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("participantLimit"), 0);
        } else { //TODO реализовать возможность поиска даже когда исчерпан лимит запросов на участие
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("participantLimit"), 0);
        }
    }
}
