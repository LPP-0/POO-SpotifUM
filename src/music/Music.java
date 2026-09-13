package music;

import DTO.MusicDTO;
import user.User;
import utils.*;
import java.io.Serializable;
import java.util.*;

public class Music implements Serializable {
    private static int musicCounter = 1;
    private final String id;
    private List<String> melody;
    private Genre genre;
    private int streams = 0;
    private String title;
    private String singer;
    private String label;
    private String lyrics;
    private boolean explicit;
    private int duration;
    private String id_album;

    public Music() {
        this.id = "M" + musicCounter++;
        this.melody = new ArrayList<>();
        this.genre = Genre.POP;
        this.title = "";
        this.singer = "";
        this.label = "";
        this.lyrics = "";
        this.explicit = false;
        this.duration = 0;
        this.id_album = "";
    }
    public Music(MusicDTO dto) {
        this.id = "M" + musicCounter++;
        this.title = dto.getTitle();
        this.singer = dto.getSinger();
        this.genre = Genre.fromString(dto.getGenre()); // Conversão segura
        this.duration = dto.getDuration();
        this.label = dto.getLabel();
        this.lyrics = dto.getLyrics();
        this.melody = dto.getMelody();
        this.explicit = dto.isExplicit();
        this.id_album = dto.getAlbumId();
        this.streams = 0;
    }


    public Music(String title, String singer, String label, String lyrics, List<String> melody,
                 Genre genre, int duration, boolean explicit, String id_album) {
        this.id = "M" + musicCounter++;
        this.title = title;
        this.singer = singer;
        this.label = label;
        this.lyrics = lyrics;
        this.melody = new ArrayList<>(melody);
        this.genre = genre;
        this.duration = duration;
        this.explicit = explicit;
        this.id_album = id_album;
        this.streams = 0;
    }

    public Music(Music m) {
        this.id = m.getId();
        this.title = m.getTitle();
        this.singer = m.getSinger();
        this.label = m.getLabel();
        this.lyrics = m.getLyrics();
        this.melody = new ArrayList<>();
        this.melody.addAll(m.melody);
        this.genre = m.getGenre();
        this.duration = m.getDuration();
        this.explicit = m.explicit;
        this.id_album = m.getIdAlbum();
        this.streams = m.getStreams();
    }

    public String getId() {
        return id;
    }

    public List<String> getMelody() {
        return new ArrayList<>(melody);
    }

    public void setMelody(List<String> melody) {
        this.melody = new ArrayList<>(melody);
    }

    public Genre getGenre() {
        return genre;
    }

    public int getStreams() {
        return streams;
    }

    public void setStreams(int streams) {this.streams = streams;}

    public void incrementarStreams() {
        this.streams++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIdAlbum() {
        return id_album;
    }

    public void setIdAlbum(String id_album) {
        this.id_album = id_album;
    }

    @Override
    public Music clone() {
        return new Music(this);
    }

    public static void setMusicCounter(int value) {
        musicCounter = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🎵 Música [").append(id).append("]\n");
        sb.append("Título: ").append(title).append("\n");
        sb.append("Intérprete: ").append(singer).append("\n");
        sb.append("Editora: ").append(label).append("\n");
        sb.append("Género: ").append(genre).append("\n");
        sb.append("Reproduções: ").append(streams).append("\n");
        sb.append("Duração: ").append(duration).append("s\n");
        sb.append("Explícita: ").append(explicit ? "Sim" : "Não").append("\n");
        sb.append("Álbum: ").append(id_album).append("\n");
        sb.append("Letra: ").append(lyrics).append("\n");
        sb.append("Melodia: ").append(melody).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Music)) return false;
        Music music = (Music) o;
        return duration == music.duration &&
                explicit == music.explicit &&
                Objects.equals(title, music.title) &&
                Objects.equals(singer, music.singer) &&
                Objects.equals(label, music.label) &&
                Objects.equals(lyrics, music.lyrics) &&
                Objects.equals(melody, music.melody) &&
                Objects.equals(genre, music.genre) &&
                Objects.equals(id_album, music.id_album);
    }



    public String reproduzir() {
        StringBuilder sb = new StringBuilder();

        sb.append("Reproduzindo: ").append(this.title).append(" por ").append(this.singer).append("\n");
        sb.append("Duração : ").append(this.duration).append(" s\n");
        if (explicit) {
            sb.append("Aviso: Conteúdo explícito!");
        }

        sb.append("\nLetra da música: ");
        for (String linha : lyrics.split(",")) {
            sb.append(linha).append("\n");
        }

        sb.append("\n");

        return sb.toString();

    }

}

