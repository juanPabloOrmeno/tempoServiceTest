package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TempistaRequest;
import org.bank.temposervice.dto.response.TempistaResponse;
import org.bank.temposervice.entity.Tempista;
import org.bank.temposervice.repository.TempistaRepository;
import org.bank.temposervice.service.TempistaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempistaServiceImpl implements TempistaService {

    private static final Logger log = LoggerFactory.getLogger(TempistaServiceImpl.class);

    private final TempistaRepository tempistaRepository;

    public TempistaServiceImpl(TempistaRepository tempistaRepository) {
        this.tempistaRepository = tempistaRepository;
    }

    @Override
    public List<TempistaResponse> getAllTempistas() {
        log.info("Getting all tempistas");

        return tempistaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TempistaResponse getByName(String name) {
        log.info("Getting tempista by name: {}", name);

        Tempista tempista = tempistaRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Tempista not found with name: " + name));

        return mapToResponse(tempista);
    }

    @Override
    public TempistaResponse create(TempistaRequest request) {
        log.info("Creating tempista with name: {}", request.name());

        Tempista tempista = new Tempista();
        tempista.setName(request.name());

        Tempista savedTempista = tempistaRepository.save(tempista);

        return mapToResponse(savedTempista);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting tempista with id: {}", id);

        if (!tempistaRepository.existsById(id)) {
            throw new RuntimeException("Tempista not found with id: " + id);
        }

        tempistaRepository.deleteById(id);
    }

    private TempistaResponse mapToResponse(Tempista tempista) {
        return new TempistaResponse(
                tempista.getId(),
                tempista.getName()
        );
    }
}