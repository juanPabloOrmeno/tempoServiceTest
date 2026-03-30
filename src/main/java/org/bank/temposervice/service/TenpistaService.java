package org.bank.temposervice.service;

import org.bank.temposervice.dto.request.TenpistaRequest;
import org.bank.temposervice.dto.response.TenpistaResponse;

import java.util.List;

public interface TenpistaService {

    List<TenpistaResponse> getAllTenpistas();

    TenpistaResponse getByName(String name);

    TenpistaResponse create(TenpistaRequest request);
}