import org.junit.jupiter.api.Assertions;

public class JavaTest {

    public static void pathContainsUser(String path, String user) {
        Assertions.assertTrue(path.contains(user));
    }

    public static void alwaysSuccess(String path, String user) {
        Assertions.assertTrue(true);
    }
}
