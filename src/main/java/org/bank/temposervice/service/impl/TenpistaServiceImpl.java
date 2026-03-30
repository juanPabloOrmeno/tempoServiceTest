package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TenpistaRequest;
import org.bank.temposervice.dto.response.TenpistaResponse;
import org.bank.temposervice.entity.Tenpista;
import org.bank.temposervice.repository.TenpistaRepository;
import org.bank.temposervice.service.TenpistaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenpistaServiceImpl implements TenpistaService {

    private static final Logger log = LoggerFactory.getLogger(TenpistaServiceImpl.class);

    private final TenpistaRepository tenpistaRepository;

    public TenpistaServiceImpl(TenpistaRepository tenpistaRepository) {
        this.tenpistaRepository = tenpistaRepository;
    }

    @Override
    public List<TenpistaResponse> getAllTenpistas() {
        log.info("Getting all tenpistas");

        return tenpistaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TenpistaResponse getByName(String name) {
        log.info("Getting tenpista by name: {}", name);

        Tenpista tenpista = tenpistaRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Tenpista not found with name: " + name));

        return mapToResponse(tenpista);
    }

    @Override
    public TenpistaResponse create(TenpistaRequest request) {
        log.info("Creating tenpista with name: {}", request.name());

        Tenpista tenpista = new Tenpista();
        tenpista.setName(request.name());

        Tenpista savedTenpista = tenpistaRepository.save(tenpista);

        return mapToResponse(savedTenpista);
    }

    private TenpistaResponse mapToResponse(Tenpista tenpista) {
        return new TenpistaResponse(
                tenpista.getId(),
                tenpista.getName()
        );
    }
}