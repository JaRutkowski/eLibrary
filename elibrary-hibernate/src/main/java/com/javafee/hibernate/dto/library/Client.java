package com.javafee.hibernate.dto.library;

import com.javafee.hibernate.dto.common.UserData;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Client.checkIfClientLoginExist", query = "from Client where login = :login"),
		@NamedQuery(name = "Client.checkWithComparingIdIfClientLoginExist", query = "from Client where login = :login and id != :id")})
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
