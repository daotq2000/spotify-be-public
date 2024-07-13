package com.spotify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.ArtistResponseType;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.service.ArtistService;
import com.spotify.service.impl.FileStorageServiceImpl;
import com.spotify.ultils.GenericsHelper;
import com.spotify.ultils.LoggerFactory;
import com.spotify.ultils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/artist")
@CrossOrigin(origins = "*")
public class ArtistController {
    private final ArtistService artistService;
    private final FileStorageServiceImpl fileStorageService;
    Logger log = LoggerFactory.getLogger("Artist");
    @Autowired
    public ArtistController(ArtistService artistService, FileStorageServiceImpl fileStorageService) {
        this.artistService = artistService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> paginationArtist(@RequestBody PaginationRequest paginationRequest) throws JsonProcessingException {
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("ArtistController - paginationArtist - start - request: ["+reqStartTime+"]");
        log.info("paginationRequest: "+ GenericsHelper.ObjectToJsonValue(paginationRequest));
        ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(artistService.paginationArtist(paginationRequest), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("ArtistController - paginationArtist - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "+GenericsHelper.ObjectToJsonValue(response));
        return response;
    }

    @PostMapping("/save")
    public ResponseEntity<ArtistResponseType> saveArtist(@RequestParam("artist") String artistJson,
                                                         @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<ArtistResponseType> pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("ArtistController - saveArtist - start - request: ["+reqStartTime+"]");
        log.info("artistJsonRequest: "+artistJson);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ArtistResponseType artistResponseType = objectMapper.readValue(artistJson, ArtistResponseType.class);
        String fileSave = fileStorageService.uploadToCloudinary(file);
        artistResponseType.setImage(fileSave);
        ArtistResponseType response = artistService.save(artistResponseType);
        pResponse = new ResponseEntity<>(response, HttpStatus.CREATED);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("ArtistController - saveArtist - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));

        return pResponse;
    }

    @PutMapping("/update-artist/{id}")
    public ResponseEntity<ArtistResponseType> updateArtist(@RequestParam("artist") String artistJson,
                                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                                           @PathVariable("id") Integer id) throws Exception {
        ResponseEntity<ArtistResponseType> pResponse = null;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("ArtistController - updateArtist - start - request: ["+reqStartTime+"]");
        log.info("artist JsonRequest: "+artistJson);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ArtistResponseType artistResponseType = objectMapper.readValue(artistJson, ArtistResponseType.class);
        artistResponseType.setId(id);
        if (null != file) {
            String fileSave = fileStorageService.uploadToCloudinary(file);
            artistResponseType.setImage(fileSave);
        }
        ArtistResponseType response = artistService.save(artistResponseType);
        pResponse = new ResponseEntity<>(response, HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("ArtistController - updateArtist - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArtist(@PathVariable("id") int id) {
        boolean isDelete = artistService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseType> findById(@PathVariable("id") int id) throws JsonProcessingException {
        ResponseEntity<ArtistResponseType> pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("ArtistController - findById - start - request: ["+reqStartTime+"]");
        log.info("AlbumResponseType ID: "+id);
        ArtistResponseType response = artistService.findById(id);
        pResponse = new ResponseEntity<>(response, HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("ArtistController - findById - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));

        return pResponse;
    }

    @GetMapping("/artist-song/{artistId}")
    public ResponseEntity<List<ArtistSongResponseType>> getListArtistSongByArtistId(@PathVariable("artistId") int artistId) throws JsonProcessingException {
        ResponseEntity<List<ArtistSongResponseType>>  pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("ArtistController - getListArtistSongByArtistId - start - request: ["+reqStartTime+"]");
        log.info("albumID: "+artistId);
        pResponse = new ResponseEntity<>(artistService.getListArtistSongsByArtistId(artistId), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("ArtistController - getListArtistSongByArtistId - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }
}
