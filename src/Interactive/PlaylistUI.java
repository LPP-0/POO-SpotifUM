package Interactive;

import DTO.*;
import playlist.*;
import music.Music;
import user.*;
import controller.*;

import java.util.*;

public class PlaylistUI {


    public static PlaylistDTO menuCriarPlaylist(User utilizador, Scanner sc) {
        StringBuilder sb = new StringBuilder();
        Input userInput = new Input(sc);

        sb.append("\n   --------------- Criar Nova Playlist ------------------\n");
        sb.append("Digite o nome da playlist: ");
        System.out.print(sb);
        String nome = userInput.inputString();

        boolean isRandom = false;
        boolean isPublic = true;
        int playlistType = 0;

        Menu.menuTiposPlaylists();
        playlistType = userInput.inputInt();

        isRandom = (playlistType == 4); // só é aleatória se for tipo 4

        System.out.print("A playlist será pública? (s/n): ");
        String priv = userInput.inputString();
        isPublic = priv.equalsIgnoreCase("s");

        // Por enquanto, a coleção de músicas começa vazia
        List<Music> musicas = new ArrayList<>();

        // Cria o DTO (id e faixa atual são definidos depois)
        return new PlaylistDTO(
                null, nome, musicas, isRandom, !isPublic,playlistType );
    }

    private static List<Music> lerMusicasSelecionadas(Scanner sc, MusicController musicController) {
        List<Music> escolhidas = new ArrayList<>();
        Input userInput = new Input(sc);

        while (true) {
            System.out.print("ID da música: ");
            String input = userInput.inputString().trim();

            if (input.equalsIgnoreCase("fim")) break;

            Music encontrada = musicController.getMusic(input);
            if (encontrada != null) {
                escolhidas.add(encontrada);
                System.out.println("Música adicionada.");
            } else {
                System.out.println("ID inválido. Tente novamente.");
            }
        }

        return escolhidas;
    }

    public static List<Music> createListPlaylist(MusicController musicController) {
        Scanner sc = new Scanner(System.in);
        MusicUI.exibirTodasMusicas(musicController);
        return lerMusicasSelecionadas(sc, musicController);
    }




    public static int imprimirPlaylistsUser(List<Playlist> playlists, int contador, Map<Integer, Object> opcoes) {
        if (!playlists.isEmpty()) {
            System.out.println("\nSuas Playlists:");
            for (Playlist p : playlists) {
                System.out.printf("%d - %s (%d músicas)%n", contador, p.getTitle(), p.getMusicCollection().size());
                opcoes.put(contador, p);
                contador++;
            }
        }
        return contador;
    }

    public static int imprimirPlaylistsPublicas(List<Playlist> playlists, int contador, Map<Integer, Object> opcoes) {
        if (!playlists.isEmpty()) {
            System.out.println("\nPlaylists Públicas:");
            for (Playlist p : playlists) {
                System.out.printf("%d - %s (%d músicas)%n", contador, p.getTitle(), p.getMusicCollection().size());
                opcoes.put(contador, p);
                contador++;
            }
        }
        return contador;
    }

    public static int pedirDuracaoMaxima(Scanner sc) {
        Input userInput = new Input(sc);
        System.out.println("\nQual a duração máxima da playlist? (em minutos)");

        while (true) {
            System.out.print("Duração (minutos): ");
            int minutos = userInput.inputInt();
            if (minutos > 0) {
                return minutos * 60; // retorna em segundos
            } else {
                System.out.println(" A duração deve ser maior que zero.");
            }

        }
    }

    public static boolean soExplicitas(Scanner sc){
        boolean soExplicitas = false;
        Input userInput = new Input(sc);

        System.out.print("A Playlist será composta somente por musicas explicitas? (s/n): ");
        String priv = userInput.inputString();
        soExplicitas = priv.equalsIgnoreCase("s");

        return soExplicitas;
    }

}
