import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void Order_ShouldReturnInstanceWithDefaultOrderStatus_WhenCalledWithCustomConstructor() {
        //GIVEN
        Product product = new Product( "1", "Apfel" );

        //WHEN
        Order order = new Order( "1", List.of( product ) );

        //EXPECT
        assertEquals( OrderStatus.PROCESSING, order.orderStatus() );
    }

}