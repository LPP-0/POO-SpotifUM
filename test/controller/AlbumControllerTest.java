package controller;

import DTO.AlbumDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumControllerTest {
    private AlbumDTO album1;
    private AlbumDTO album2;


    private AlbumController albumController = new AlbumController();

    @BeforeEach
    public void setUp() {
        // USERS
        album1 = new AlbumDTO("Dreams", "Coldplay", 2020);
        album2 = new AlbumDTO("Future Nostalgia", "Dua Lipa", 2021);
        albumController.addAlbum(album1);
        albumController.addAlbum(album2);


    }

    @Test
    public void testAddAlbum_erro() {
        AlbumException e = assertThrows(AlbumException.class, () -> {
            albumController.addAlbum(album1);
        });
        assertEquals("Album Já existente.", e.getMessage());
    }

    @Test
    public void testAddAlbum_success() {
        assertDoesNotThrow(() -> {
            albumController.addAlbum(new AlbumDTO("Teste", "Teste", 2020));
        });
    }
}