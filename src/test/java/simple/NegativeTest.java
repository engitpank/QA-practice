package simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags({@Tag("simple"), @Tag("negative")})
public class NegativeTest {
    @Test
    void negative_test1() {
        Assertions.assertTrue(false);
    }

    @Test
    void negative_test2() {
        Assertions.assertTrue(false);
    }

    @Test
    void negative_test3() {
        Assertions.assertTrue(false);
    }
}
