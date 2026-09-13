package reproducao;

import java.io.Serializable;
import java.time.*;
import java.util.Objects;

public class Reproducao implements Serializable {
    private String userId;
    private String musicId;
    private LocalDate data;

    public Reproducao(String userId, String musicId, LocalDate dataHora) {
        this.userId = userId;
        this.musicId = musicId;
        this.data = dataHora;
    }

    public Reproducao() {
        this.data = LocalDate.now();
        this.musicId = "";
        this.userId = "";
    }

    public Reproducao(Reproducao reproducao) {
        this.userId = reproducao.userId;
        this.musicId = reproducao.musicId;
        this.data = reproducao.data;
    }


    public String getUserId() {
        return userId;
    }

    public String getMusicId() {
        return musicId;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "[" + userId + "] ouviu [" + musicId + "] em " + data.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reproducao that = (Reproducao) o;
        return Objects.equals(userId, that.userId) && Objects.equals(musicId, that.musicId) && Objects.equals(data, that.data);
    }

    public Reproducao clone() {
        return new Reproducao(userId, musicId, data);
    }
}

