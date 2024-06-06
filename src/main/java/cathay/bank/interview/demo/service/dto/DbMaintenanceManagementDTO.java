package cathay.bank.interview.demo.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link cathay.bank.interview.demo.entity.DbMaintenanceManagement} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DbMaintenanceManagementDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2719301125603853419L;
    @NotNull
    private String serviceName;

    private Boolean activated;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
