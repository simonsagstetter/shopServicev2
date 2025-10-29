import models.Order;
import models.OrderStatus;
import models.Product;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void Order_ShouldReturnInstanceWithDefaultValues_WhenCalledWithCustomConstructor() {
        //GIVEN
        Product product = new Product( "1", "Apfel" );

        //WHEN
        Order order = new Order( List.of( product ) );

        //EXPECT
        assertEquals( OrderStatus.PROCESSING, order.orderStatus() );
        assertEquals( Instant.class, order.orderDateTime().getClass() );
        assertFalse( order.id().isEmpty() );
    }

}