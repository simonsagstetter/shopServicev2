import java.util.List;
import java.util.Optional;

public interface OrderRepo {

    List<Order> getOrders();

    Optional<Order> getOrderById( String id );

    Order addOrder( Order newOrder );

    Order updateOrder( Order updatedOrder );

    void removeOrder( String id );
}
