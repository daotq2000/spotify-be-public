package com.spotify.dto.response;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryResponse {
    private String format;
    private String resource_type;
    private String bytes;
    private String width;
    private String secure_url;
    private String asset_id;
    private String type;
    private String version;
    private String url;
    private String public_id;
    private String height;
}
