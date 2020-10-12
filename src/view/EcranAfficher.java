package view;

import java.util.List;

import bll.ContactManager;
import bo.Contact;

public class EcranAfficher {

	public void run() {
		ContactManager cm = new ContactManager();
		List<Contact> contacts = cm.getAll();
		for (Contact current : contacts) {
			System.out.println(cm.jolifie(current));
		}
		
		new ApplicationConsole().run();
	}

}
