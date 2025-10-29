package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void Product_ShouldReturnInstanceWithDefaultValues_WhenCalledWithCustomConstructor() {
        //GIVEN

        //WHEN
        Product product = new Product( "Apple" );

        //EXPECT
        assertFalse( product.id().isEmpty() );
        assertTrue( product.id().contains( Product.class.getSimpleName() ) );
        assertEquals( "Apple", product.name() );
    }

}