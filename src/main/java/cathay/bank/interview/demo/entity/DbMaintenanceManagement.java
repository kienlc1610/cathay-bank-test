package cathay.bank.interview.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DbMaintenanceManagement.
 */
@Entity
@Table(name = "db_maintenance_management")
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DbMaintenanceManagement implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "service_name", nullable = false, unique = true, updatable = false)
    private String serviceName;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
