# Shop Service v2

[![CI](https://github.com/simonsagstetter/shopServicev2/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/simonsagstetter/shopServicev2/actions/workflows/maven.yml)

## Implemented assignments

Required: [Check pull request #2, #4, #6, #8, #10](https://github.com/simonsagstetter/shopServicev2/pulls?q=is%3Apr)

Bonus: [Check pull request #12](https://github.com/simonsagstetter/shopServicev2/pulls?q=is%3Apr)

## Basic Usage

```java
import java.util.List;

public class Main {
    public static void main( String[] args ) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();

        for ( int i = 0; i < 100; i++ ) {
            productRepo.addProduct(
                    new Product( String.valueOf( i ), "Product No. " + i )
            );
        }

        ShopService shopService = new ShopService( productRepo, orderRepo );

        List<Product> products = productRepo.getProducts();

        try {
            Order firstOrder = shopService.addOrder( List.of( products.getFirst().id() ) );
            Order secondOrder = shopService.addOrder( List.of( products.getFirst().id(), products.getLast().id() ) );
            Order thirdOrder = shopService.addOrder( products.stream().map( Product::id ).toList() );
        } catch ( ProductNotFoundException error ) {
            throw new RuntimeException( error );
        }

        List<Order> newOrders = shopService.getOrdersByOrderStatus( OrderStatus.PROCESSING );

        System.out.printf( "%-50s", "ORDERID" );
        System.out.printf( "%-10s", "PRODUCTS" );
        System.out.printf( "%20s%n", "STATUS" );

        for ( Order order : newOrders ) {
            System.out.printf( "%-50s", order.id() );
            System.out.printf( "%-10s", order.products().size() );
            System.out.printf( "%20s%n", order.orderStatus() );
        }
    }
}
```