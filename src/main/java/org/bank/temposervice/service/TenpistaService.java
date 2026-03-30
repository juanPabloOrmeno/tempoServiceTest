package org.bank.temposervice.service;

import org.bank.temposervice.dto.response.TenpistaResponse;

import java.util.List;

public interface TenpistaService {

    List<TenpistaResponse> getAllTenpistas();

    TenpistaResponse getByName(String name);
}