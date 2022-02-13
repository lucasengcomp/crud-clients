package com.br.clients.services;

import com.br.clients.dto.ClientDTO;
import com.br.clients.entities.Client;
import com.br.clients.repositories.ClientRepostory;
import com.br.clients.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepostory repostory;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> client = repostory.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
        return new ClientDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> clients = repostory.findAll(pageRequest);
        return clients.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = handleObjectClient(dto);
        client = repostory.save(client);
        return new ClientDTO(client);
    }

    private Client handleObjectClient(ClientDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setBirthDate(dto.getBirthDate());
        client.setChildren(dto.getChildren());
        client.setCpf(dto.getCpf());
        client.setIncome(dto.getIncome());
        return client;
    }
}
