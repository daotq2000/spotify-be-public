package com.spotify.service.impl;

import com.spotify.dto.response.HistoryListenResponseType;
import com.spotify.service.HistoryListenService;
import org.springframework.stereotype.Service;

@Service
public class HistoryListenServiceImpl implements HistoryListenService {
    @Override
    public HistoryListenResponseType save(HistoryListenResponseType historyListenResponseType) {
        return null;
    }

    @Override
    public HistoryListenResponseType update(HistoryListenResponseType historyListenResponseType) {
        return null;
    }

    @Override
    public HistoryListenResponseType findById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
