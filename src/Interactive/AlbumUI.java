package Interactive;

import DTO.*;
import album.*;
import controller.*;
import utils.*;

import java.util.*;

public class AlbumUI {

    public static int lerAnoLancamentoAlbum(Scanner sc) {
        Input userInput = new Input(sc);

        while (true) {
            System.out.print("Ano de lançamento (formato AAAA): ");
            int input = userInput.inputInt();

            try {
                    Validation.validarAnoLancamento(input);
                    return input;
            }catch (ValidarAnoException e){
                System.out.println(e.getMessage());
            }

        }

    }


    public static AlbumDTO menuAdicionarAlbum(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        Input userInput = new Input(sc);

        sb.append("\n\t========== ADICIONAR ÁLBUM ==========\n");
        sb.append("Insira os dados do álbum conforme solicitado.\n");

        System.out.print(sb);

        System.out.print("Nome do álbum: ");
        String nome = userInput.inputString();

        System.out.print("Artista: ");
        String artista = userInput.inputString();

        int ano = lerAnoLancamentoAlbum(sc);

        return new AlbumDTO(nome, artista, ano);
    }

    public static int imprimirAlbunsUser(List<Album> albuns, int contador, Map<Integer, Object> opcoes) {
        if (!albuns.isEmpty()) {
            System.out.println("\nÁlbums do User:");
            for (Album a : albuns) {
                System.out.printf("%d - %s (%d faixas)%n", contador, a.getName(), a.getTracks().size());
                opcoes.put(contador, a);
                contador++;
            }
        }
        return contador;
    }

    public static int imprimirAlbuns(List<Album> albuns, int contador, Map<Integer, Object> opcoes) {
        if (!albuns.isEmpty()) {
            System.out.println("\nÁlbums Disponíveis:");
            for (Album a : albuns) {
                System.out.printf("%d - %s (%d faixas)%n", contador, a.getName(), a.getTracks().size());
                opcoes.put(contador, a);
                contador++;
            }
        }
        return contador;
    }





    public static void listarAlbuns(List<Album> albuns){
        for (Album a : albuns) {
            System.out.printf("%s - %s (%d faixas)%n", a.getId(), a.getName(), a.getTracks().size());
        }
    }

    public static Album menuAddAlbumBiblioteca(AlbumController albumController,Scanner sc) {
        Input userInput = new Input(sc);

        StringBuilder sb = new StringBuilder();

        sb.append("\n   ========== ADICIONAR ÁLBUM À BIBLIOTECA ==========\n");
        sb.append("Insira o ID do álbum que deseja adicionar à sua biblioteca pessoal.\n");
        sb.append("Exemplo: A1, A20, etc.\n");

        System.out.print(sb);


        Collection<Album> albunslist = albumController.getAllAlbums();
        listarAlbuns(albunslist.stream().toList());

        String id = userInput.inputString().trim();

        Album album = albumController.getAlbum(id);

        if (album != null){
            return album;
        }
        else {
            System.out.println("Erro: Não existe nenhum álbum com o ID \"" + id + "\".\n");
            return null;
        }
    }


}
