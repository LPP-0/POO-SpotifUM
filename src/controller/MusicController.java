package controller;

import DTO.MusicDTO;
import DTO.PlaylistDTO;
import music.*;
import playlist.RandomPlaylist;
import utils.*;
import utils.*;

import java.io.Serializable;
import java.util.*;

public class MusicController implements Serializable {

    private Map<String, Music> musicLibrary;

    public MusicController() {
        musicLibrary = new HashMap<>();
    }

    public MusicController(MusicController musicController) {
        musicLibrary = new HashMap<>();
        for (Music music : musicController.musicLibrary.values()) {
            this.musicLibrary.put(music.getId(), music);
        }
    }

    public Genre getGenre(String musicId) {
        Music music = this.getMusic(musicId);
        if (music == null) {return null;}
        return music.getGenre();
    }


    public void addMusic(Music music) {
        this.musicLibrary.put(music.getId(), music);
    }

    public Music getMusic(String id) {
        Music music = this.musicLibrary.get(id.trim());
        if (music == null) {return null;}
        else {return music.clone();}
    }

    public Collection<Music> getAllMusic() {
        List<Music> musicCopies = new ArrayList<>();
        for (Music music : musicLibrary.values()) {
            musicCopies.add(music.clone());
        }

        return musicCopies;
    }

    public void displayAllMusic() {
        for (Music m : getAllMusic()) { // (Music m : musicLibrary.values())
            System.out.println(m);
        }
    }

    public void removeMusic(String musicId) {
        this.musicLibrary.remove(musicId);
    }



    public Music addMusic(MusicDTO dto) throws MusicException {
            Music nova = dto.isMultimedia() ? new MusicMultimedia(dto) : new Music(dto);

            if(musicLibrary.values().stream().anyMatch(music -> music.equals(nova))){
                throw new MusicException("Música já existente");
            }

            this.addMusic(nova);
            return nova;

    }

    public List<Music> generatePlaylist(Hashtable<Genre, Double> genreDistribution, int totalTime, boolean soExplicitas) {
        List<Music> playlist = new ArrayList<>();
        Map<Genre, List<Music>> genreMap = new HashMap<>();

        for (Music music : musicLibrary.values()) {
            genreMap.computeIfAbsent(music.getGenre(), k -> new ArrayList<>()).add(music);
        }

        for (Map.Entry<Genre, Double> entry : genreDistribution.entrySet()) {
            Genre genre = entry.getKey();
            double proportion = entry.getValue();
            int genreTimeTarget = (int) Math.round(proportion * totalTime);

            List<Music> songs = genreMap.getOrDefault(genre, new ArrayList<>());
            songs.sort(Comparator.comparingInt(Music::getDuration));

            int accumulatedTime = 0;
            for (Music song : songs) {
                if (accumulatedTime + song.getDuration() <= genreTimeTarget) {

                    if(soExplicitas){

                        if(song.isExplicit()){
                            playlist.add(song);
                            accumulatedTime += song.getDuration();
                        }
                    }else{
                        playlist.add(song);
                        accumulatedTime += song.getDuration();
                    }
                }
                if (accumulatedTime >= genreTimeTarget) break;
            }
        }

        return playlist;
    }

    public void addStream(String musicId){
        Music m = this.musicLibrary.get(musicId);
        //this.displayAllMusic();
        //System.out.println(musicId);

        if(m != null) {
            m.incrementarStreams();
        }
    }

    public List<Music> randomMusicList(){
        PlaylistDTO p = new PlaylistDTO();

        List<Music> todas = new ArrayList<>(this.getAllMusic());

        Collections.shuffle(todas);

        List<Music> selecionadas = new ArrayList<>(todas.subList(0, Math.min(30, todas.size())));


        p.setMusicCollection(selecionadas);

        return selecionadas;
    }



}
