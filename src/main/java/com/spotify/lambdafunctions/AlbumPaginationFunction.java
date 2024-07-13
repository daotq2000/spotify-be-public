package com.spotify.lambdafunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
@AllArgsConstructor
@Service
//com.spotify.lambdafunctions.AlbumPaginationFunction::handleRequest
public class AlbumPaginationFunction implements RequestHandler<PaginationRequest, Map<String, Object>> {
    private final AlbumService albumService;

    @Override
    public Map<String, Object> handleRequest(PaginationRequest request, Context context) {
        return albumService.paginationAlbum(request);

    }
}
