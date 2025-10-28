import lombok.With;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/*
    Why we use Instant:
    We use an Instant for the orderDateTime so we can adjust the timezone
    by using the shipping or invoice address of the customer. It would also be
    possible to use ZonedDateTime. In this case we would need the client to
    pass a ZoneId to addOrder() so we can set the correct datetime with timezone.
    But this would pose a security risk as it is easy to manipulate the timezone
    of the client's system.
 */

@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus orderStatus,
        Instant orderDateTime
) {

    public Order( List<Product> products ) {
        this( UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now() );
    }
}
