package simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags({@Tag("simple"), @Tag("positive")})
public class PositiveTest {
    @Test
    void test1() {
        Assertions.assertTrue(true);
    }

    @Test
    void test2() {
        Assertions.assertTrue(true);
    }

    @Test
    void test3() {
        Assertions.assertTrue(true);
    }

    @Test
    void test4() {
        Assertions.assertTrue(true);
    }

    @Test
    void test5() {
        Assertions.assertTrue(true);
    }

    @Test
    void test6() {
        Assertions.assertTrue(true);
    }

    @Test
    void test7() {
        Assertions.assertTrue(true);
    }
}