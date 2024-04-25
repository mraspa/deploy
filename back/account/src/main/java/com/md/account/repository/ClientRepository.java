package com.md.account.repository;


import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByDocumentNumber(String documentNumber);

    @Query("SELECT NEW com.md.account.model.dto.AliasCBUDtoResponse(a.alias, a.CBU) FROM Client c JOIN c.account a WHERE c.documentNumber = :documentNumber")
    Optional<AliasCBUDtoResponse>  findAliasAndCBUByDocumentNumber(String documentNumber);
    @Query("SELECT NEW com.md.account.model.dto.ClientDtoResponse(c.name,c.lastName,c.cuil,a.accountNumber) FROM Client c JOIN c.account a WHERE a.CBU = :CBU")
    Optional<ClientDtoResponse> findClientDtoByCBU(String CBU);
    @Query("SELECT NEW com.md.account.model.dto.ClientDtoResponse(c.name,c.lastName,c.cuil,a.accountNumber) FROM Client c JOIN c.account a WHERE a.alias = :alias")
    Optional<ClientDtoResponse> findClientDtoByAlias(String alias);


}
