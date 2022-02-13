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

import javax.persistence.EntityNotFoundException;
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
        Client client = new Client();
        objectsClient(dto, client);
        client = repostory.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client client = repostory.getOne(id);
            objectsClient(dto, client);
            client = repostory.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Entity not found for this id: " + id);
        }
    }

    private void objectsClient(ClientDTO dto, Client client) {
        client.setName(dto.getName());
        client.setBirthDate(dto.getBirthDate());
        client.setChildren(dto.getChildren());
        client.setCpf(dto.getCpf());
        client.setIncome(dto.getIncome());
    }
}
