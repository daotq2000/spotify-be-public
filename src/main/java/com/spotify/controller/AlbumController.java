package com.spotify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.AlbumResponseType;
import com.spotify.dto.response.AlbumSongResponseType;
import com.spotify.service.AlbumService;
import com.spotify.service.impl.FileStorageServiceImpl;
import com.spotify.ultils.GenericsHelper;
import com.spotify.ultils.LoggerFactory;
import com.spotify.ultils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/albums")
@CrossOrigin(origins = "*")
public class AlbumController {
    private final AlbumService albumService;
    private final FileStorageServiceImpl fileStorageService;
    Logger log = LoggerFactory.getLogger("Album");
    @Autowired
    public AlbumController(AlbumService albumService, FileStorageServiceImpl fileStorageService) {
        this.albumService = albumService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> paginationAlbums(@RequestBody PaginationRequest paginationRequest) throws JsonProcessingException {
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("AlbumController - paginationAlbums - start - request: ["+reqStartTime+"]");
        log.info("paginationRequest: "+ GenericsHelper.ObjectToJsonValue(paginationRequest));
        ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(albumService.paginationAlbum(paginationRequest), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("AlbumController - paginationAlbums - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "+GenericsHelper.ObjectToJsonValue(response));
        return response;
    }

    @PostMapping("/save")
    public ResponseEntity<AlbumResponseType> saveAlbum(@RequestParam("album") String albumJson,
                                                       @RequestParam("file") MultipartFile file) throws Exception {
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("AlbumController - saveAlbum - start - request: ["+reqStartTime+"]");
        log.info("albumJsonRequest: "+albumJson);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        AlbumResponseType albumResponseType = objectMapper.readValue(albumJson, AlbumResponseType.class);
        String fileSave = fileStorageService.uploadToCloudinary(file);
        albumResponseType.setImage(fileSave);
        albumResponseType.setReleaseDate(new Date());
        AlbumResponseType response = albumService.save(albumResponseType);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);
        log.info("AlbumController - saveAlbum - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(responseEntity));

        return responseEntity;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<AlbumResponseType> updateAlbum(@RequestParam("album") String albumJson,
                                                         @RequestParam(value = "file", required = false) MultipartFile file,
                                                         @PathVariable("id") Integer id) throws Exception {
        ResponseEntity<AlbumResponseType> pResponse = null;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("AlbumController - updateAlbum - start - request: ["+reqStartTime+"]");
        log.info("albumJsonRequest: "+albumJson);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        AlbumResponseType albumResponseType = objectMapper.readValue(albumJson, AlbumResponseType.class);
        albumResponseType.setId(id);
        if (null != file) {
            String fileSave = fileStorageService.uploadToCloudinary(file);
            albumResponseType.setImage(fileSave);
        }
        AlbumResponseType response = albumService.updateAlbum(albumResponseType, id);
        pResponse = new ResponseEntity<>(response, HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("AlbumController - updateAlbum - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAlbum(@PathVariable("id") int id) {
        boolean isDelete = albumService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteListAlbum(@RequestBody List<Integer> albumIds) {
        boolean isDelete = albumService.deleteListAlbumId(albumIds);
        if (isDelete) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseType> findById(@PathVariable("id") int id) throws JsonProcessingException {
        ResponseEntity<AlbumResponseType> pResponse = null;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("AlbumController - findById - start - request: ["+reqStartTime+"]");
        log.info("AlbumResponseType ID: "+id);
        AlbumResponseType albumResponseType = albumService.findById(id);
        pResponse = new ResponseEntity<>(albumResponseType, HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("AlbumController - findById - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @GetMapping("/albumSongs/{album}")
    public ResponseEntity<List<AlbumSongResponseType>> findAlbumSongByAlbumId(@PathVariable("album") int albumId) throws JsonProcessingException {
        ResponseEntity<List<AlbumSongResponseType>>  pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("AlbumController - findAlbumSongByAlbumId - start - request: ["+reqStartTime+"]");
        log.info("albumID: "+albumId);
        pResponse = new ResponseEntity<>(albumService.getListSongByAlbumId(albumId), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("AlbumController - findAlbumSongByAlbumId - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }
}
