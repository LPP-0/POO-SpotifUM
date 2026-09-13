package music;

import DTO.MusicDTO;
import utils.Genre;

import java.io.Serializable;
import java.util.*;



public class MusicMultimedia extends Music implements Multimedia, Serializable {
    private String videoUrl;

    public MusicMultimedia(){
        super();
        this.videoUrl = "";
    }

    public MusicMultimedia(MusicMultimedia m){
        super(m);
        this.videoUrl = m.getUrlVideo();
    }

    public MusicMultimedia(MusicDTO music){
        super(music);
        this.videoUrl = (music.getVideoUrl() != null) ? music.getVideoUrl() : "";

    }

    public MusicMultimedia(String title, String singer, String label, String lyrics, List<String> melody,
                           Genre genre, int duration, boolean explicit, String id_album, String videoUrl) {
        super(title, singer, label, lyrics, melody, genre, duration, explicit, id_album);
        this.videoUrl = videoUrl;
    }



    @Override
    public String reproduzir() {
        return super.reproduzir() + "A reproduzir vídeo: " + this.getUrlVideo();

    }

    @Override
    public String getUrlVideo() {
        return videoUrl;
    }



    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());  // Chama o toString da classe Music
        sb.append("Vídeo: ").append(videoUrl).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicMultimedia that)) return false;
        if (!super.equals(o)) return false;
        return videoUrl.equals(that.videoUrl);
    }

    @Override
    public MusicMultimedia clone() {
        return new MusicMultimedia(this);
    }
}

