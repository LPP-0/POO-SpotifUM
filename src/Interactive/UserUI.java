package Interactive;

import DTO.*;
import user.*;
import controller.*;
import utils.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserUI {

    public static LocalDate lerDataNascimento(Scanner sc) {
        Input userInput = new Input(sc);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthday = null;

        while (true) {
            System.out.print("Birthday (dd/MM/yyyy): ");

            String input = userInput.inputString().trim();


            try {
                Validation.isDataValida(input);

                birthday = LocalDate.parse(input, formatter);

                try {
                    Validation.validarDataNascimento(birthday);
                    return birthday;
                }
                catch (ValidarDataException e) {
                    System.out.println(e.getMessage());
                }
            } catch (ValidarDataException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    public static String insertHiddenPassword(Scanner sc){
        Input userInput = new Input(sc);
        String password;

        Console console = System.console();
        if (console != null){
            char[] passwordArray = console.readPassword("Password: ");
            password = new String(passwordArray);
        }
        else {   // Executa caso não haja terminal (IDE)
            System.out.print("Password: ");
            password = userInput.inputString();
        }

        return password;
    }


    public static String lerPassword(Scanner sc){

        String password;

        while (true){

            password = insertHiddenPassword(sc);

            if(isPasswordValid(password)) return password;
        }
    }

    public static boolean isPasswordValid(String password){
        try {
            Validation.validarPassword(password);  //
            return true;
        } catch (PasswordValidatorException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public static UserDTO UserRegistration(UserController userController,Scanner sc) {
        int subscriptionPlan = -1;
        Input userInput = new Input(sc);

        System.out.println("\n\t -----------REGISTAR USER-----------\n");

        // Seleção do plano
        while(true) {

            Menu.menuUserPlans();

            int input = userInput.inputInt();

            if (input >= 0 && input <= 2) {
                subscriptionPlan = input;
                break;
            }
            else if(input == 3){
                Menu.TabelaPlanos(sc);
            }
            else {
                System.out.println("Erro: o número deve estar entre 0 e 2.");
            }
        }

        // Coleta de dados
        System.out.print("Username: ");
        String username = userInput.inputString().trim().toLowerCase();
        if(userController.getUser(username) != null){
            System.out.println("Já existe um utilizador com este username!");
            System.out.println("Faça login ou tente novamente com um username diferente.");
            System.out.println("A voltar ao menu inicial.");
            return null;
        }

        System.out.print("Name: ");
        String name = userInput.inputString();

        System.out.print("Email: ");
        String email = userInput.inputString();

        System.out.print("Address: ");
        String address = userInput.inputString();

        LocalDate birthday = lerDataNascimento(sc);

        String password = lerPassword(sc);

        return new UserDTO(
                username,
                name,
                email,
                address,
                password,
                birthday,
                subscriptionPlan
        );
    }

    public static List<String> menuLogin(Scanner sc){
        Input userInput = new Input(sc);
        StringBuilder sb = new StringBuilder(
                "             -----------LOGIN-----------\n\n"
        );
        sb.append("username: ");
        System.out.print(sb.toString());
        String username = userInput.inputString();

        String password = insertHiddenPassword(sc);

        List<String> login = new ArrayList<>();
        login.add(username);
        login.add(password);


        return login;
    }

    public static int userMenu(User utilizador,Scanner sc) {
        Input userInput = new Input(sc);
        StringBuilder menu = new StringBuilder();


        if (utilizador instanceof UserFree) {
            Menu.menuUserFree();
        } else if (utilizador instanceof UserPremiumBase || utilizador instanceof UserPremiumTop) {
            Menu.menuUserPremium();
        }
        int op = -1;

        op = userInput.inputInt();

        return op;
    }
}
