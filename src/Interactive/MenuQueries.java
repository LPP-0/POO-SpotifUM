package Interactive;

import queries.Statistics;
import music.*;
import user.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class MenuQueries {

    public static void statisticsMenu(Statistics stats, Scanner sc) {
        int opcao = -1;
        Input userInput = new Input(sc);

        do {
            System.out.println("\n\t========== ESTATÍSTICAS ==========\n");
            System.out.println("1. Música mais reproduzida");
            System.out.println("2. Artista mais popular");
            System.out.println("3. Top utilizador por reproduções");
            System.out.println("4. Utilizador com mais pontos");
            System.out.println("5. Género mais popular");
            System.out.println("6. Número de playlists públicas");
            System.out.println("7. Utilizador com mais playlists");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção (0-7): ");

            opcao = userInput.inputInt();
            if (opcao >= 1 && opcao <= 7) {
                MenuQueries.execQuery(opcao, stats, sc);
            }
            else if (opcao != 0) {
                System.out.println("Opção inválida. Escolha um número de 0 a 7.");
            }
        } while(opcao != 0);
    }

    public static void execQuery(int opcao, Statistics stats, Scanner sc) {
        switch (opcao) {
            case 1 -> {
                Music musica = stats.getMostPlayedSong();
                System.out.println("\n========== QUERY 1: MÚSICA MAIS REPRODUZIDA ==========\n");
                if (musica != null)
                    System.out.println("Música mais reproduzida: " + musica.getTitle() + " (" + musica.getStreams() + " streams)");
                else
                    System.out.println("Nenhuma música encontrada.");
            }
            case 2 -> {
                System.out.println("\n========== QUERY 2: INTÉRPRETE MAIS ESCUTADO ==========\n");
                try{
                    String artista = stats.getMostPopularArtist();
                    if (artista != null && !artista.isEmpty())
                        System.out.println("Artista mais popular: " + artista);
                    else
                        System.out.println("Nenhum artista encontrado.");
                }catch (NoSuchElementException e){
                    System.out.println(e.getMessage());
                }

            }
            case 3 -> menuQuery3(stats, sc);  // passa Scanner
            case 4 -> {
                System.out.println("\n========== QUERY 4: UTILIZADOR COM MAIS PONTOS ==========\n");
                User userPoints = stats.getUserWithMostPoints();
                if (userPoints != null) {
                    System.out.println(userPoints.getUsername() + " - " + userPoints.getPoints() + " pontos");
                }
                else
                    System.out.println("Nenhum utilizador com pontos encontrado.");
            }
            case 5 -> {
                System.out.println("\n========== QUERY 5: GÉNERO DE MÚSICA MAIS REPRODUZIDO ==========\n");
                try {
                    String genero = stats.getMostPlayedGenre();
                    System.out.println("Género mais popular: " + genero);
                }catch (NoSuchElementException e) {
                    System.out.println(e.getMessage());
                }

            }
            case 6 -> {
                System.out.println("\n========== QUERY 6: TOTAL DE PLAYLISTS PÚBLICAS ==========\n");
                int num = stats.getNumPublicPlaylists();
                System.out.println("Número de playlists públicas: " + num);
            }
            case 7 -> {
                System.out.println("\n========== QUERY 7: UTILIZADOR COM MAIS PLAYLISTS ==========\n");
                User userPlaylist = stats.getUserWithMostPlaylists();
                if (userPlaylist != null)
                    System.out.println("Utilizador com mais playlists: " + userPlaylist.getUsername());
                else
                    System.out.println("Nenhum Utilizador com playlists encontradas.");
            }
            default -> System.out.println("Opção inválida. Escolha um número de 0 a 7.");
        }
    }



    public static void menuQuery3(Statistics stats, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n========== QUERY 3: Utilizador que ouviu mais músicas num período, ou desde sempre ==========\n");
        System.out.println("Insira a data de início (formato: dd/MM/yyyy), ou pressione Enter para ignorar:");
        String startInput = sc.nextLine().trim();

        System.out.println("Insira a data de fim (formato: dd/MM/yyyy), ou pressione Enter para ignorar:");
        String endInput = sc.nextLine().trim();

        LocalDate startDate = null, endDate = null;

        try {
            if (!startInput.isEmpty()) {
                startDate = LocalDate.parse(startInput, formatter);
            }
            if (!endInput.isEmpty()) {
                endDate = LocalDate.parse(endInput, formatter);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use o formato dd/MM/yyyy.");
            return;
        }

        User topUser = stats.getTopUserByPlays(startDate, endDate);

        if (topUser != null) {
            if (startDate == null && endDate == null) {
                System.out.println("Utilizador com mais reproduções (desde sempre): " + topUser.getUsername());
            } else {
                System.out.print("Utilizador com mais reproduções ");
                if (startDate != null) System.out.print("desde " + formatter.format(startDate) + " ");
                if (endDate != null) System.out.print("até " + formatter.format(endDate));
                System.out.println(": " + topUser.getUsername());
            }
        } else {
            System.out.println("Nenhuma reprodução encontrada no período especificado.");
        }
    }




}
