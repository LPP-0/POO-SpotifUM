package Interactive;

import DTO.AlbumDTO;
import DTO.MusicDTO;
import DTO.PlaylistDTO;
import DTO.UserDTO;
import controller.AlbumController;
import controller.MusicController;
import controller.PlaylistController;
import controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumUITest {
    private AlbumDTO album1;
    private AlbumDTO album2;


    private AlbumController albumController = new AlbumController();

    @BeforeEach
    public void setUp() {
        album1 = new AlbumDTO("Dreams", "Coldplay", 2020);
        album2 = new AlbumDTO("Future Nostalgia", "Dua Lipa", 2021);
        albumController.addAlbum(album1);
        albumController.addAlbum(album2);
    }



}