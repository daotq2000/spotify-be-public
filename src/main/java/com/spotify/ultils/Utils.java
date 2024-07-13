package com.spotify.ultils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Utils {
    public static String getUrlFilePath(String fileName) {
        return Constraints.PATH_DOWNLOAD + fileName;
    }
    public static String getUrlFilePathImage(String fileName){
        return Constraints.PATH_UPLOAD_IMAGE
                + fileName;
    }
    public static String getUrlFilePathMp3Source(String fileName){
        return Constraints.PATH_UPLOAD_MP3 + fileName;
    }
    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static String getTotalTimeMp3File(MultipartFile multipartFile) throws UnsupportedAudioFileException, IOException {
//        InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());
        File file = new File("download/PlayDate.mp3");
        System.out.println(file);
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (getFileExtension(multipartFile.getOriginalFilename()).equals("mp3")) {
            Map<?, ?> properties = (fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int mili = (int) (microseconds / 1000);
            int sec = (mili / 1000) % 60;
            int min = (mili / 1000) / 60;
            return min + ":" + sec;
        } else {
            throw new UnsupportedAudioFileException();
        }

    }
    public static String extractBody(HttpServletRequest request) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = request.getInputStream();
        while ((bytesRead = is.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        return bos.toString();
    }
}
