package cathay.bank.interview.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@EntityListeners(AuditingEntityListener.class)
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Currency implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "rate")
    private String rate;

    @Column(name = "description")
    private String description;

    @Column(name = "rate_float")
    private Float rateFloat;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "latest_sync_at")
    private LocalDateTime latestSyncAt;
}
