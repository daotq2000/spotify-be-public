package com.spotify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.GenresResponseType;
import com.spotify.service.GenresService;
import com.spotify.service.impl.FileStorageServiceImpl;
import com.spotify.ultils.GenericsHelper;
import com.spotify.ultils.LoggerFactory;
import com.spotify.ultils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "*")
public class GenresController {
    private final GenresService genresService;
    private final FileStorageServiceImpl fileStorageService;
    Logger log = LoggerFactory.getLogger("Genres");

    @Autowired
    public GenresController(GenresService genresService, FileStorageServiceImpl fileStorageService) {
        this.genresService = genresService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> paginationGenres(@RequestBody PaginationRequest paginationRequest) throws JsonProcessingException {
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("GenresController - paginationGenres - start - request: ["+reqStartTime+"]");
        log.info("paginationRequest: "+ GenericsHelper.ObjectToJsonValue(paginationRequest));
        ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(genresService.paginationGenres(paginationRequest), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("GenresController - paginationGenres - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "+GenericsHelper.ObjectToJsonValue(response));
        return response;
    }

    @PostMapping("/save")
    public ResponseEntity<GenresResponseType> saveGenres(@RequestParam("genres") String genres,
                                                         @RequestParam("file") MultipartFile image
    ) throws Exception {
        GenresResponseType genresResponseType = new ObjectMapper().readValue(genres, GenresResponseType.class);
        String imageURL = fileStorageService.uploadToCloudinary(image);
        genresResponseType.setImage(imageURL);
        return new ResponseEntity<>(genresService.save(genresResponseType), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenresResponseType> findByIdGenres(@PathVariable("id") int id) {
        GenresResponseType genresResponseType = genresService.findById(id);
        return new ResponseEntity<>(genresResponseType, HttpStatus.OK);
    }
    @PostMapping("/album-song/{id}")
    public ResponseEntity<Map<String,Object>> getAlbumAndSongByGenresId(@RequestParam(value = "paginationAlbum") String paginationAlbum,
                                                                        @RequestParam(value = "paginationSong") String paginationSong,
                                                                        @PathVariable("id") int id
                                                                        )throws Exception {
        ResponseEntity<Map<String,Object>>  pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("GenresController - getAlbumAndSongByGenresId - start - request: ["+reqStartTime+"]");
        log.info("paginationAlbum Request: "+paginationAlbum);
        log.info("paginationSong Request: "+paginationAlbum);
        PaginationRequest albumRequest = new ObjectMapper().readValue(paginationAlbum,PaginationRequest.class);
        PaginationRequest songRequest = new ObjectMapper().readValue(paginationSong,PaginationRequest.class);
        pResponse = new ResponseEntity<>(genresService.getSongAndAlbumByGenresId(id,albumRequest,songRequest),HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("GenresController - getAlbumAndSongByGenresId - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }
}
