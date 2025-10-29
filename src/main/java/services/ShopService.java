package services;

import models.Order;
import models.OrderStatus;
import models.Product;
import exceptions.OrderNotFoundException;
import exceptions.ProductNotFoundException;
import repositories.OrderMapRepo;
import repositories.OrderRepo;
import repositories.ProductRepo;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public ShopService( ProductRepo productRepo, OrderRepo orderRepo ) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order addOrder( List<String> productIds ) throws ProductNotFoundException {

        List<Product> products = new ArrayList<>();
        for ( String productId : productIds ) {
            Product productToOrder = productRepo
                    .getProductById( productId )
                    .orElseThrow( () -> new ProductNotFoundException( productId ) );

            products.add( productToOrder );
        }
        // We moved the auto generation for UUID and the creation of the Instant
        // to a custom constructor inside the models.Order record class
        Order newOrder = new Order( products );

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
