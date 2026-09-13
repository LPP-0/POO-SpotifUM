package utils;

import music.*;
import playlist.*;
import user.*;
import album.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;

public class Utils {

    public static String formatarDuracao(int segundosT){
        int horas = segundosT / 3600;
        int minutos = (segundosT % 3600) / 60;
        int segundos = segundosT % 60;

        StringBuilder sb = new StringBuilder();
        if (horas > 0) sb.append(horas).append(" h ");
        if (minutos > 0) sb.append(minutos).append(" min ");
        if (segundos > 0 || sb.length() == 0) sb.append(segundos).append(" seg");

        return sb.toString().trim();
    }

    public static void atualizarMusicCounter(Collection<Music> musicas) {
        int max = 0;
        for (Music m : musicas) {
            String id = m.getId(); // Ex: "M23"
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > max) max = num;
            } catch (NumberFormatException ignored) {}
        }
        Music.setMusicCounter(max + 1); // Atualiza o contador
    }


    public static void atualizarPlaylistCounter(Collection<Playlist> playlists) {
        int max = 0;
        for (Playlist m : playlists) {
            String id = m.getId(); // Ex: "M23"
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > max) max = num;
            } catch (NumberFormatException ignored) {}
        }
        Playlist.setPlaylistCounter(max + 1); // Atualiza o contador
    }

    public static void atualizarAlbumCounter(Collection<Album> albuns) {
        int max = 0;
        for (Album a : albuns) {
            String id = a.getId();
            if (id != null && id.startsWith("A") && id.length() > 1) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        Album.setAlbumCounter(max + 1);
    }
}


