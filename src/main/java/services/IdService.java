package services;

import java.util.UUID;

public final class IdService {

    private IdService() {
    }

    public static <T> String generateIdFor( Class<T> forClass ) {
        String uniqueID = UUID.randomUUID().toString().replace( "-", "" );
        return forClass.getSimpleName() + "-" + uniqueID;
    }
}
