package playlist;

import DTO.PlaylistDTO;
import music.Music;
import controller.MusicController;

import java.io.Serializable;
import java.util.*;

public class RandomPlaylist extends Playlist implements Serializable {

    public RandomPlaylist(String title, List<Music> musicCollection, boolean isPrivate) {
        super(title, musicCollection, isPrivate, true);
    }

    public RandomPlaylist() {
        super();
    }

    public RandomPlaylist(RandomPlaylist rp) {
        super(rp);
    }

    public RandomPlaylist(PlaylistDTO playlistDTO) {
        super(playlistDTO);
    }

    @Override
    public RandomPlaylist clone() {
        return new RandomPlaylist(this);
    }





}