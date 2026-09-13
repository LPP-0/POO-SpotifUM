package queries;

import estado.Estado;
import music.Music;
import playlist.Playlist;
import user.*;
import reproducao.Reproducao;

import java.time.*;

import java.util.*;

public class Statistics {
    private Estado estado;


    public Statistics(Estado estado) {
        this.estado = estado;

    }


    // QUERY 1
    public Music getMostPlayedSong(){
        return estado.getMusicController().getAllMusic().stream()
                .max(Comparator.comparingInt(Music::getStreams)).orElse(null);
    }

    //QUERY 2
    public String getMostPopularArtist(){
        Map<String, Integer> artistPlays = new HashMap<>();
        for(Music m : estado.getMusicController().getAllMusic()){
            String singer = m.getSinger();
            if (artistPlays.containsKey(singer)) {
                artistPlays.put(singer, artistPlays.get(singer) + m.getStreams());
            } else {
                artistPlays.put(singer, m.getStreams());
            }
        }

        if (artistPlays.isEmpty()) {
            throw new NoSuchElementException("Não há músicas para determinar o artista mais popular.");
        }

        return Collections.max(artistPlays.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // QUERY 3
    public User getTopUserByPlays() {
        return getTopUserByPlays(null, null);
    }

    public User getTopUserByPlays(LocalDate startDate, LocalDate endDate) {
        // Verifica se existem usuários
        if (estado.getUserController().getUsers().isEmpty()) {
            return null;
        }

        Map<User, Integer> userPlayCounts = new HashMap<>();

        for (User u : estado.getUserController().getUsers()) {
            int plays = countUserPlays(u, startDate, endDate);
            if (plays > 0) { // Só adiciona usuários com plays
                userPlayCounts.put(u, plays);
            }
        }

        // Trata caso onde nenhum usuário tem plays no período
        if (userPlayCounts.isEmpty()) {
            return null;
        }

        return Collections.max(userPlayCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private int countUserPlays(User user, LocalDate startDate, LocalDate endDate) {
        int count = 0;
        for (Reproducao r : user.getHistorico()) {
            if (isWithinPeriod(r.getData(), startDate, endDate)) {
                count++;
            }
        }
        return count;
    }

    private boolean isWithinPeriod(LocalDate date, LocalDate start, LocalDate end) {
        if (start == null || end == null) return true; // Sem filtro de período
        return !date.isBefore(start) && !date.isAfter(end);
    }

    //QUERY 4
    public User getUserWithMostPoints(){
        return estado.getUserController().getUsers().stream()
                .max(Comparator.comparingDouble(User::getPoints)).orElse(null);
    }

    //QUERY 5
    public String getMostPlayedGenre() {
        Map<String, Integer> genrePlays = new HashMap<>();

        for (Music m : estado.getMusicController().getAllMusic()) {
            String genre = m.getGenre().name();
            if (genrePlays.containsKey(genre)) {
                genrePlays.put(genre, genrePlays.get(genre) + m.getStreams());
            } else {
                genrePlays.put(genre, m.getStreams());
            }
        }

        if (genrePlays.isEmpty()) {
            throw new NoSuchElementException("Não há músicas para determinar o gênero mais tocado.");
        }

        Map.Entry<String, Integer> maxEntry = Collections.max(genrePlays.entrySet(), Map.Entry.comparingByValue());
        return maxEntry.getKey();
    }

    //QUERY  6
    public int getNumPublicPlaylists() {
        int count = 0;
        for (Playlist p : estado.getPlaylistController().getAllPlaylists()) {
            if (!p.getPrivate()) {
                count++;
            }
        }
        return count;
    }

    //QUERY 7
    public User getUserWithMostPlaylists() {
        Map<User,Integer> numPlaylists = new HashMap<>();
        for(User u : estado.getUserController().getUsers()) {
            if(u instanceof UserPremium){
                numPlaylists.put(u, ((UserPremium) u).getPlaylists().size());
            }
        }
        if (numPlaylists.isEmpty()) {
            return null;
        }

        return Collections.max(numPlaylists.entrySet(), Map.Entry.comparingByValue()).getKey();
    }



}

