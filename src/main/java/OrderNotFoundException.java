public class OrderNotFoundException extends Exception {
    public OrderNotFoundException( String orderId ) {
        super( "Could not find order with id " + orderId );
    }
}
