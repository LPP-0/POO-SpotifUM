package user;
import DTO.UserDTO;
import music.Music;
import playlist.Playlist;
import album.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class UserPremiumBase extends UserPremium implements Serializable {

    public UserPremiumBase(UserDTO userDTO) {
        super(userDTO);
    }
    public UserPremiumBase() { super(); }
    public UserPremiumBase(String username, String name, String email, String address, String password, LocalDate birthday, double points,List<Album> albums, List<Playlist> playlists) {
        super(username, name, email, address, password, birthday, points,playlists,albums);
    }
    public UserPremiumBase(UserPremiumBase userPremiumBase) {
        super(userPremiumBase);
    }

    @Override
    public void addPoints() {
        double points = this.getPoints();
        this.setPoints(points + 10);
    }

    @Override
    public UserPremium clone() {
        return new UserPremiumBase(this);
    }

}
