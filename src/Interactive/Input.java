package Interactive;

import java.util.Scanner;

public class Input {
    private Scanner sc = null;

    public Input(Scanner sc) {
        this.sc = sc;
    }

    public Input() {
        this.sc = new Scanner(System.in);
    }

    public String inputString() {
        String input;
        do {
            input = this.sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Erro: entrada não pode estar vazia. Tente novamente.");
            }
        } while (input.isEmpty());
        return input;
    }

    public int inputInt() {
        int number = 0;
        boolean valid = false;
        do {
            String input = this.sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Erro: entrada não pode estar vazia. Tente novamente.");
                continue;
            }
            try {
                number = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Erro: entrada deve ser um número inteiro. Tente novamente.");
            }
        } while (!valid);
        return number;
    }



    @Override
    public Input clone() {
        try {
            Input clone = (Input) super.clone();
            // Uma cópia superficial do Scanner é geralmente suficiente para esta classe.
            // Se precisasse de uma cópia independente do estado interno do Scanner (se houvesse),
            // isso exigiria uma lógica mais complexa, pois a classe Scanner não suporta clonagem profunda facilmente.
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
