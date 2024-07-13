package com.spotify.dto.converter;

import com.spotify.dto.response.ArtistAlbumResponseType;
import com.spotify.dto.response.ArtistResponseType;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.entities.Artists;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArtistConverter {
    @Autowired
    private ArtistSongConverter artistSongConverter;
    @Autowired
    private ArtistConverter artistConverter;
    @Autowired
    private AlbumConverter albumConverter;
    private final ModelMapper modelMapper = new ModelMapper();

    public ArtistResponseType convertToDTO(Artists artists) {
        ArtistResponseType artistResponseType = new ArtistResponseType();
        artistResponseType.setDescription(artists.getDescription());
        artistResponseType.setImage(artists.getImage());
        artistResponseType.setId(artists.getId());
        artistResponseType.setBirthDay(artists.getBirthDay());
        artistResponseType.setCountryActive(artists.getCountryActive());
        artistResponseType.setFullName(artists.getFullName());
        artistResponseType.setGender(artists.isGender());
        artistResponseType.setCountListen(artists.getCountListen());
        return artistResponseType;
    }

    public ArtistResponseType convertAllDependencies(Artists artists) {
        ArtistResponseType artistResponseType = new ArtistResponseType();
        artistResponseType.setDescription(artists.getDescription());
        artistResponseType.setImage(artists.getImage());
        artistResponseType.setId(artists.getId());
        artistResponseType.setBirthDay(artists.getBirthDay());
        artistResponseType.setCountryActive(artists.getCountryActive());
        artistResponseType.setCountListen(artists.getCountListen());
        artistResponseType.setFullName(artists.getFullName());
        artistResponseType.setGender(artists.isGender());
        List<ArtistSongResponseType> artistSongResponseTypes = new ArrayList<>();
        if (null != artists.getArtistSongs()) {
            artists.getArtistSongs().forEach(artistSongs -> {
                ArtistSongResponseType artistSongs1 = artistSongConverter.convertToDTO(artistSongs);
                artistSongResponseTypes.add(artistSongs1);
            });
            artistResponseType.setArtistSongs(artistSongResponseTypes);
        }
        List<ArtistAlbumResponseType> albumResponseTypes = new ArrayList<>();
        if (null != artists.getArtistAlbums()) {
            artists.getArtistAlbums().forEach(artistAlbum -> {
                ArtistAlbumResponseType artistAlbumResponseType = new ArtistAlbumResponseType();
                artistAlbumResponseType.setArtists(artistConverter.convertToDTO(artistAlbum.getArtistAlbumId().getArtists()));
                artistAlbumResponseType.setAlbums(albumConverter.convertToDTO(artistAlbum.getArtistAlbumId().getAlbums()));
            });
            artistResponseType.setArtistAlbums(albumResponseTypes);
        }
        return artistResponseType;
    }

    public Artists convertToEntity(ArtistResponseType artistResponseType) {
        Artists artists = modelMapper.map(artistResponseType, Artists.class);
        return artists;
    }
}
