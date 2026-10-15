import java.time.LocalDateTime;

public record StockEventData(
        Product product,
        int currentQuantity,
        int reorderLevel,
        LocalDateTime alertTime
) {}
