package cathay.bank.interview.demo.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link cathay.bank.interview.demo.entity.Currency} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CurrencyDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8293366504717896599L;

    @NotEmpty
    @NotNull
    private String code;

    private String symbol;

    private String rate;

    private String description;

    private Float rateFloat;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime latestSyncAt;
}
