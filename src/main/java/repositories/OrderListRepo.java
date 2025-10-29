package repositories;

import models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderListRepo implements OrderRepo {
    private List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Optional<Order> getOrderById( String id ) {
        return this.orders
                .stream()
                .filter( order -> order.id().equals( id ) )
                .findFirst();
    }

    @Override
    public Order addOrder( Order newOrder ) {
        orders.add( newOrder );
        return newOrder;
    }

    @Override
    public Order updateOrder( Order updatedOrder ) {
        this.removeOrder( updatedOrder.id() );
        this.addOrder( updatedOrder );
        return updatedOrder;
    }

    @Override
    public void removeOrder( String id ) {
        for ( Order order : orders ) {
            if ( order.id().equals( id ) ) {
                orders.remove( order );
                return;
            }
        }
    }
}
