package utils;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    private int ano = 2020;
    private int ano_incorreto = 500;

    @Test
    public void ano_incorreto() {
        ValidarAnoException e = assertThrows(ValidarAnoException.class, () -> {
            Validation.validarAnoLancamento(ano_incorreto);
        });

        assertEquals("O ano deve estar entre 1000 e " + Year.now().getValue(), e.getMessage());
    }

    @Test
    public void ano_correto() {
        assertDoesNotThrow(() -> {
            Validation.validarAnoLancamento(ano);
        });
    }
}