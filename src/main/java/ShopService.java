import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder( List<String> productIds ) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for ( String productId : productIds ) {
            Product productToOrder = productRepo
                    .getProductById( productId )
                    .orElseThrow( () -> new ProductNotFoundException( productId ) );

            products.add( productToOrder );
        }

        Order newOrder = new Order( UUID.randomUUID().toString(), products );

        return orderRepo.addOrder( newOrder );
    }

    public Order updateOrder( String orderId, OrderStatus newOrderStatus ) throws OrderNotFoundException {
        Order order = orderRepo
                .getOrderById( orderId )
                .orElseThrow( () -> new OrderNotFoundException( orderId ) );

        return orderRepo.updateOrder( order.withOrderStatus( newOrderStatus ) );
    }

    public List<Order> getOrdersByOrderStatus( OrderStatus orderStatus ) {
        return this.orderRepo
                .getOrders()
                .stream()
                .filter( order -> order.orderStatus().equals( orderStatus ) )
                .toList();
    }
}
