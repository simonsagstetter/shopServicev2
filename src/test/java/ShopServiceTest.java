import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest_WhenValidId_ExceptDoesNotThrow() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of( "1" );

        //WHEN
        assertDoesNotThrow( () -> {
            Order actual = shopService.addOrder( productsIds );

            //THEN
            Order expected = new Order( List.of( new Product( "1", "Apfel" ) ) );
            assertEquals( expected.products(), actual.products() );
            assertNotNull( expected.id() );
            assertEquals( Instant.class, expected.orderDateTime().getClass() );

        } );

    }

    @Test
    void addOrderTest_whenInvalidProductId_expectProductNotFoundException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of( "1", "2" );

        //WHEN
        ProductNotFoundException exception = assertThrows( ProductNotFoundException.class, () -> {
            Order actual = shopService.addOrder( productsIds );
        } );

        //THEN
        assertEquals( exception.getMessage(), new ProductNotFoundException( "2" ).getMessage() );
    }

    @Test
    void getOrdersByOrderStatus_WhenNoOrderHasGivenOrderStatus_ExpectEmptyListOfOrders() {
        //GIVEN
        ShopService shopService = new ShopService();

        assertDoesNotThrow( () -> {
            shopService.addOrder( List.of( "1" ) );
        } );

        //WHEN
        List<Order> orders = shopService.getOrdersByOrderStatus( OrderStatus.COMPLETED );

        //THEN
        assertTrue( orders.isEmpty() );
    }

    @Test
    void getOrdersByOrderStatus_WhenOrderHasGivenOrderStatus_ExpectOrderInOrderList() {
        //GIVEN
        ShopService shopService = new ShopService();
        AtomicReference<Order> order = new AtomicReference<>();
        assertDoesNotThrow( () -> {
            order.set( shopService.addOrder( List.of( "1" ) ) );
        } );

        //WHEN
        List<Order> orders = shopService.getOrdersByOrderStatus( OrderStatus.PROCESSING );

        //THEN
        assertFalse( orders.isEmpty() );
        assertEquals( orders.getFirst(), order.get() );
    }

    @Test
    void updateOrder_WhenOrderDoesExist_ExpectOrderWithUpdatedOrderStatus() {
        assertDoesNotThrow( () -> {
            //GIVEN
            ShopService shopService = new ShopService();
            Order order = shopService.addOrder( List.of( "1" ) );

            //WHEN
            Order updatedOrder = shopService.updateOrder( order.id(), OrderStatus.COMPLETED );

            //THEN
            assertNotEquals( order, updatedOrder );
            assertEquals( OrderStatus.COMPLETED, updatedOrder.orderStatus() );

        } );
    }

    @Test
    void updateOrder_WhenOrderDoesNotExist_ExpectOrderNotFoundException() {
        OrderNotFoundException exception = assertThrows( OrderNotFoundException.class, () -> {
            //GIVEN
            ShopService shopService = new ShopService();

            //WHEN
            Order updatedOrder = shopService.updateOrder( "test-id", OrderStatus.COMPLETED );
        } );

        //THEN
        assertEquals( exception.getMessage(), new OrderNotFoundException( "test-id" ).getMessage() );
    }
}
