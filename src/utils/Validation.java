package utils;

import java.time.*;
import java.time.format.*;

public class Validation {


    public static void validarPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new PasswordValidatorException("A palavra-passe não pode estar vazia.");
        }

        if (password.length() < 6) {
            throw new PasswordValidatorException("A palavra-passe deve ter pelo menos 6 caracteres.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new PasswordValidatorException("A palavra-passe deve conter pelo menos uma letra maiúscula.");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new PasswordValidatorException("A palavra-passe deve conter pelo menos uma letra maiúscula.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new PasswordValidatorException("A palavra-passe deve conter pelo menos um número.");
        }
    }

    public static void validarAnoLancamento(int ano) throws ValidarAnoException {
        int ano_atual = Year.now().getValue();
        if(ano < 1000 || ano > ano_atual){
            throw new ValidarAnoException("O ano deve estar entre 1000 e " + ano_atual);
        }
    }




    public static void isDataValida(String dataStr) throws ValidarDataException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            throw new ValidarDataException("Formato de data inválido! Use dd/MM/yyyy.");
        }
    }

    public static void validarDataNascimento(LocalDate dataNascimento) throws ValidarDataException {
        LocalDate dataMinima = LocalDate.of(1900, 1, 1);
        LocalDate dataAtual = LocalDate.now();

        if( dataNascimento.isBefore(dataMinima) || dataNascimento.isAfter(dataAtual)) {
            throw new ValidarDataException("Data inválida! A data deve ser entre 01/01/1900 e " + dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
        }
    }

    @Override
    public String toString() {
        return "Classe de validações de utilidade";
    }
}
