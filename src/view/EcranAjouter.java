package view;

import bll.ContactManager;
import bo.Contact;
import util.ScannerUtil;

public class EcranAjouter {

	public void run() {
		displayMenu();
		
		ContactManager cm = new ContactManager();
		String nom, prenom, telephone, mail;
		boolean valid = false;
		
		do {
			System.out.println("Veuillez saisir le nom du contact");
			nom = ScannerUtil.getScanner().nextLine();
			
			System.out.println("Veuillez saisir le prenom du contact");
			prenom = ScannerUtil.getScanner().nextLine();
			
			System.out.println("Veuillez saisir le numero de t�l�phone du contact");
			telephone = ScannerUtil.getScanner().nextLine();
			
			System.out.println("Veuillez saisir l'adresse mail du contact");
			mail = ScannerUtil.getScanner().nextLine();
			
			valid = cm.isValid(nom, prenom, telephone, mail);
			if(!valid)
				System.err.println("Les informations rensignees ne sont pas valides. Veuillez reessayer.");
		} while (!valid);
		
		cm.insert(new Contact(nom, prenom, telephone, mail));
		new ApplicationConsole().run();
	}

	private void displayMenu() {
		System.out.println("*************************************");
		System.out.println("* Vous souhaitez ajouter un contact *");
		System.out.println("*************************************");
	}

}
