import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of( "1" );

        //WHEN
        Order actual = shopService.addOrder( productsIds );

        //THEN
        Order expected = new Order( "-1", List.of( new Product( "1", "Apfel" ) ) );
        assertEquals( expected.products(), actual.products() );
        assertNotNull( expected.id() );
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of( "1", "2" );

        //WHEN
        Order actual = shopService.addOrder( productsIds );

        //THEN
        assertNull( actual );
    }

    @Test
    void getOrdersByOrderStatus_WhenNoOrderHasGivenOrderStatus_ExpectEmptyListOfOrders() {
        //GIVEN
        ShopService shopService = new ShopService();
        Order order = shopService.addOrder( List.of( "1" ) );

        //WHEN
        List<Order> orders = shopService.getOrdersByOrderStatus( OrderStatus.COMPLETED );

        //THEN
        assertTrue( orders.isEmpty() );
    }

    @Test
    void getOrdersByOrderStatus_WhenOrderHasGivenOrderStatus_ExpectOrderInOrderList() {
        //GIVEN
        ShopService shopService = new ShopService();
        Order order = shopService.addOrder( List.of( "1" ) );

        //WHEN
        List<Order> orders = shopService.getOrdersByOrderStatus( OrderStatus.PROCESSING );

        //THEN
        assertFalse( orders.isEmpty() );
        assertEquals( orders.getFirst(), order );
    }
}
