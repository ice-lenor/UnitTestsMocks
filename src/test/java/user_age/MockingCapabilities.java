package user_age;

import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// This class demonstrates some mocking capabilities,
// available via the Mockito framework.
// See full documentation here:
// https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
public class MockingCapabilities {

    @Test
    public void mockExampleVerifyCalls() {
        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        User user = new User(42, "Ada Lovelace", DateHelpers.parseDate("1815-12-10"));
        Mockito.when(usersDatabase.getUserById(42)).thenReturn(user);
        Mockito.when(usersDatabase.getUserById(42)).thenReturn(user);

        // call to the mock
        usersDatabase.getUserById(42);

        // we ensure that the getUserById method was called with the argument userId=42
        verify(usersDatabase).getUserById(42);

        // we can also verify that getUserById with userId=0 was never called
        verify(usersDatabase, never()).getUserById(0);

        // alternatively, we can check that getUserById was called with any userId,
        // we don't care exactly which one
        verify(usersDatabase, times(1)).getUserById(anyInt());

        // atMost and atLeast help you to ensure it was called not more (or not less) than N times
        verify(usersDatabase, atMost(1)).getUserById(anyInt());
    }

    @Test
    public void mockExampleTwoCalls() {

        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);

        // mock two calls
        when(usersDatabase.getUserById(anyInt()))
                .thenReturn(new User(999, "Mary Jackson", DateHelpers.parseDate("1921-04-09")))
                .thenThrow(new RuntimeException("Second call throws an exception"));

        // Ensure that the first call returns Mary Jackson
        User u1 = usersDatabase.getUserById(1);
        assertEquals(u1.getName(), "Mary Jackson");

        // and the second call throws an exception
        assertThrows(RuntimeException.class, () -> {
            usersDatabase.getUserById(2);
        });
    }

    @Test
    public void mockExampleCaptureArguments() {
        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);

        // call to the mock
        usersDatabase.getUserById(99);

        // get the arguments the call was using - after the call happened!
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(usersDatabase).getUserById(argument.capture());
        int userIdTheMethodWasCalledWith = argument.getValue();
        // ensure that the arguments are valid
        assertEquals(99, userIdTheMethodWasCalledWith);
    }
}
