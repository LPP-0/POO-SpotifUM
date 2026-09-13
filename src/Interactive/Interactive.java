package Interactive;

import DTO.*;
import album.*;
import estado.Estado;
import music.MusicMultimedia;
import playlist.*;
import music.Music;
import queries.Statistics;
import user.*;
import controller.*;
import utils.Utils;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import utils.*;


public class Interactive {
    /// ///////////////////////////////////////////// ATRIBUTOS ////////////////////////////////////////////////
    private Estado estado;
    private User utilizadorLogado;

    /// ///////////////////////////////////////////// CONSTRUTOR ////////////////////////////////////////////////
    public Interactive() {
        this.estado = new Estado(
                new controller.UserController(),
                new controller.MusicController(),
                new controller.PlaylistController(),
                new controller.AlbumController()
        );

    }

    /// ///////////////////////////////////////////// MÉTODOS DE ESTADO ////////////////////////////////////////////////

    public void salvarEstado(String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(this.estado);
            System.out.println("Estado salvo em: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void carregarEstado(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Ficheiro não encontrado: " + caminhoArquivo + ". Será iniciado um novo estado.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            this.estado = (Estado) ois.readObject();
            System.out.println("Estado carregado de: " + caminhoArquivo);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
    }

    /// ///////////////////////////////////////////// START ////////////////////////////////////////////////

    public void start() {
        System.out.println("Carregar estado...");
        carregarEstado("estado.dat");

        Utils.atualizarMusicCounter(this.estado.getMusicController().getAllMusic());
        Utils.atualizarPlaylistCounter(this.estado.getPlaylistController().getAllPlaylists());
        Utils.atualizarAlbumCounter(this.estado.getAlbumController().getAllAlbums());
        Menu.welcome();
        boolean stop = false;
        Scanner sc = new Scanner(System.in);


        while (!stop) {
            int option = Menu.mainMenu(sc);
            switch (option) {
                case 1 -> userLogin(sc);
                case 2 -> userRegister(sc);
                case 0 -> stop = true;
            }
        }

        System.out.println("Salvar estado...");
        salvarEstado("estado.dat");
    }

    /// ///////////////////////////////////////////// AUTENTICAÇÃO ////////////////////////////////////////////////

    public void userLogin(Scanner sc) {
        List<String> login = UserUI.menuLogin(sc);
        this.utilizadorLogado = estado.getUserController().authenticate(login);

        if (utilizadorLogado != null) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + this.utilizadorLogado.getName() + "!");
            userArea(sc);
        } else {
            System.out.println("Username ou password incorreto!");
        }
    }


    /// ///////////////////////////////////////////// ÁREA DO UTILIZADOR ////////////////////////////////////////////////

    public void userArea(Scanner sc) {
        boolean sair = false;

        while (!sair) {
            int op = UserUI.userMenu(utilizadorLogado, sc);

            if (this.utilizadorLogado instanceof UserPremium) {
                sair = userPremiumArea(op, sc);
            } else {
                sair = userFreeArea(op, sc);
            }

        }
    }

    public boolean userFreeArea(int op, Scanner sc) {
        boolean sair = false;

        switch (op) {
            case 1 -> reproduzirConteudo(sc);
            case 2 -> salvarEstadoDoPrograma(sc);
            case 3 -> carregarEstadoDoPrograma(sc);
            case 9 -> MenuQueries.statisticsMenu(new Statistics(estado), sc);
            case 10 -> {
                utilizadorLogado = null;
                sair = true;
            }
            default -> System.out.println("Opção inválida.");
        }

        return sair;
    }

    public boolean userPremiumArea(int op, Scanner sc) {
        boolean sair = false;

        switch (op) {
            case 0 -> adicionarMusica(sc);
            case 1 -> reproduzirConteudo(sc);
            case 2 -> createPlaylist(sc);
            case 3 -> adicionarAlbumBiblioteca(sc);
            case 4 -> salvarEstadoDoPrograma(sc);
            case 5 -> carregarEstadoDoPrograma(sc);
            case 6 -> adicionarAlbum(sc);
            case 9 -> MenuQueries.statisticsMenu(new Statistics(estado), sc);
            case 10 -> {
                utilizadorLogado = null;
                sair = true;
            }
            default -> System.out.println("Opção inválida.");
        }

        return sair;
    }

    /// ///////////////////////////////////////////// Criar Música / Album / Playlist / User ////////////////////////////////////////////////

    private void adicionarMusica(Scanner sc) {
        if (estado.getAlbumController().getAllAlbums().isEmpty()) {
            System.out.println("Não existe nenhum álbum registado. Crie um álbum antes de adicionar músicas.");
            return;
        }

        MusicDTO novaMusica = MusicUI.menuAdicionarMusica(estado.getAlbumController(), sc);
        if (novaMusica == null) {
            return;
        }

        try {
            Music musicaCriada;
            try {
                musicaCriada = estado.getMusicController().addMusic(novaMusica);
            } catch (MusicException e) {
                System.out.println(e.getMessage());
                musicaCriada = null;
            }

            if (musicaCriada != null) {
                System.out.println("Música adicionada com sucesso!");

                boolean inserida = estado.getAlbumController().addMusicaAoAlbum(musicaCriada.getIdAlbum(), musicaCriada);
                if (!inserida) {
                    System.out.println("Música criada, mas ocorreu um erro ao associá-la ao álbum.");
                }

            } else {
                System.out.println("Ocorreu um erro ao adicionar a música.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void adicionarAlbum(Scanner sc) {
        AlbumDTO novoAlbum = AlbumUI.menuAdicionarAlbum(sc);
        try {
            estado.getAlbumController().addAlbum(novoAlbum);
        } catch (AlbumException e) {
            System.out.println(e.getMessage());
        }

        estado.getAlbumController().displayAllAlbums();
    }

    private void adicionarAlbumBiblioteca(Scanner sc) {
        Album album = AlbumUI.menuAddAlbumBiblioteca(estado.getAlbumController(), sc);

        try {
            this.estado.getUserController().addAlbumToUser(this.utilizadorLogado, album);
            System.out.println("\nÁlbum adicionado à sua biblioteca com sucesso!");
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }

    public void userRegister(Scanner sc) {
        UserDTO user = UserUI.UserRegistration(this.estado.getUserController(), sc);
        if (user == null) return;
        try {
            this.estado.getUserController().addUser(user);
            System.out.println("\nUtilizador criado com sucesso!");

        } catch (UserException e) {
            System.out.println(e.getMessage());
        }

    }


    public void createPlaylist(Scanner scanner) {
        if (this.utilizadorLogado instanceof UserPremium) {
            PlaylistDTO p = PlaylistUI.menuCriarPlaylist(utilizadorLogado, scanner);
            Genre g = null;
            int maxDuration = 0;
            boolean soExplicitas = false;

            switch (p.getPlaylistType()) {
                case 1 /*Genero*/ -> {
                    g = Menu.escolherGenero(scanner);
                    maxDuration = PlaylistUI.pedirDuracaoMaxima(scanner);
                }
                case 2 /*Normal*/ -> {
                    p.setMusicCollection(PlaylistUI.createListPlaylist(this.estado.getMusicController()));
                }
                case 3 /*Favoritos*/ -> {
                    maxDuration = PlaylistUI.pedirDuracaoMaxima(scanner);
                    soExplicitas = PlaylistUI.soExplicitas(scanner);
                }
            }

            try {
                estado.getUserController().addPlaylist(utilizadorLogado, p, estado.getPlaylistController(), estado.getMusicController(), g, maxDuration, soExplicitas);
                System.out.println("\nPlaylist '" + p.getTitle() + "' criada com sucesso!");

            } catch (PlaylistException e) {
                System.out.println(e.getMessage());
            }


        }
    }

    /// ///////////////////////////////////////////// Reprodução ////////////////////////////////////////////////


    public void reproduzirMusicas(List<Music> musicas, char tipo, String titulo, String artista, int anoLancamento, boolean premium) {
        if (musicas.isEmpty()) {
            System.out.println("Lista de músicas está vazia.");
            return;
        }

        Menu.apresentacaoReproducao(musicas, tipo, titulo, artista, anoLancamento, premium);

        final String[] comando = {""};
        final boolean[] comandoFinal = {true};
        Scanner sc = new Scanner(System.in);

        // Thread para escutar comandos do utilizador
        Thread inputThread = new Thread(() -> {
            while (comandoFinal[0] && !comando[0].equals("Q")) {
                String input = sc.nextLine().trim().toUpperCase();
                if (input.equals("Q") || input.equals("N") || (premium && input.equals("P"))) {
                    comando[0] = input;
                } else {
                    System.out.println("Comando Inválido.");
                }
            }
        });
        inputThread.start();

        int index = 0;
        while (index >= 0 && index < musicas.size()) {
            Music m = musicas.get(index);

            this.utilizadorLogado.adicionarReproducao(m.getId());
            this.utilizadorLogado.addPoints();
            this.estado.adicionarReproducao(m.getId(), this.utilizadorLogado.getUsername());

            System.out.println("\nMúsica " + (index + 1) + "/" + musicas.size());
            System.out.println(m.reproduzir());

            int duracao = m.getDuration() * 10;
            for (int t = 0; t < duracao; t++) {
                if (comando[0].equals("Q")) {
                    System.out.println("Reprodução interrompida.");
                    return;
                } else if (comando[0].equals("N")) {
                    comando[0] = "";
                    index++;
                    break;
                } else if (comando[0].equals("P") && premium) {
                    comando[0] = "";
                    index = (index > 0) ? index - 1 : 0;
                    break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Erro ao simular duração.");
                }

                if (t == duracao - 1) index++;
            }

        }

        comando[0] = "Q";
        comandoFinal[0] = false;
        System.out.println("Fim da reprodução.");
    }


    public void reproduzirConteudo(Scanner sc) {

        boolean isPremium = this.utilizadorLogado instanceof UserPremium;

        if (!isPremium) {
            List<Music> randomMusics = this.estado.getMusicController().randomMusicList();

            if (randomMusics == null || randomMusics.isEmpty()) {
                System.out.println("Não foi possível criar uma playlist aleatória.");
                return;
            }

            reproduzirMusicas(randomMusics, 'P', "Playlist Aleatória", "", 0, false);
            return;
        }

        // Utilizador Premium → escolhe conteúdo
        Object selecionado = Menu.playContentMenu(utilizadorLogado, estado, sc);

        if (selecionado == null) {
            System.out.println("A reprodução foi cancelada.");
            return;
        }

        List<Music> musicas;
        String titulo, artista = "";
        char tipo;
        int anoLancamento = 0;

        if (selecionado instanceof Playlist playlist) {
            musicas = playlist.getMusicCollection();
            titulo = playlist.getTitle();
            tipo = 'P';
        } else if (selecionado instanceof Album album) {
            musicas = album.getTracks();
            titulo = album.getName();
            artista = album.getArtist();
            tipo = 'A';
            anoLancamento = album.getYear();
        } else {
            System.out.println("Tipo de conteúdo não suportado.");
            return;
        }

        if (musicas.isEmpty()) {
            System.out.println("Não há músicas para reproduzir.");
            return;
        }

        int repType = Menu.reproductionType(sc);

        if (repType == 0) {
            Collections.shuffle(musicas);
        }

        reproduzirMusicas(musicas, tipo, titulo, artista, anoLancamento, true);


    }

    public void salvarEstadoDoPrograma(Scanner sc) {
        String ficheiro = Menu.salvarEstadoProgramaMenu(sc);
        if (ficheiro == null) {
            return;
        }
        salvarEstado(ficheiro);
    }

    public void carregarEstadoDoPrograma(Scanner sc) {
        String ficheiro = Menu.carregarEstadoProgramaMenu(sc);
        if (ficheiro == null) {
            return;
        }
        carregarEstado(ficheiro);
    }

}




