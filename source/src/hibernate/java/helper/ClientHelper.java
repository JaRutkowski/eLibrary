package helper;

import java.util.Optional;

import org.hibernate.Session;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Client;

public class ClientHelper {

	public static Optional<Client> findClientById(final int id){
		Optional<Client> client = Optional.empty();
		 try {
	          
			 Session session = HibernateUtil.getSessionFactory().openSession();
	            client =  Optional.ofNullable(session.get(Client.class, id));
	        } catch (Exception e) {
	        }
		 
		 return client;
	}
}
