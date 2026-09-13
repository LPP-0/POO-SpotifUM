package Interactive;

import album.Album;
import estado.Estado;
import music.Music;
import playlist.Playlist;
import user.*;
import utils.Genre;
import utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Menu {
    public static void welcome() {

        System.out.println("\n\t\t   Bem-vindo ao SpotifUM!");

        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        String filePath = "src/logo.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            System.out.print(ANSI_GREEN);
            for (String line : lines) {
                System.out.println(line);
            }
            System.out.print(ANSI_RESET);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static int mainMenu(Scanner sc){
        int r = -1;
        Input userInput = new Input(sc);

        while(true){
            StringBuilder sb = new StringBuilder("\n\t   -----------MENU INICIAL-----------\n\n");
            sb.append("1) Iniciar sessão.\n");
            sb.append("2) Registar novo utilizador.\n");
            sb.append("0) Sair.\n\n");
            sb.append("Selecione a opção pretendida: ");
            System.out.println(sb.toString());

            int op = -1;
            while (true) {
                op = userInput.inputInt();
                if(op >= 0 && op < 3){
                    return op;
                }
                System.out.print("opção invalida. Tente novamente: ");
            }
        }
    }



    public static Genre escolherGenero(Scanner sc) {
        Genre[] generos = Genre.values();
        Input userInput = new Input(sc);

        System.out.println("\nEscolha um género musical:");

        for (int i = 0; i < generos.length; i++) {
            System.out.printf("%d - %s%n", i + 1, generos[i].name());
        }

        while (true) {
            System.out.print("Digite o número do género desejado: ");
            try {
                int opcao = userInput.inputInt();
                if (opcao >= 1 && opcao <= generos.length) {
                    return generos[opcao - 1];
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }


    public static Map.Entry<Integer, Boolean> menuFiltroPlaylist(Scanner sc) {
        int tempoMaximo = -1;
        boolean apenasExplicitas = false;

        // Ler tempo máximo
        while (true) {
            System.out.print("Digite o tempo máximo de reprodução da playlist (em segundos) [pressione Enter para ilimitado]: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                tempoMaximo = -1;
                break;
            }

            try {
                tempoMaximo = Integer.parseInt(input);
                if (tempoMaximo <= 0) {
                    System.out.println("O tempo deve ser maior que zero ou vazio para ilimitado.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro ou pressione Enter para ilimitado.");
            }
        }

        // Ler se apenas explícitas
        while (true) {
            System.out.print("A playlist deve conter apenas músicas explícitas? (s/n): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("s")) {
                apenasExplicitas = true;
                break;
            } else if (input.equals("n")) {
                apenasExplicitas = false;
                break;
            } else {
                System.out.println("Resposta inválida. Digite 's' para sim ou 'n' para não.");
            }
        }

        return new AbstractMap.SimpleEntry<>(tempoMaximo, apenasExplicitas);
    }


    public static void menuTiposPlaylists(){
        StringBuilder str = new StringBuilder();

        str.append("\nEscolha o tipo de playlist:\n");
        str.append("1 - Playlist por Género\n");
        str.append("2 - Playlist Normal\n");
        str.append("3 - Playlist de favoritos\n");
        str.append("4 - Playlist Aleatória\n");
        str.append("Opção:\n");

        System.out.print(str);
    }

    public static Object playContentMenu(User user, Estado estado, Scanner sc) {
        Input userInput = new Input(sc);
        List<Playlist> playlistsUser = user.getPlaylists();
        List<Album> albumUser = user.getAlbums();
        List<Playlist> playlistsPublicas = estado.getPlaylistController().getPlaylistsPublicas();
        List<Album> albuns = new ArrayList<>(estado.getAlbumController().getAllAlbums());

        int contador = 1;
        Map<Integer, Object> opcoes = new HashMap<>();

        contador = PlaylistUI.imprimirPlaylistsUser(playlistsUser, contador, opcoes);
        contador = AlbumUI.imprimirAlbunsUser(albumUser, contador, opcoes);
        contador = PlaylistUI.imprimirPlaylistsPublicas(playlistsPublicas, contador, opcoes);
        contador = AlbumUI.imprimirAlbuns(albuns, contador, opcoes);

        if (opcoes.isEmpty()) {
            System.out.println("Não existem conteúdos disponíveis para reprodução.");
            return null;
        }

        System.out.println("\n0 - Voltar ao menu principal");
        System.out.print("Digite o número da opção a ser reproduzida: ");
        int escolha;

        while (true) {
            escolha = userInput.inputInt(); // usa a função segura com nextLine()

            if (escolha == 0) {
                return null;
            }

            if (opcoes.containsKey(escolha)) {
                Object escolhido = opcoes.get(escolha);

                if (escolhido instanceof Playlist) {
                    System.out.println(" Playlist escolhida.");
                    return (Playlist) escolhido;

                } else if (escolhido instanceof Album) {
                    System.out.println(" Álbum escolhido.");
                    return (Album) escolhido;

                } else {
                    System.out.println(" Tipo de conteúdo desconhecido.");
                    return null;
                }

            } else {
                System.out.print("Opção inválida. Tente novamente: ");
            }
        }
    }

    public static String salvarEstadoProgramaMenu(Scanner sc){
        Input userInput = new Input(sc);
        StringBuilder sb = new StringBuilder("             -----------SALVAR ESTADO DO PROGRAMA-----------\n\n");
        sb.append("Nome do ficheiro: ");
        System.out.println(sb.toString());

        return userInput.inputString();
    }

    public static String carregarEstadoProgramaMenu(Scanner sc){
        Input userInput = new Input(sc);
        StringBuilder sb = new StringBuilder("             -----------CARREGAR ESTADO DO PROGRAMA-----------\n\n");
        sb.append("Nome do ficheiro: ");
        System.out.println(sb.toString());

        return userInput.inputString();
    }



    public static int reproductionType(Scanner sc){
        Input userInput = new Input(sc);
        StringBuilder menu = new StringBuilder();

        menu.append("\nEscolha o tipo de reprodução: \n");

        menu.append("0 - Random\n");

        menu.append("1 - Normal\n");

        System.out.println(menu.toString());

        int escolha;
        while(true){

            escolha = userInput.inputInt();
            if (escolha >= 0 || escolha < 2) {
                return escolha;
            } else {
                System.out.print("Escolha inválida. Tente novamente: ");
            }
        }

    }

    public static void menuUserPlans(){
        StringBuilder sb = new StringBuilder();

        sb.append("Plano de Subscrição:\n");
        sb.append("0. Free\n");
        sb.append("1. Premium Base\n");
        sb.append("2. Premium Top\n");
        sb.append("3. Consultar vantagens de cada Plano\n");
        sb.append("Opção: \n");

        System.out.print(sb.toString());
    }


    public static void menuUserFree(){
        StringBuilder menu = new StringBuilder();
        menu.append("\n\t========== MENU UTILIZADOR ==========\n\n");
        menu.append("1 - Reproduzir random playlist\n");
        menu.append("2 - Salvar estado do programa\n");
        menu.append("3 - Carregar estado do programa\n");
        menu.append("9 - Consultar estatísticas\n");
        menu.append("10 - Logout\n");
        menu.append("Escolha uma opção: \n");

        System.out.print(menu.toString());

    }

    public static void menuUserPremium(){
        StringBuilder menu = new StringBuilder();
        menu.append("\n\t========== MENU UTILIZADOR ==========\n\n");

        menu.append("0 - Adicionar música\n");
        menu.append("1 - Reproduzir conteúdo\n");
        menu.append("2 - Criar playlist\n");
        menu.append("3 - Adicionar álbum à biblioteca\n");
        menu.append("4 - Salvar estado do programa\n");
        menu.append("5 - Carregar estado do programa\n");
        menu.append("6 - Criar álbum\n");
        menu.append("9 - Consultar estatísticas\n");
        menu.append("10 - Logout\n");
        menu.append("Escolha uma opção: \n");

        System.out.print(menu.toString());
    }

    public static void TabelaPlanos(Scanner sc) {
        final String RESET = "\u001B[0m";
        final String YELLOW = "\u001B[33m";
        final String CYAN = "\u001B[36m";

        StringBuilder tabela = new StringBuilder();

        tabela.append(CYAN)
                .append("\n\n                 ========================= PLANOS DE SUBSCRIÇÃO =========================\n")
                .append(RESET);

        // Ajuste de larguras: 60 para descrição, 15 para cada coluna
        String format = "%-60s%-15s%-15s%-15s\n";

        tabela.append(String.format(format, "", "Free", "PremiumBase", "PremiumTop"));

        tabela.append(String.format(format, "Criar músicas", "×", "✓", "✓"));
        tabela.append(String.format(format, "Criar álbuns", "×", "✓", "✓"));
        tabela.append(String.format(format, "Criar playlists", "×", "✓", "✓"));
        tabela.append(String.format(format, "Ouvir playlists aleatórias", "✓", "✓", "✓"));
        tabela.append(String.format(format, "Criar playlists aleatórias", "×", "✓", "✓"));
        tabela.append(String.format(format, "Criar playlists normal (tu escolhes as músicas)", "×", "✓", "✓"));
        tabela.append(String.format(format, "Criar playlists de um género específico", "×", "✓", "✓"));
        tabela.append(String.format(format, "Gerar playlist favorita (criada com base nos teus gostos)", "×", "×", "✓"));
        tabela.append(String.format(format, "Retroceder música", "×", "✓", "✓"));
        tabela.append(String.format(format, "Avançar música", "✓", "✓", "✓"));
        tabela.append(String.format(format, "Sair da reprodução", "✓", "✓", "✓"));
        tabela.append(String.format(format, "Consultar estatísticas", "✓", "✓", "✓"));
        tabela.append(String.format(format, "Pontos por reprodução", "5 pts", "10 pts", "2.5% dos pontos totais"));
        tabela.append(String.format(format, "Bónus de adesão", "×", "×", "✓ +100 pts"));

        tabela.append(YELLOW);
        tabela.append(String.format(format, "Preço", "0€/Mês", "5€/Mês", "10€/Mês"));
        tabela.append(RESET);

        tabela.append("\nDigite 0 para voltar à página de registo.\n");

        System.out.println(tabela);

        while (true){
            String input = sc.nextLine().trim();
            if (input.equals("0")){
                break;
            }
            else {
                System.out.println("Comando Inválido. Digite 0 para voltar à página de registo.");
            }
        }
    }

    public static void apresentacaoReproducao(List<Music> musicas, char tipo, String titulo, String artista, int anoLancamento, boolean premium){

        int segundosT = musicas.stream().mapToInt(Music::getDuration).sum();
        String duracaoFormatada = Utils.formatarDuracao(segundosT);

        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append(tipo == 'A' ? "Álbum" : "Playlist").append(" • ").append(titulo).append("\n");

        if (tipo == 'A') {
            sb.append(artista);
            if (anoLancamento > 0) {
                sb.append(" • ").append(anoLancamento);
            }
            sb.append(" • ");
        }

        sb.append(musicas.size()).append(" músicas  • ").append(duracaoFormatada).append("\n");

        sb.append("Comandos: Q - Parar | N - Próxima música");
        if (premium) sb.append(" | P - Música anterior");

        sb.append("\n\n");

        System.out.print(sb);
    }

    /// ///////////////////////////////////// input ///////////////////////////////////////////////////////////


}