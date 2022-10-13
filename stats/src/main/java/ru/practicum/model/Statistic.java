package ru.practicum.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistics", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class,
        defaultForType = AdditionalFields.class
)
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "uri")
    private String uri;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Type(type = "jsonb")
    @Column(name = "additional_fields", columnDefinition = "jsonb")
    private AdditionalFields additionalFields;
}
