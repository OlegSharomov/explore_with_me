package ru.practicum.requests.repository;

import ru.practicum.requests.entity.Request;

import java.util.List;

public interface CustomRequestRepository {
    Request findRequestByIdOnlyWithEvent(Long requestId);

    Request findRequestByIWithoutRelatedFields(Long requestId);

    List<Request> findAllRequestsWithoutRelatedFieldsByRequesterId(Long userId);
}
