package Interactive;

import DTO.*;
import controller.*;
import music.Music;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reproducao.Reproducao;
import user.User;
import utils.Genre;
import utils.Utils;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InteractiveTest {
    private UserDTO user1;
    private UserDTO user2;
    private AlbumDTO album1;
    private AlbumDTO album2;
    private MusicDTO music1;
    private MusicDTO music2;
    private PlaylistDTO playlist1;
    private PlaylistDTO playlist2;

    private MusicController musicController;
    private UserController userController;
    private AlbumController albumController;
    private PlaylistController playlistController;

    @BeforeEach
    public void setUp() throws UserException, MusicException {
          musicController = new MusicController();
          userController = new UserController();
          albumController = new AlbumController();
          playlistController = new PlaylistController();

        // USERS
        user1 = new UserDTO(
                "joaosilva",
                "João Silva",
                "joao@email.com",
                "Rua A",
                "1234",
                LocalDate.of(2000, 1, 15),
                2
        );

        user2 = new UserDTO(
                "anaoliveira",
                "Ana Oliveira",
                "ana@email.com",
                "Rua B",
                "abcd",
                LocalDate.of(1998, 5, 30),
                0
        );

        // ALBUNS
        album1 = new AlbumDTO("Dreams", "Coldplay", 2020);
        album2 = new AlbumDTO("Future Nostalgia", "Dua Lipa", 2021);

        // MUSICAS PRINCIPAIS
        music1 = new MusicDTO();
        music1.setTitle("Sky Full of Stars");
        music1.setSinger("Coldplay");
        music1.setGenre(Genre.POP.name());
        music1.setDuration(210);
        music1.setLabel("Parlophone");
        music1.setLyrics("Cause you're a sky, full of stars...");
        music1.setMelody(List.of("C", "G", "Am", "F"));
        music1.setExplicit(false);
        music1.setAlbumId("A1");
        music1.setMultimedia(false);

        music2 = new MusicDTO();
        music2.setTitle("Levitating");
        music2.setSinger("Dua Lipa");
        music2.setGenre(Genre.POP.name());
        music2.setDuration(205);
        music2.setLabel("Warner Records");
        music2.setLyrics("If you wanna run away with me...");
        music2.setMelody(List.of("Em", "Bm", "C", "G"));
        music2.setExplicit(false);
        music2.setAlbumId("A2");
        music2.setMultimedia(true);
        music2.setVideoUrl("https://youtube.com/levitating ");

        // Adicionando os utilizadores e álbuns
        userController.addUser(user1);
        userController.addUser(user2);

        albumController.addAlbum(album1);
        albumController.addAlbum(album2);

        musicController.addMusic(music1);
        musicController.addMusic(music2);

        // === ADICIONAR MAIS 15 MÚSICAS ===

        List<MusicDTO> novasMusicas = new ArrayList<>();

        // 🎶 Funk (5 músicas)
        novasMusicas.add(createMusicDTO("Groove Thing", "James Brown Jr.", Genre.FUNK.name(), 10, true, false, null));
        novasMusicas.add(createMusicDTO("Funky Town", "The Funky Cats", Genre.FUNK.name(), 10, false, false, null));
        novasMusicas.add(createMusicDTO("Soul Train", "Marvin Groove", Genre.FUNK.name(), 10, true, false, null));
        novasMusicas.add(createMusicDTO("Disco Fever", "Party People", Genre.FUNK.name(), 10, false, false, null));
        novasMusicas.add(createMusicDTO("Boogie Nights", "Funkadelic", Genre.FUNK.name(), 10, true, false, null));

        // 🎵 Pop (5 músicas)
        novasMusicas.add(createMusicDTO("Sunshine Day", "Pop Princess", Genre.POP.name(), 10, false, true, "https://youtube.com/sunshine "));
        novasMusicas.add(createMusicDTO("Dancing Queen", "Pop Starlet", Genre.POP.name(), 10, false, true, "https://youtube.com/dancingqueen "));
        novasMusicas.add(createMusicDTO("Bubblegum Dreams", "Candy Pop", Genre.POP.name(), 10, false, false, null));
        novasMusicas.add(createMusicDTO("Love Me Again", "Romantic Pop", Genre.POP.name(), 10, false, true, "https://youtube.com/lovemeagain "));
        novasMusicas.add(createMusicDTO("Shiny World", "Glitter Band", Genre.POP.name(), 10, false, false, null));

        // 🤘 Rock (5 músicas)
        novasMusicas.add(createMusicDTO("Electric Guitar", "Rock Legends", Genre.ROCK.name(), 10, true, false, null));
        novasMusicas.add(createMusicDTO("Smoke on the Water", "Classic Rockers", Genre.ROCK.name(), 10, false, false, null));
        novasMusicas.add(createMusicDTO("Highway to Hell", "Wild Riders", Genre.ROCK.name(), 10, true, false, null));
        novasMusicas.add(createMusicDTO("Born to Run", "Street Kings", Genre.ROCK.name(), 10, false, false, null));
        novasMusicas.add(createMusicDTO("Thunderstruck", "Hardcore Rock", Genre.ROCK.name(), 10, true, false, null));


        // Adicionando todas as novas músicas
        for (MusicDTO musica : novasMusicas) {
            musicController.addMusic(musica);
        }

        // PLAYLIST
        playlist1 = new PlaylistDTO();
        playlist1.setId("playlist1");
        playlist1.setTitle("Pop Vibes");
        playlist1.setMusicCollection(new ArrayList<>());
        playlist1.setRandom(false);
        playlist1.setPrivate(false);
        playlist1.setPlaylistType(1);

        playlist2 = new PlaylistDTO();
        playlist2.setId("");
        playlist2.setTitle("FavPlaylist");
        playlist2.setMusicCollection(new ArrayList<>());
        playlist2.setRandom(false);
        playlist2.setPrivate(false);
        playlist2.setPlaylistType(3);
    }



    private MusicDTO createMusicDTO(String title, String singer, String genre, int duration,
                                    boolean isExplicit, boolean hasMultimedia, String videoUrl) {
        MusicDTO music = new MusicDTO();
        music.setTitle(title);
        music.setSinger(singer);
        music.setGenre(genre);
        music.setDuration(duration);
        music.setLabel("Universal Music");
        music.setLyrics("Dummy lyrics for testing purposes.");
        music.setMelody(List.of("E", "A", "D")); // Acordes simples
        music.setExplicit(isExplicit);
        music.setAlbumId("album1"); // Pode ajustar conforme necessário
        music.setMultimedia(hasMultimedia);
        if (hasMultimedia) {
            music.setVideoUrl(videoUrl);
        }
        return music;
    }

    @Test
    public void testAddUser_sucesso() {

        UserDTO userDTO = new UserDTO(
                "GustavoBraga", "GustavoBraga", "joao@email.com", "Rua A",
                "1234", LocalDate.of(2000, 1, 15), 0
        );

        assertDoesNotThrow(() -> userController.addUser(userDTO));
    }

    @Test
    public void testAddUser_duplicado() {

        UserDTO userDTO = new UserDTO(
                "joaosilva", "joaõ", "joao@email.com", "Rua A",
                "1234", LocalDate.of(2000, 1, 15), 0
        );


        UserException e = assertThrows(UserException.class, () -> {
            userController.addUser(userDTO); // duplicado
        });

        assertEquals("Utilizador já existe com o username: joaosilva", e.getMessage());
    }

    @Test
    public void testAddUser_invalido() {

        UserDTO userDTO = new UserDTO(
                null, "Sem username", "x@email.com", "Rua", "123", LocalDate.of(2001, 5, 20), 0
        );

        UserException e = assertThrows(UserException.class, () -> userController.addUser(userDTO));

        assertEquals("Utilizador inválido (username nulo).", e.getMessage());

    }
    @Test
    public void testGenrePlaylist_time100() {
        assertDoesNotThrow(() -> {
            userController.addPlaylist(userController.getUser("joaosilva"),playlist1,playlistController,musicController,Genre.POP,100,false);

        });

        userController.getUser("joaosilva").getPlaylists().forEach(playlist -> {
            System.out.println(playlist.toString());
        });
    }

    @Test
    public void printMusics() {
        musicController.getAllMusic().forEach(System.out::println);
    }

    @Test void listarPOPMusic() {
        List<Music> genreTracks = musicController.getAllMusic().stream()
                .filter(m -> m.getGenre().equals(Genre.POP))
                .toList();

        System.out.println(genreTracks);
    }



    @Test
    public void addMusic_duplicado() {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO = new MusicDTO();
        musicDTO.setTitle("Sky Full of Stars");
        musicDTO.setSinger("Coldplay");
        musicDTO.setGenre(Genre.POP.name());
        musicDTO.setDuration(210);
        musicDTO.setLabel("Parlophone");
        musicDTO.setLyrics("Cause you're a sky, full of stars...");
        musicDTO.setMelody(List.of("C", "G", "Am", "F"));
        musicDTO.setExplicit(false);
        musicDTO.setAlbumId("album1");
        musicDTO.setMultimedia(false);

        MusicDTO finalMusicDTO = musicDTO;
        MusicException e = assertThrows(MusicException.class, () -> {
            musicController.addMusic(finalMusicDTO); // duplicado
        });

        assertEquals("Música já existente", e.getMessage());
    }




    @Test
    public void testFavPlaylist_insucesso() {
        User user = userController.getUser("joaosilva");



        PlaylistException e = assertThrows(PlaylistException.class, () ->{
            userController.addPlaylist(user,playlist2,playlistController,musicController,Genre.POP,100,false);
        });
        userController.getUser("joaosilva").getPlaylists().forEach(playlist -> {
            System.out.println(playlist.toString());
        });

        assertEquals("Impossivel criar playlist de favorito: Historico vazio", e.getMessage());
    }
    @Test
    public void testFavPlaylist_sucesso() {
        User user = userController.getUser("joaosilva");
        user.adicionarReproducao("M3");
        user.adicionarReproducao("M1");
        user.adicionarReproducao("M15");


        assertDoesNotThrow(() -> {
            userController.addPlaylist(user,playlist2,playlistController,musicController,Genre.POP,100,false);
        });
        userController.getUser("joaosilva").getPlaylists().forEach(playlist -> {
            System.out.println(playlist.toString());
        });

    }
    @Test
    public void testFavPlaylist_soExplicitas_sucesso() {
        User user = userController.getUser("joaosilva");
        user.adicionarReproducao("M3");
        user.adicionarReproducao("M1");
        user.adicionarReproducao("M15");


        assertDoesNotThrow(() -> {
            userController.addPlaylist(user,playlist2,playlistController,musicController,Genre.POP,100,true);
        });
        userController.getUser("joaosilva").getPlaylists().forEach(playlist -> {
            System.out.println(playlist.toString());
        });

    }







}