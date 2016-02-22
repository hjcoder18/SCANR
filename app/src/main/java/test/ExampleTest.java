package test;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

/**
 * Created by Hunter on 2/22/2016.
 */
public class ExampleTest extends InstrumentationTestCase{
    // this will fail
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}
