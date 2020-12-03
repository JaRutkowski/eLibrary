package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lib_client")
@PrimaryKeyJoinColumn(name = "id_client", referencedColumnName = "id_user_data")
public class Client extends UserData {
}
