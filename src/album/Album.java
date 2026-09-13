package album;

import music.Music;
import DTO.*;
import java.io.Serializable;
import java.util.*;

public class Album implements Serializable {
    private static int albumCounter = 1;
    private final String id;
    private String name;
    private String artist;
    private int year;
    private List<Music> tracks;

    public Album(String name, String artist, int year) {
        this.id = "A" + albumCounter++;
        this.name = name;
        this.artist = artist;
        this.year = year;
        this.tracks = new ArrayList<>();
    }

    public Album(){
        this.id = "A" + albumCounter++;
        this.name = "";
        this.artist = "";
        this.year = 0;
        this.tracks = new ArrayList<>();
    }

    public Album(Album album){
        this.id = album.getId();
        this.name = album.name;
        this.artist = album.artist;
        this.year = album.year;
        this.tracks = new ArrayList<>(album.tracks);
    }

    public Album(AlbumDTO dto) {
        this.id = "A" + albumCounter++;
        this.name = dto.getName();
        this.artist = dto.getArtist();
        this.year = dto.getYear();
        this.tracks = new ArrayList<>(); // Os albuns serão inicializados vazios e vamos adicionando as músicas
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public List<Music> getTracks() {

        List<Music> cloneCollection = new ArrayList<>();
        for (Music music : this.tracks) {
            cloneCollection.add(music.clone());
        }
        return cloneCollection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTracks(List<Music> tracks) {
        this.tracks = new ArrayList<>(tracks);
    }


    public void addTrack(Music music) {
        if (music != null && !tracks.contains(music)) {
            this.tracks.add(music);
        }
    }

    public void removeTrack(Music music) {
        this.tracks.remove(music);
    }

    @Override
    public Album clone(){
        return new Album(this);
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;

        Album a = (Album) o;
        return  a.getId().equals(this.id)         &&
                a.getName().equals(this.name)     &&
                a.getArtist().equals(this.artist) &&
                a.year == this.year               &&
                a.getTracks().equals(this.tracks) ;
    }


    @Override
    public String toString() {
        return "Album{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", artist='" + getArtist() + '\'' +
                ", year=" + getYear() + '\'' +
                ", tracks=" + getTracks() +
                '}';
    }

    public static void setAlbumCounter(int value){
        albumCounter = value;
    }

}