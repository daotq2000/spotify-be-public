package com.spotify.dto.converter;

import com.spotify.dto.response.AlbumSongResponseType;
import com.spotify.entities.AlbumSongId;
import com.spotify.entities.AlbumSongs;
import com.spotify.entities.Albums;
import com.spotify.entities.Songs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumSongConverter {
    private final SongConverter songConverter;
    private final AlbumConverter albumConverter;

    @Autowired
    public AlbumSongConverter(SongConverter songConverter, AlbumConverter albumConverter) {
        this.songConverter = songConverter;
        this.albumConverter = albumConverter;
    }

    public AlbumSongResponseType convertToDTO(AlbumSongs albumSongs) {
        AlbumSongResponseType albumSongResponseType = new AlbumSongResponseType();
        Songs songs = albumSongs.getAlbumSongId().getSongs();
        Albums albums = albumSongs.getAlbumSongId().getAlbums();
        if (null != songs) {
            albumSongResponseType.setSongs(songConverter.convertToDTO(songs));
        }
        if (null != albums) {
            albumSongResponseType.setAlbums(albumConverter.convertToDTO(albums));
        }
        return albumSongResponseType;
    }

    public AlbumSongs convertToEntity(AlbumSongResponseType albumSongResponseType) {
        AlbumSongs albumSongs = new AlbumSongs();
        AlbumSongId albumSongId = new AlbumSongId();
        albumSongId.setAlbums(albumConverter.convertToEntity(albumSongResponseType.getAlbums()));
        albumSongId.setSongs(songConverter.convertToEntity(albumSongResponseType.getSongs()));
        albumSongs.setAlbumSongId(albumSongId);
        return albumSongs;
    }
}
