package playlist;

import DTO.PlaylistDTO;
import music.Music;
import user.UserPremiumTop;

import java.io.Serializable;
import java.util.List;

public class PremiumPlaylist extends Playlist implements Serializable {
    public PremiumPlaylist(PlaylistDTO playlistDTO) {
        super(playlistDTO);
    }
    public PremiumPlaylist(){
        super();
    }
    public PremiumPlaylist(String title, List<Music> musicCollection, boolean isPrivate, boolean isRandom) {
        super(title, musicCollection, isPrivate, isRandom);
    }
    public PremiumPlaylist(PremiumPlaylist premiumPlaylist) {
        super(premiumPlaylist);
    }

    @Override
    public Playlist clone() {
        return new PremiumPlaylist(this);
    }

}
