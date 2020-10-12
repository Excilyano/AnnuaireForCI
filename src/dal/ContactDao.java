package dal;

import java.util.ArrayList;
import java.util.List;

import bo.Contact;

public class ContactDao {
	private List<Contact> contacts;
	
	public ContactDao() {
		contacts = new ArrayList<Contact>();
		contacts.add(new Contact("Cassin", "Etienne", "0658587290", "et.cassin@gmail.com"));
		contacts.add(new Contact("Bougard", "Quentin", "0600010203", "quentin.bougard@gmail.com"));
		contacts.add(new Contact("Gerard", "Francois", "0751263578", "f.gerard@gmail.com"));
	}

	public List<Contact> getAll() {
		return contacts;
	}

	public void insert(Contact a) {
		contacts.add(a);
	}
	
	public void delete(Contact a) {
		contacts.remove(a);
	}

	public boolean exist(String id) {
		return contacts.stream().anyMatch(x -> id.equals(String.valueOf(x.getId())));
	}

	public Contact find(int id) {
		return contacts.stream().filter(x -> x.getId() == id).findFirst().get();
	}
}
