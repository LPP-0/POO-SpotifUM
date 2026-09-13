package controller;
import DTO.PlaylistDTO;
import Interactive.PlaylistUI;
import music.Music;
import playlist.*;
import reproducao.Reproducao;
import user.*;
import playlist.RandomPlaylist;
import utils.Genre;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class PlaylistController implements Serializable {
    private Map<String, Playlist> playlists; // Key = UUID, Value = Playlist

    public PlaylistController() {
        this.playlists = new HashMap<>();
    }
    public PlaylistController(PlaylistController playlistController) {
        this.playlists = new HashMap<>();
    }

    public void addPlaylist(Playlist playlist) {
        playlists.put(playlist.getId(), playlist);
    }

    public Playlist getPlaylistById(String id) {
        Playlist playlist = this.playlists.get(id.trim());
        if (playlist == null) {return null;}
        else {return playlist.clone();}
    }

    public Collection<Playlist> getAllPlaylists() {
        List<Playlist> playlistCopies = new ArrayList<>();
        for (Playlist playlist : playlists.values()) {
            playlistCopies.add(playlist.clone());
        }

        return playlistCopies;
    }

    public List<Playlist> getPlaylistsPublicas() {
        return playlists.values().stream()
                .filter(p -> !p.isPrivate())
                .collect(Collectors.toList());
    }


    public List<Playlist> getPlaylistsByName(String name) {
        List<Playlist> result = new ArrayList<>();
        for (Playlist p : playlists.values()) {
            if (p.getTitle().equalsIgnoreCase(name)) {
                result.add(p);
            }
        }
        return result;
    }

    public Playlist criarPlaylistNormal(PlaylistDTO playlistDTO) {
        PremiumPlaylist play = new PremiumPlaylist(playlistDTO);
        addPlaylist(play);

        return new PremiumPlaylist(playlistDTO);
    }

    public Playlist criarPlaylistAleatoria(PlaylistDTO p, MusicController musicController) {
        List<Music> todas = new ArrayList<>(musicController.getAllMusic());

        Collections.shuffle(todas);

        List<Music> selecionadas = new ArrayList<>(todas.subList(0, Math.min(30, todas.size())));


        p.setMusicCollection(selecionadas);

        Playlist aleatoria = new RandomPlaylist(p);

        addPlaylist(aleatoria);


        return aleatoria;
    }

    public Playlist createGenrePlaylist(PlaylistDTO playlistDTO, MusicController musicController, Genre g, int maxDuration) {
        List<Music> genreTracks = musicController.getAllMusic().stream()
                .filter(m -> m.getGenre().equals(g))
                .toList();

        List<Music> selected = new ArrayList<>();
        int totalDuration = 0;

        for (Music music : genreTracks) {
            if (totalDuration + music.getDuration() <= maxDuration) {
                selected.add(music);
                totalDuration += music.getDuration();
            }
        }

        playlistDTO.setMusicCollection(selected);

        Playlist genrePlaylist = new GenrePlaylist(playlistDTO, maxDuration, g);
        addPlaylist(genrePlaylist);

        return genrePlaylist;
    }



    public Playlist createFavoritePlaylist(PlaylistDTO pDTO, MusicController musicController, int maxDuration, boolean soExplicitas, List<Reproducao> historico) throws PlaylistException {


        if (historico.isEmpty()) {
            throw new PlaylistException("Impossivel criar playlist de favorito: Historico vazio");
        }

        List<Music> selected = new ArrayList<>();
        selected = favoritePlaylistMusics(maxDuration,soExplicitas,musicController,historico);


        pDTO.setMusicCollection(selected);

        FavouritePlaylist favplay = new FavouritePlaylist(pDTO,soExplicitas,maxDuration);
        addPlaylist(favplay);

        return favplay ;
    }


    public void removePlaylist(String id) {
        playlists.remove(id);
    }

    /////////////////////////////////////////////// Playlist favorita /////////////////////////////////////////

    public Hashtable<Genre, Double> calcularPreferenciasPorGenero(MusicController musicController, List<Reproducao> historico) {

        Hashtable<Genre, Integer> contagemGeneros = new Hashtable<>();
        int totalReproducoes = 0;

        // Conta quantas vezes cada gênero foi ouvido
        for (Reproducao rep : historico) {
            String musicId = rep.getMusicId();
            Genre genero = musicController.getGenre(musicId);
            if (genero != null) {
                contagemGeneros.put(genero, contagemGeneros.getOrDefault(genero, 0) + 1);
                totalReproducoes++;
            }
        }

        // Converte contagem para proporção
        Hashtable<Genre, Double> proporcoes = new Hashtable<>();
        for (Map.Entry<Genre, Integer> entry : contagemGeneros.entrySet()) {
            double proporcao = (double) entry.getValue() / totalReproducoes;
            proporcoes.put(entry.getKey(), proporcao);
        }

        return proporcoes;
    }

    public List<Music> favoritePlaylistMusics(int duration, boolean soExplicitas, MusicController musicController, List<Reproducao> historico) {

        Hashtable<Genre, Double> proporcoes = calcularPreferenciasPorGenero(musicController, historico);

        List<Music> musicas = new ArrayList<>();
        musicas = musicController.generatePlaylist(proporcoes, duration, soExplicitas);

        return musicas;
    }

}
