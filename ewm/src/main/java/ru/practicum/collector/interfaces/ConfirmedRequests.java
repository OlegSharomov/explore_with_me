package ru.practicum.collector.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedRequests implements ConfirmedRequestsInterface {
    private Long eventId;
    private Long quantityConfirmedRequests;
}
