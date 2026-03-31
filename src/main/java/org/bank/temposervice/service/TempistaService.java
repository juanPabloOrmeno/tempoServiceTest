package org.bank.temposervice.service;

import org.bank.temposervice.dto.request.TempistaRequest;
import org.bank.temposervice.dto.response.TempistaResponse;

import java.util.List;

public interface TempistaService {

    List<TempistaResponse> getAllTempistas();

    TempistaResponse getByName(String name);

    TempistaResponse create(TempistaRequest request);

    void delete(Long id);
}