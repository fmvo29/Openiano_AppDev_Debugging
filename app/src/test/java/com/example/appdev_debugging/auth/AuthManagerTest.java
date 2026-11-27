package com.example.appdev_debugging.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.appdev_debugging.data.InMemoryUserRepository;
import com.example.appdev_debugging.data.UserRepository;

import org.junit.Before;
import org.junit.Test;

public class AuthManagerTest {

    private AuthManager authManager;

    @Before
    public void setUp() {
        UserRepository repository = new InMemoryUserRepository();
        authManager = new AuthManager(repository);
    }

    @Test
    public void login_withDifferentEmailCasing_succeeds() {
        String registeredEmail = "Juan.DelaCruz@Mail.com";
        String password = "Password123";

        AuthResult regResult = authManager.register("Juan Dela Cruz", registeredEmail, password, password);
        assertTrue("Registration should succeed", regResult.isSuccess());

        // Login with exact casing
        AuthResult loginExact = authManager.login("Juan.DelaCruz@Mail.com", password);
        assertTrue("Login with exact casing should succeed", loginExact.isSuccess());

        // Login with all lowercase
        AuthResult loginLower = authManager.login("juan.delacruz@mail.com", password);
        assertTrue("Login with lowercase email should succeed", loginLower.isSuccess());

        // Login with all uppercase
        AuthResult loginUpper = authManager.login("JUAN.DELACRUZ@MAIL.COM", password);
        assertTrue("Login with uppercase email should succeed", loginUpper.isSuccess());
    }

    @Test
    public void register_duplicateEmailWithDifferentCase_fails() {
        String password = "Password123";
        AuthResult first = authManager.register("Juan", "Juan.DelaCruz@Mail.com", password, password);
        assertTrue("First registration should succeed", first.isSuccess());

        AuthResult second = authManager.register("Juan", "juan.delacruz@mail.com", password, password);
        assertFalse("Second registration with different casing should fail", second.isSuccess());
        assertEquals(AuthResult.Field.EMAIL, second.getField());
    }
}
