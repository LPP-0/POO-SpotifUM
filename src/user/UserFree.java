package user;

import DTO.UserDTO;
import music.Music;
import playlist.RandomPlaylist;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class UserFree extends User implements Serializable {

    public UserFree() {
        super();
    }

    public UserFree(String username, String name, String email, String address,String password, LocalDate birthday, int points) {
        super(username, name, email,address,password,birthday,points);
    }

    public UserFree(UserFree user){
        super(user);
    }

    public UserFree(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public void addPoints() {
        double points = this.getPoints();
        this.setPoints(points + 5);
    }

    @Override
    public UserFree clone(){
        return new UserFree(this);
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

}
