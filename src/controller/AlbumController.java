package controller;

import album.Album;
import DTO.*;
import music.*;
import user.User;

import java.io.Serializable;
import java.util.*;

public class AlbumController implements Serializable {
    private Map<String, Album> albuns;

    public AlbumController() {
        this.albuns = new HashMap<>();
    }

    public AlbumController(AlbumController albumController) {
        this.albuns = new HashMap<>();
        for (Map.Entry<String, Album> entry : albumController.albuns.entrySet()) {
            this.albuns.put(entry.getKey(), entry.getValue().clone());
        }
    }


    public boolean isEmpty(){
        return this.albuns.isEmpty();
    }

    public void addAlbum(Album album) {
        albuns.put(album.getId(), album);
    }

    public void removeAlbum(String id) {
        albuns.remove(id);
    }

    public Map<String, Album> getAlbumMap() {
        HashMap<String, Album> copiaAlbum = new HashMap<>();
        for (Map.Entry<String, Album> entry : albuns.entrySet()) {
            String chave = entry.getKey();
            Album albumOriginal = entry.getValue();
            Album album = albumOriginal.clone();
            copiaAlbum.put(chave, album);
        }
        return copiaAlbum;
    }

    public Collection<Album> getAllAlbums() {
        List<Album> albumCopies = new ArrayList<>();
        for (Album album : albuns.values()) {
            albumCopies.add(album.clone());
        }
        return albumCopies;
    }

    public void displayAllAlbums() {
        if (albuns.isEmpty()) {
            System.out.println("Nenhum álbum disponível.");
            return;
        }

        System.out.println("\n====== LISTA DE ÁLBUNS ======\n");
        for (Album a : albuns.values()) {
            System.out.println(a);
        }
    }

    public boolean addMusicaAoAlbum(String albumId, Music musica){
        Album album = this.albuns.get(albumId);
        if (album != null && musica != null) {
            album.addTrack(musica);
            return true;
        }
        return false;
    }


    public void addAlbum(AlbumDTO dto) throws AlbumException {
        boolean exists = albuns.values().stream().anyMatch(
                album -> album.getName().equalsIgnoreCase(dto.getName()) &&
                        album.getArtist().equalsIgnoreCase(dto.getArtist()) &&
                        album.getYear() == dto.getYear()
        );

        if(exists) {
            throw new AlbumException("Album Já existente.");
        }

        try {
            Album novo = new Album(dto);
            albuns.put(novo.getId(), novo);
        } catch (Exception e) {
            throw new AlbumException("Erro ao criar álbum: " + e.getMessage());
        }
    }

    public Album getAlbum(String id) {
        Album album = this.albuns.get(id);
        return album != null ? album.clone() : null;
    }


}
