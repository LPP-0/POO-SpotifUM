package estado;

import controller.*;
import reproducao.Reproducao;
import java.io.Serializable;
import java.time.*;
import java.util.*;

public class Estado implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserController userController;
    private MusicController musicController;
    private PlaylistController playlistController;
    private AlbumController albumController;

    private List<Reproducao> historicoGlobal;

    public Estado(UserController userController,
                  MusicController musicController,
                  PlaylistController playlistController,
                  AlbumController albumController) {
        this.userController = userController;
        this.musicController = musicController;
        this.playlistController = playlistController;
        this.albumController = albumController;
        this.historicoGlobal = new ArrayList<>();
    }

    public UserController getUserController() { return userController; }
    public MusicController getMusicController() {
        return this.musicController; // usando um construtor de cópia
    }
    public PlaylistController getPlaylistController() { return this.playlistController; }
    public AlbumController getAlbumController() { return this.albumController; }


    public List<Reproducao> getHistoricoGlobal() {
        return historicoGlobal;
    }

    public void adicionarReproducaoGlobal(Reproducao r) {
        this.historicoGlobal.add(r);
    }

    public void adicionarReproducao(String musicID, String userID) {
        Reproducao r = new Reproducao(userID, musicID, LocalDate.now());
        this.historicoGlobal.add(r);
        this.musicController.addStream(musicID);
    }
}
