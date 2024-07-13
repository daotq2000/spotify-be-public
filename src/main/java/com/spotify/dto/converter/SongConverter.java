package com.spotify.dto.converter;

import com.spotify.dto.response.ArtistResponseType;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.dto.response.GenresResponseType;
import com.spotify.dto.response.SongResponseType;
import com.spotify.entities.ArtistSongId;
import com.spotify.entities.ArtistSongs;
import com.spotify.entities.Songs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongConverter {
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ArtistSongConverter artistSongConverter;
    @Autowired
    private ArtistConverter artistConverter;
    @Autowired
    private GenresConverter genresConverter;

    public SongResponseType convertToDTO(Songs song) {
        SongResponseType songResponseType = new SongResponseType();
        songResponseType.setMediaUrl(song.getMediaUrl());
        songResponseType.setImage(song.getImage());
        songResponseType.setDescription(song.getDescription());
        songResponseType.setCountListen(song.getCountListen());
        songResponseType.setId(song.getId());
        songResponseType.setTitle(song.getTitle());
        songResponseType.setLyrics(song.getLyrics());
        songResponseType.setCountListen(song.getCountListen());
        songResponseType.setDownloadPermission(song.getDownloadPermission());
        songResponseType.setTimePlay(song.getTimePlay());
        if (null != song.getGenres()) {
            songResponseType.setGenres(GenresResponseType.builder().id(song.getGenres()).build());
        }
        return songResponseType;
    }

    public Songs convertToEntity(SongResponseType songResponseType) {
        Songs song = new Songs();
        song.setCountListen(songResponseType.getCountListen());
        song.setDescription(songResponseType.getDescription());
        song.setImage(songResponseType.getImage());
        song.setLyrics(songResponseType.getLyrics());
        song.setTitle(songResponseType.getTitle());
        song.setMediaUrl(songResponseType.getMediaUrl());
        song.setDownloadPermission(songResponseType.getDownloadPermission());
        song.setTimePlay(songResponseType.getTimePlay());
        song.setId(songResponseType.getId());
        if (null != songResponseType.getGenres()) {
            song.setGenres(songResponseType.getGenres().getId());
        }
        List<ArtistSongs> artistSongs = new ArrayList<>();
        List<ArtistSongResponseType> artistSongResponseTypes = songResponseType.getArtistSongs();
        if (null != artistSongResponseTypes) {
            artistSongResponseTypes.forEach(artistSongResponseType -> {
                ArtistSongId artistSongId = new ArtistSongId();
                artistSongId.setSongs(song);
                artistSongId.setArtists(artistConverter.convertToEntity(artistSongResponseType.getArtists()));
                ArtistSongs artistSongsValue = new ArtistSongs(artistSongId);
                artistSongs.add(artistSongsValue);
            });
            song.setArtistSongs(artistSongs);
        }
        return song;
    }
}
