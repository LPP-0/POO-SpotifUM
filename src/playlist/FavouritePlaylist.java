package playlist;

import java.io.Serializable;

import DTO.PlaylistDTO;
import music.Music;
import user.*;
import java.util.*;

public class FavouritePlaylist extends Playlist implements Serializable {
    private boolean soExplicitas;
    private int maxDuration;

    public boolean isSoExplicitas() {
        return soExplicitas;
    }

    public void setSoExplicitas(boolean soExplicitas) {
        this.soExplicitas = soExplicitas;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public FavouritePlaylist(FavouritePlaylist fp) {
        super(fp);
        this.soExplicitas = fp.soExplicitas;
        this.maxDuration = fp.maxDuration;
    }
    public FavouritePlaylist() {
        super();
        this.soExplicitas = false;
        this.maxDuration = 0;
    }

    public FavouritePlaylist(PlaylistDTO playlistDTO, boolean soExplicitas, int maxDuration) {

        super(playlistDTO);
        this.soExplicitas = soExplicitas;
        this.maxDuration = maxDuration;
    }

    @Override
    public FavouritePlaylist clone() {
        return new FavouritePlaylist(this);
    }

}
