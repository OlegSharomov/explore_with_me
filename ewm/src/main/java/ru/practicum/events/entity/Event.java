package ru.practicum.events.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.entity.Category;
import ru.practicum.compilations.entity.Compilation;
import ru.practicum.events.model.EventState;
import ru.practicum.events.model.Location;
import ru.practicum.users.entity.User;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "annotation", length = 2000, nullable = false)
    @Size(min = 20)
    private String annotation;
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;
    @Column(name = "description", length = 7000, nullable = false)
    @Size(min = 20)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "location", nullable = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "lon")),
    })
    private Location location;
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;
    @Column(name = "title", length = 120, nullable = false)
    @Size(min = 3)
    private String title;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id", nullable = false)
    private User initiator;
    @Column(name = "state", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "craeted_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @ManyToMany(mappedBy = "events")
    @JsonIgnore
    List<Compilation> compilations;
}
