import exceptions.OrderNotFoundException;
import models.Order;
import models.OrderStatus;
import models.Product;
import org.junit.jupiter.api.Test;
import repositories.OrderMapRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        List<Order> expected = repo.getOrders();

        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( List.of( product ) );
        expected.add( newOrder );
        repo.addOrder( newOrder );

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        assertEquals( actual, expected );
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( List.of( product ) );
        repo.addOrder( newOrder );

        //WHEN
        assertDoesNotThrow( () -> {
            Order actual = repo.getOrderById( newOrder.id() )
                    .orElseThrow( () -> new OrderNotFoundException( newOrder.id() ) );

            //THEN
            assertEquals( actual, newOrder );
        } );
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( List.of( product ) );

        //WHEN
        Order expected = repo.addOrder( newOrder );

        //THEN
        assertDoesNotThrow( () -> {
            Order actual = repo.getOrderById( expected.id() ).orElseThrow( () -> new OrderNotFoundException( expected.id() ) );
            assertEquals( expected, actual );
        } );
    }

    @Test
    void removeOrder() {
        OrderNotFoundException exception = assertThrows( OrderNotFoundException.class, () -> {
            //GIVEN
            OrderMapRepo repo = new OrderMapRepo();

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
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product( "1", "Apfel" );
        Order newOrder = new Order( List.of( product ) );
        repo.addOrder( newOrder );

        //WHEN
        Order updatedOrder = repo.updateOrder( newOrder.withOrderStatus( OrderStatus.COMPLETED ) );

        //THEN
        assertNotEquals( newOrder, updatedOrder );
        assertEquals( OrderStatus.COMPLETED, updatedOrder.orderStatus() );

        assertDoesNotThrow( () -> {
            // WHEN
            Order updatedOrder2 = repo.updateOrder( updatedOrder.withId( "2" ).withOrderStatus( OrderStatus.COMPLETED ) );
            Order foundOrder = repo.getOrderById( updatedOrder2.id() ).orElseThrow( () -> new OrderNotFoundException( "2" ) );
            assertEquals( OrderStatus.COMPLETED, foundOrder.orderStatus() );
        } );
    }

}
