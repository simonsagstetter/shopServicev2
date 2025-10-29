package models;

import services.IdService;

public record Product(
        String id,
        String name
) {

    public Product( String name ) {
        this( IdService.generateIdFor( Product.class ), name );
    }

}
