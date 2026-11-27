package com.example.appdev_debugging.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ValidatorTest {

    @Test
    public void validateEmail_acceptsStandardDotCom() {
        assertNull(Validator.validateEmail("juan@school.com"));
    }

    @Test
    public void validateEmail_acceptsTwoLetterCctld() {
        assertNull("Two-letter ccTLD .ph should be valid", Validator.validateEmail("juan@school.ph"));
        assertNull("Multi-part domain ending in .ph should be valid", Validator.validateEmail("juan@up.edu.ph"));
        assertNull("Government domain ending in .gov.ph should be valid", Validator.validateEmail("juan@gov.ph"));
    }

    @Test
    public void validateEmail_rejectsMalformedEmails() {
        assertNotNull(Validator.validateEmail(""));
        assertNotNull(Validator.validateEmail(null));
        assertNotNull(Validator.validateEmail("juan@"));
        assertNotNull(Validator.validateEmail("@school.ph"));
        assertNotNull(Validator.validateEmail("juan.school.ph"));
    }

    @Test
    public void validateNewPassword_rejectsWeakPasswords() {
        assertNotNull("Letters only should fail", Validator.validateNewPassword("abcdefgh"));
        assertNotNull("Digits only should fail", Validator.validateNewPassword("12345678"));
        assertNotNull("Special characters only should fail", Validator.validateNewPassword("!@#$%^&*"));
    }

    @Test
    public void validateNewPassword_acceptsValidPasswords() {
        assertNull("Letters and digits should pass", Validator.validateNewPassword("abcd1234"));
        assertNull("Letters, digits, and special chars should pass", Validator.validateNewPassword("Password123!"));
    }
}
