package com.spotify.dto.converter;

import com.spotify.dto.response.AlbumResponseType;
import com.spotify.dto.response.AlbumSongResponseType;
import com.spotify.dto.response.ArtistAlbumResponseType;
import com.spotify.dto.response.SongResponseType;
import com.spotify.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AlbumConverter {
    @Autowired
    GenresConverter genresConverter;
    @Autowired
    SongConverter songConverter;
    @Autowired
    ArtistConverter artistConverter;
    private final ModelMapper modelMapper = new ModelMapper();

    public AlbumResponseType convertToDTO(Albums albums) {

        AlbumResponseType albumResponseType = new AlbumResponseType();
        albumResponseType.setId(albums.getId());
        albumResponseType.setAlbumName(albums.getAlbumName());
        albumResponseType.setCountListen(albums.getCountListen());
        albumResponseType.setId(albums.getId());
        albumResponseType.setImage(albums.getImage());
        albumResponseType.setReleaseDate(albums.getReleaseDate());
        albumResponseType.setTotalListen(Objects.isNull(albums.getCountListen())?null:albums.getCountListen().intValue());
        albumResponseType.setAlbumTimeLength(albums.getAlbumTimeLength());
        albumResponseType.setDownloadPermission(albums.isDownloadPermission());
        albumResponseType.setAlbumSongs(albums.getAlbumSongs().stream().map(f->AlbumSongResponseType.builder().songs(SongResponseType.builder().id(f.getAlbumSongId().getSongs().getId()).build()).build()).collect(Collectors.toList()));
        return albumResponseType;
    }

    public AlbumResponseType convertToAllDependency(Albums albums) {
        AlbumResponseType albumResponseType = new AlbumResponseType();
        albumResponseType.setImage(albums.getImage());
        albumResponseType.setAlbumName(albums.getAlbumName());
        albumResponseType.setCountListen(albums.getCountListen());
        albumResponseType.setAlbumTimeLength(albums.getAlbumTimeLength());
        albumResponseType.setReleaseDate(albums.getReleaseDate());
        albumResponseType.setTotalListen(Objects.isNull(albums.getCountListen())?null:albums.getCountListen().intValue());
        albumResponseType.setId(albums.getId());
        albumResponseType.setDownloadPermission(albums.isDownloadPermission());
        albumResponseType.setGenres(genresConverter.convertToDTO(albums.getGenres()));
        List<ArtistAlbumResponseType> albumResponseTypes = new ArrayList<>();
        List<ArtistAlbums> artistAlbums = albums.getArtistAlbums();
        if (null != artistAlbums && !(artistAlbums.isEmpty())) {
            artistAlbums.forEach(artistAlbums1 -> {
                ArtistAlbumResponseType artistAlbumResponseType = new ArtistAlbumResponseType();
                artistAlbumResponseType.setArtists(artistConverter.convertToDTO(artistAlbums1.getArtistAlbumId().getArtists()));
                albumResponseTypes.add(artistAlbumResponseType);
            });
        }
        List<AlbumSongs> albumSongs = albums.getAlbumSongs();
        List<AlbumSongResponseType> albumSongResponseTypes = new ArrayList<>();
        if (null != albumSongs && !albumSongs.isEmpty()) {
            albumSongs.forEach(albumSongs1 -> {
                AlbumSongResponseType albumSongResponseType = new AlbumSongResponseType();
                albumSongResponseType.setSongs(songConverter.convertToDTO(albumSongs1.getAlbumSongId().getSongs()));
                albumSongResponseTypes.add(albumSongResponseType);
            });
        }
        if (!albumSongResponseTypes.isEmpty() && null != albumSongResponseTypes) {
            albumResponseType.setAlbumSongs(albumSongResponseTypes);
        }
        if (null != albumResponseTypes && !(albumResponseTypes.isEmpty())) {
            albumResponseType.setArtistAlbums(albumResponseTypes);
        }
        return albumResponseType;
    }

    public Albums convertToEntity(AlbumResponseType albumResponseType) {
        Albums albums = modelMapper.map(albumResponseType, Albums.class);
        List<ArtistAlbums> artistAlbumsList = new ArrayList<>();
        List<ArtistAlbumResponseType> albumResponseTypes = albumResponseType.getArtistAlbums();
        if (null != albumResponseTypes) {
            albumResponseTypes.forEach(artistAlbumResponseType -> {
                ArtistAlbumId artistAlbumId = new ArtistAlbumId();
                artistAlbumId.setAlbums(albums);
                artistAlbumId.setArtists(artistConverter.convertToEntity(artistAlbumResponseType.getArtists()));
                ArtistAlbums artistAlbums = new ArtistAlbums(artistAlbumId);
                artistAlbumsList.add(artistAlbums);
            });
            albums.setArtistAlbums(artistAlbumsList);
        }
        List<AlbumSongs> albumSongsList = new ArrayList<>();
        List<AlbumSongResponseType> albumSongResponseTypes = albumResponseType.getAlbumSongs();
        if (null != albumSongResponseTypes) {
            albumSongResponseTypes.forEach(albumSongResponseType -> {
                AlbumSongId albumSongId = new AlbumSongId();
                albumSongId.setAlbums(albums);
                albumSongId.setSongs(songConverter.convertToEntity(albumSongResponseType.getSongs()));
                AlbumSongs albumSongs = new AlbumSongs(albumSongId);
                albumSongsList.add(albumSongs);
            });
            albums.setAlbumSongs(albumSongsList);
        }
        return albums;
    }

}
