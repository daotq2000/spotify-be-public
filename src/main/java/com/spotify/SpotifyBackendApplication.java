package com.spotify;

import com.spotify.lambdafunctions.AlbumPaginationFunction;
import com.spotify.ultils.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@RedisHash(value = "*", timeToLive = 300)
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableCaching
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class SpotifyBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpotifyBackendApplication.class, args);
    }

}
