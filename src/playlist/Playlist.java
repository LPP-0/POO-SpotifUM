package playlist;

import music.Music;

import java.io.Serializable;
import DTO.PlaylistDTO;
import utils.Utils;

import java.util.*;

public abstract class Playlist implements Serializable {
    private static int playlistCounter = 1;
    private final String id;
    private String title;
    private  List<Music> musicCollection;
    private boolean isRandom;
    private boolean isPrivate;

    public Playlist(String title, List<Music> musicCollection, boolean isPrivate,boolean isRandom) {
        this.id = "P" + playlistCounter++;
        this.title = title;
        setMusicCollection(musicCollection);
        this.isRandom = isRandom;
        this.isPrivate = isPrivate;
    }



    public Playlist(Playlist p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.musicCollection = p.getMusicCollection();
        this.isPrivate = p.getPrivate();
        this.isRandom = p.getRandom();
    }


    public Playlist() {
        this.id = "P" + playlistCounter++;  // ID gerado automaticamente
        this.musicCollection = new ArrayList<>();
        this.title = "";
        this.isPrivate = true;
        this.isRandom = false;
    }

    public Playlist(PlaylistDTO p ){
        this.id = "P" + playlistCounter++;
        this.title = p.getTitle();
        this.musicCollection = p.getMusicCollection();
        this.isPrivate = p.isPrivate();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Music> getMusicCollection() {
        List<Music> cloneCollection = new ArrayList<>();
        for (Music music : this.musicCollection) {
            cloneCollection.add(music.clone());
        }
        return cloneCollection;
    }


    public void setMusicCollection(List<Music> musicCollection) {
        this.musicCollection= new ArrayList<>();
        for (Music music : musicCollection) {
            this.musicCollection.add(music.clone());
        }
    }



    public String getId() {
        return id;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean getRandom() {
        return isRandom;
    }

    public void setRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }


    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Playlist p = (Playlist) obj;
        return id.equals(p.getId()) && title.equals(p.getTitle()) &&
                this.musicCollection.equals(p.getMusicCollection()) &&
                isRandom == p.getRandom() &&
                isPrivate == p.getPrivate();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Playlist{\n");
        str.append("ID: " + id + "\n");
        str.append("Title: " + title + "\n");
        str.append("MusicCollection: \n");
        for (Music music : musicCollection) {
            str.append("Music -> ID: ").append(music.getId());
            str.append(", Title: ").append(music.getTitle());
            str.append(", Genre: ").append(music.getGenre());
            str.append(", Singer: ").append(music.getSinger());
            str.append(", Is explicit: ").append(music.isExplicit());
            str.append(", duration: ").append(music.getDuration()).append("\n");
        }
        str.append("Random: " + isRandom + "\n");
        str.append("Private: " + isPrivate + "\n");
        str.append("}\n");

        return str.toString();
    }




    public abstract Playlist clone();
    public  void addMusic(Music music){
        this.musicCollection.add(music);
    };
    public  void removeMusic(Music music){
        this.musicCollection.remove(music);
    };



    protected void shufflePlaylist() {
        Collections.shuffle(this.musicCollection);
    }



    public boolean isPrivate() {
        return this.isPrivate;
    }

    public static void setPlaylistCounter(int value) {
        playlistCounter = value;
    }
}


