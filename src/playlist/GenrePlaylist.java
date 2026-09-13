package playlist;

import java.io.Serializable;
import java.util.stream.Collectors;

import DTO.PlaylistDTO;
import music.Music;
import utils.Genre;

import javax.naming.ldap.ControlFactory;
import java.util.*;

public class GenrePlaylist extends Playlist implements Serializable {

    private Genre genre;
    private int maxDuration;


    public GenrePlaylist() {
        super();
        this.genre = Genre.POP;
        this.maxDuration = 0;
        super.setRandom(false);
    }

    public GenrePlaylist(PlaylistDTO p, int maxDuration, Genre genre) {
        super(p);
        this.maxDuration = maxDuration;
        this.genre = genre;
    }

    public GenrePlaylist(GenrePlaylist gp) {
        super(gp);
        this.genre = gp.getGenre();
        this.maxDuration = gp.getMaxDuration();
        super.setRandom(false);
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GenrePlaylist p = (GenrePlaylist) o;
        return maxDuration == p.maxDuration && Objects.equals(genre, p.genre);
    }

    @Override
    public GenrePlaylist clone() {
        return new GenrePlaylist(this);
    }

    public int totalDuration() {
        int total = 0;
        for (Music m : super.getMusicCollection()) {
            total += m.getDuration();
        }
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + "\nGenre: " + genre + "\nMax duration: " + maxDuration;
    }





}

