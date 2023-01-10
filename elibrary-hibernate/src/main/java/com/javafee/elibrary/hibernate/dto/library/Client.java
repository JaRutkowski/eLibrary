package com.javafee.elibrary.hibernate.dto.library;



import com.javafee.elibrary.hibernate.dto.common.UserData;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@NamedQueries({
		@NamedQuery(name = "Client.checkWithComparingIdIfUserDataLoginExist", query = "from Client c join UserAccount ua on c.userAccount.idUserAccount = ua.idUserAccount where ua.login = :login and c.idUserData != :id"),
		@NamedQuery(name = "Client.checkIfUserDataLoginExist", query = "from Client c join UserAccount ua on c.userAccount.idUserAccount = ua.idUserAccount where ua.login = :login")})
@Table(name = "lib_client")
@PrimaryKeyJoinColumn(name = "id_client", referencedColumnName = "id_user_data")
public class Client extends UserData implements Cloneable {
	@Override
	public Object clone() {
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		if (getSurname() != null && getName() == null)
			result = getSurname();
		else if (getSurname() == null && getName() != null)
			result = getName();
		else if (getSurname() != null && getName() != null)
			result = getSurname() + " " + getName();
		return result;
	}
}
