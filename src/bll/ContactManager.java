package bll;

import java.util.List;
import java.util.regex.Pattern;

import bo.Contact;
import dal.ContactDao;

public class ContactManager {
	private ContactDao dao;
	private static ContactDao instance;
	
	public ContactManager() {
		if (instance == null) {
			dao = new ContactDao();
			instance = dao;
		} else {
			dao = instance;
		}
	}
	
	public ContactManager(ContactDao dao) {
		this.dao = dao;
	}

	public List<Contact> getAll() {
		return dao.getAll();
	}

	public void insert(Contact a) {
		dao.insert(a);
	}
	
	public void delete(Contact a) {
		dao.delete(a);
	}

	public String jolifie(Contact contact) {
		String result = "";
		result += contact.getId() + " | ";
		result += contact.getNom() + " | ";
		result += contact.getPrenom() + " | ";
		result += contact.getTelephone() + " | ";
		result += contact.getMail() + " | ";
		return result;
	}

	public boolean isValid(String nom, String prenom, String telephone, String mail) {
		return checkTelephone(telephone) && checkMail(mail);
	}

	public boolean checkMail(String mail) {
		Pattern pattern = Pattern.compile(".+@.+\\..+");
		return pattern.matcher(mail).matches();
	}

	
	public boolean checkTelephone(String telephone) {
		return telephone.length() == 10 && telephone.charAt(0) == '0';
	}

	public boolean exist(String id) {
		return dao.exist(id);
	}

	public Contact find(int id) {
		return dao.find(id);
	}
}
