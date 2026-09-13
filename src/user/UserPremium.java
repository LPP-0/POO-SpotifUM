package user;

import DTO.PlaylistDTO;
import DTO.UserDTO;
import playlist.Playlist;
import album.Album;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public abstract class UserPremium extends User implements Serializable {

    /** Lista de Playlists do User. */
    private List<Playlist> playlists;

    /** Lista de Albuns do user. */
    private List<Album> albuns;


    /** Construtores */

    public UserPremium(UserDTO u) {
        super(u);
        this.playlists = new ArrayList<>();
        this.albuns = new ArrayList<>();
    }

    public UserPremium(UserPremium user) {
        super(user);
        this.playlists = new ArrayList<>();
        for (Playlist p : user.playlists) {
            this.playlists.add(p.clone());
        }

        this.albuns = new ArrayList<>();
        for (Album a : user.albuns) {
            this.albuns.add(a.clone());
        }

        this.setPoints(user.getPoints());
    }

    public UserPremium() {
        super();
        this.playlists = new ArrayList<>();
        this.albuns = new ArrayList<>();
    }

    public UserPremium(String username, String name, String email, String address, String password, LocalDate birthday, double points, List<Playlist> playlists, List<Album> albuns) {
        super(username, name, email, address, password, birthday, points);
        this.playlists = playlists;
        this.albuns = albuns;
    }

    /** Getters / Setters */

    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (Playlist p : this.playlists) {
            playlists.add(p.clone());
        }
        return playlists;
    }

    public List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for (Album a : this.albuns) {
            albums.add(a.clone());
        }

        return albums;
    }

    public List<Album> getAlbuns() {
        return albuns;
    }

    public void addAlbum(Album album) {
        if (!albuns.contains(album)) {
            albuns.add(album);
        }
    }


    public void addPlaylist(Playlist p) {
        playlists.add(p);
    }

    public boolean hasAlbum(Album album) {
        return this.albuns.contains(album);
    }

    public abstract void addPoints(); // implementar em PremiumBase e PremiumTop
    /** Clone / equals / hashcode */
    @Override
    public abstract UserPremium clone();


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserPremium that = (UserPremium) o;
        return Objects.equals(playlists, that.playlists) && Objects.equals(albuns, that.albuns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlists, albuns);
    }
}