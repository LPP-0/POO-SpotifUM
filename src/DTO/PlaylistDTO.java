package DTO;

import music.Music;

import java.util.*;

public class PlaylistDTO{
    private String id;
    private String title;
    private List<Music> musicCollection;
    private boolean isRandom;
    private boolean isPrivate;
    private int playlistType;

    public PlaylistDTO(String id, String title, List<Music> musicCollection,
                       boolean isRandom, boolean isPrivate, int playlistType) {
        this.title = title;
        this.musicCollection = musicCollection;
        this.isRandom = isRandom;
        this.isPrivate = isPrivate;
        this.playlistType = playlistType;
    }

    public PlaylistDTO() {
        this.title = "";
        this.musicCollection = new ArrayList<>();
        this.isRandom = true;
        this.isPrivate = true;
        this.playlistType = 4;
    }


    public String getTitle() {
        return title;
    }

    public List<Music> getMusicCollection() {
        return musicCollection;
    }

    public void setMusicCollection(List<Music> musicCollection) {
        this.musicCollection = musicCollection;
    }

    public int getPlaylistType() {
        return playlistType;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public boolean isPrivate() {
        return isPrivate;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRandom(boolean random) {
        isRandom = random;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }


    public void setPlaylistType(int playlistType) {
        this.playlistType = playlistType;
    }

    @Override
    public String toString() {
        return "PlaylistDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", músicas=" + musicCollection.size() +
                ", aleatória=" + isRandom +
                ", privada=" + isPrivate +
                '}';
    }
}
