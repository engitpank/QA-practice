package simple;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags({@Tag("simple"), @Tag("ignored")})
public class IgnoredTest {
    @Test
    @Disabled("Just disabled test")
    void ignored_test() {

    }
}
