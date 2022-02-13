package com.br.clients.services;

import com.br.clients.dto.ClientDTO;
import com.br.clients.entities.Client;
import com.br.clients.repositories.ClientRepostory;
import com.br.clients.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepostory repostory;

    public ClientDTO findById(Long id) {
        Optional<Client> client = repostory.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
        return new ClientDTO(entity);
    }
}
