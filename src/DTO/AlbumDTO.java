package DTO;

import java.util.*;
import music.*;

public class AlbumDTO {
    private final String name;
    private final String artist;
    private final int year;

    public AlbumDTO(String name, String artist, int year) {
        this.name = name;
        this.artist = artist;
        this.year = year;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public int getYear() {
        return this.year;
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", year=" + year +
                '}';
    }

}