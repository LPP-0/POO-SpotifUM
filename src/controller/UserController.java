package controller;

import DTO.PlaylistDTO;
import DTO.UserDTO;
import album.Album;
import user.*;
import playlist.*;
import music.Music;
import utils.Genre;
import utils.Utils;

import java.io.Serializable;
import java.util.*;

public class UserController implements Serializable {
    private Map<String, User> users;

    public UserController(UserController userController) {
        users = new HashMap<>();
        for (User user : userController.users.values()) {
            users.put(user.getUsername(), user);
        }
    }
    public UserController() {
        users = new HashMap<>();
    }

    public User getUser(String username) {
        User user = this.users.get(username.toLowerCase());
        return user != null ? user.clone() : null;
    }

    public void removeUser(String idUser){
        this.users.remove(idUser.toLowerCase());
    }


    public User authenticate(List<String> login) {
        String username = login.get(0).toLowerCase();

        if (this.users.containsKey(username)) {
            User u = this.users.get(username);
            if (u.getPassword().equals(login.get(1))) {
                return u;
            }
        }
        return null;
    }

    public void addAlbumToUser(User user, Album album) throws UserException {
        if (album == null) {
            throw new UserException("Álbum inválido.");
        }

        if (user instanceof UserPremium premium) {
            if (premium.hasAlbum(album)) {
                throw new UserException("O álbum já está na sua biblioteca.");
            }
            premium.addAlbum(album);
        }
        else {
            throw new UserException("Erro ao adicionar um álbum!");
        }
    }


    public Collection<User> getUsers() {
        List<User> userCopies = new ArrayList<>();
        for (User user : users.values()) {
            userCopies.add(user.clone());
        }

        return userCopies;
    }

    public void addUser(UserDTO userDTO) throws UserException {
        if (userDTO == null || userDTO.getUsername() == null)
            throw new UserException("Utilizador inválido (username nulo).");

        if (this.users.containsKey(userDTO.getUsername()))
            throw new UserException("Utilizador já existe com o username: " + userDTO.getUsername());

        User user = switch (userDTO.getSubscriptionPlan()) {
            case 1 -> new UserPremiumBase(userDTO);
            case 2 -> new UserPremiumTop(userDTO);
            default -> new UserFree(userDTO);
        };

        if (user.getBirthday() == null)
            throw new UserException("Data de nascimento inválida.");

        this.users.put(userDTO.getUsername().toLowerCase(), user);
    }



    public void addPlaylist(User user, PlaylistDTO playlistDTO, PlaylistController playlistController, MusicController musicController, Genre g, int maxDuration, boolean soExplicitas) throws PlaylistException {

        if (playlistDTO == null) {
            throw new PlaylistException("PlaylistDTO não pode ser nulo.");
        }

        Playlist p;

        switch (playlistDTO.getPlaylistType()) {
            case 4 -> {
                p = playlistController.criarPlaylistAleatoria(playlistDTO, musicController);
            }
            case 3 -> {
                try{
                    p = playlistController.createFavoritePlaylist(playlistDTO, musicController, maxDuration, soExplicitas,user.getHistorico());
                }catch(PlaylistException e){
                    throw new PlaylistException(e.getMessage());
                }
            }
            case 2 -> {
                p = playlistController.criarPlaylistNormal(playlistDTO);
            }
            case 1 -> {
                p = playlistController.createGenrePlaylist(playlistDTO, musicController, g, maxDuration);
            }
            default -> throw new PlaylistException("Tipo de playlist inválido: " + playlistDTO.getPlaylistType());
        }

        if (p.getMusicCollection() == null || p.getMusicCollection().isEmpty()) {
            throw new PlaylistException("A playlist gerada está vazia. Não foi possível adicioná-la.");
        }

        user.addPlaylist(p.clone());
    }


    public void listUsers() {
        if(this.users.isEmpty()){ System.out.println("0 Users");}
        for (User user : this.users.values()) {
            System.out.println(user.toString());
        }
    }

    public void addUser(User user) {
        this.users.put(user.getUsername().toLowerCase(), user);

    }

}
