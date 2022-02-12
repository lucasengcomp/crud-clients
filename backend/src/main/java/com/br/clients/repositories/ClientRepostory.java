package com.br.clients.repositories;

import com.br.clients.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepostory extends JpaRepository<Client, Long> {
}
