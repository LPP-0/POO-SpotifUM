package Interactive;

import org.junit.jupiter.api.Test;
import utils.PasswordValidatorException;
import utils.Validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserUITest {
    private String valid_password = "Abc123";
    private String invalid_password = "1234";

    @Test
    public void testPassword_wrong() {
        PasswordValidatorException e = assertThrows(PasswordValidatorException.class, () -> {
            Validation.validarPassword(invalid_password);
        });

        assertEquals("A palavra-passe deve ter pelo menos 6 caracteres.", e.getMessage());
    }

    @Test
    public void testPassword_valid() {
        assertDoesNotThrow(() -> Validation.validarPassword(valid_password));
    }
}