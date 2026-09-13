package user;
import DTO.UserDTO;
import music.Music;
import playlist.Playlist;
import album.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class UserPremiumTop extends UserPremium implements Serializable {

    public UserPremiumTop(UserDTO userDTO) {
        super(userDTO);
        super.setPoints(100);
    }
    public UserPremiumTop(String username, String name, String email, String address, String password, LocalDate birthday, double points, List<Album> albums, List<Playlist> playlists) {
        super(username, name, email, address, password, birthday, points,playlists,albums);
        super.setPoints(100);

    }

    public UserPremiumTop(){
        super();
        super.setPoints(100);

    }

    public UserPremiumTop(UserPremiumTop userPremiumTop) {
        super(userPremiumTop);
    }

    @Override
    public void addPoints() {
        double points = this.getPoints();
        this.setPoints(points * 1.025);
    }

    @Override
    public UserPremiumTop clone() {
        return new UserPremiumTop(this);
    }


}


























/*
    public UserPremiumTop() {
        super();
    }

    public UserPremiumTop(String name, String email, String address){
        super(name,email,address);
    }

    public UserPremiumTop(UserPremiumTop user){
        super(user);
    }

    public UserPremiumTop clone(){
        return new UserPremiumTop(this);
    }

    @Override
    public String toString() {
        return String.format("User{" +
                "name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", address='" + this.getAddress() + '\'' +
                ", points=" + this.getPoints() +
                ", playlists=" + this.getPlaylists() +
                '}');
    }
*/