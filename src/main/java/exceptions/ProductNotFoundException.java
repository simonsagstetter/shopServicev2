package exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException( String productId ) {
        super( "Could not find product with " + productId );
    }
}
