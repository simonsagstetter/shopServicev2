import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( "1", List.of( product ) );
        repo.addOrder( newOrder );

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product( "1", "Apfel" );
        expected.add( new Order( "1", List.of( product1 ) ) );

        assertEquals( actual, expected );
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( "1", List.of( product ) );
        repo.addOrder( newOrder );

        //WHEN
        assertDoesNotThrow( () -> {
            Order actual = repo.getOrderById( "1" )
                    .orElseThrow( () -> new OrderNotFoundException( newOrder.id() ) );
            //THEN
            Product product1 = new Product( "1", "Apfel" );
            Order expected = new Order( "1", List.of( product1 ) );

            assertEquals( actual, expected );
        } );

    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( "1", List.of( product ) );

        //WHEN
        Order actual = repo.addOrder( newOrder );

        //THEN
        Product product1 = new Product( "1", "Apfel" );
        Order expected = new Order( "1", List.of( product1 ) );
        assertEquals( actual, expected );
        assertDoesNotThrow( () -> {
            Order order = repo.getOrderById( "1" ).orElseThrow( () -> new OrderNotFoundException( "1" ) );
            assertEquals( order, expected );
        } );
    }

    @Test
    void removeOrder() {
        OrderNotFoundException exception = assertThrows( OrderNotFoundException.class, () -> {
            //GIVEN
            OrderListRepo repo = new OrderListRepo();

            //WHEN
            repo.removeOrder( "1" );

            //THEN
            repo.getOrderById( "1" ).orElseThrow( () -> new OrderNotFoundException( "1" ) );
        } );

        assertEquals( exception.getMessage(), new OrderNotFoundException( "1" ).getMessage() );
    }

    @Test
    void updateOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( "1", List.of( product ) );
        repo.addOrder( newOrder );

        //WHEN
        Order updatedOrder = repo.updateOrder( newOrder.withOrderStatus( OrderStatus.COMPLETED ) );

        //THEN
        assertNotEquals( newOrder, updatedOrder );
        assertEquals( OrderStatus.COMPLETED, updatedOrder.orderStatus() );

        assertDoesNotThrow( () -> {
            // WHEN
            Order updatedOrder2 = repo.updateOrder( updatedOrder.withId( "2" ).withOrderStatus( OrderStatus.COMPLETED ) );
            Order foundOrder = repo.getOrderById( updatedOrder2.id() ).orElseThrow( () -> new OrderNotFoundException( "1" ) );
            assertEquals( OrderStatus.COMPLETED, foundOrder.orderStatus() );
        } );
    }
}
