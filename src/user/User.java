package user;

import DTO.UserDTO;
import album.Album;
import music.Music;
import playlist.Playlist;
import reproducao.Reproducao;
import utils.Utils;
//import java.io.Serializable;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public abstract class User implements Serializable {

    private String username;
    private String name;
    private String email;
    private String address;
    private String password;
    private LocalDate birthday;
    private double points;
    private List<Reproducao> historico = new ArrayList<>();

    /**
     * Construtores
     */

    public User() {
        this.username = "";
        this.name = "";
        this.email = "";
        this.address = "";
        this.password = "";
        this.points = 0;
        this.birthday = null;
    }

    public User(String username, String name, String email, String address, String password, LocalDate birthday, double points) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.points = points;
        this.password = password;
        this.birthday = birthday;

    }

    public User(User user) {
        this.username = user.username;
        this.name = user.name;
        this.email = user.email;
        this.address = user.address;
        this.points = user.points;
        this.password = user.password;

    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.address = userDTO.getAddress();
        this.password = userDTO.getPassword();
        this.birthday = userDTO.getBirthday();
        this.points = 0.0;
    }


    /**
     * Getters / Setters
     */

    public List<Reproducao> getHistorico() {
        return historico;
    }

    public List<Playlist> getPlaylists() {
        return Collections.emptyList();
    }

    public List<Album> getAlbums() {return  Collections.emptyList();}

    public void adicionarReproducao(String musicID) {
        Reproducao r = new Reproducao(this.getUsername(), musicID, LocalDate.now());
        historico.add(r);
    }

    public abstract void addPoints();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.points = Double.parseDouble(df.format(points).replace(",", "."));
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


    /**
     * Clone / equals / toString
     */

    public abstract User clone();


    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;

        User u = (User) o;
        return  u.getUsername().equals(this.username)    &&
                u.getName().equals(this.name)            &&
                u.getEmail().equals(this.email)          &&
                u.getAddress().equals(this.address)      &&
                u.getPassword().equals(this.password)    &&
                u.points == this.points;

    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", points=" + points +
                ", password=" + password +
                '}';
    }


    public void addPlaylist(Playlist p){
    }


}