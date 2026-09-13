package DTO;

import java.util.ArrayList;
import java.util.List;

public class MusicDTO {
    private String title;
    private String singer;
    private String genre;
    private int duration;
    private String label;
    private String lyrics;
    private List<String> melody;
    private boolean explicit;
    private String albumId;
    private boolean isMultimedia;
    private String videoUrl; // só preenchido se for multimédia

    public MusicDTO(String title, String singer, String genre, int duration,
                    boolean explicit, String albumId, boolean isMultimedia, String videoUrl, String lyrics, List<String> melody,String label) {
        this.title = title;
        this.singer = singer;
        this.genre = genre;
        this.label = label;
        this.duration = duration;
        this.explicit = explicit;
        this.albumId = albumId;
        this.isMultimedia = isMultimedia;
        this.videoUrl = videoUrl;
        this.lyrics = lyrics;
        this.melody = melody;
    }

    public MusicDTO(){
        this.title = "";
        this.singer = "";
        this.genre = "";
        this.duration = 0;
        this.explicit = false;
        this.albumId = "";
        this.isMultimedia = false;
        this.videoUrl = "";
        this.lyrics = "";
        this.melody = new ArrayList<>();
    }

    // Getters apenas (imutável)
    public String getTitle() { return title; }
    public String getSinger() { return singer; }
    public String getLyrics() { return lyrics; }
    public String getGenre() { return genre; }
    public List<String> getMelody() { return melody; }
    public int getDuration() { return duration; }
    public boolean isExplicit() { return explicit; }
    public String getAlbumId() { return albumId; }
    public boolean isMultimedia() { return isMultimedia; }
    public String getVideoUrl() { return videoUrl; }
    public String getLabel() { return label; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setMelody(List<String> melody) {
        this.melody = melody;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setMultimedia(boolean multimedia) {
        isMultimedia = multimedia;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "MusicDTO{" +
                "title='" + title + '\'' +
                ", singer='" + singer + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", explicit=" + explicit +
                ", albumId='" + albumId + '\'' +
                ", isMultimedia=" + isMultimedia +
                (isMultimedia ? ", videoUrl='" + videoUrl + '\'' : "") +
                '}';
    }
}

