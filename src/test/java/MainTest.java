import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void Main_ShouldNotThrow_WhenCalled() {
        assertDoesNotThrow( () -> {
            Main.main( new String[0] );
        } );
    }

}