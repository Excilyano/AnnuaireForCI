package view;

import bll.ContactManager;
import bo.Contact;
import util.ScannerUtil;

public class EcranMettreAJour {
	public void run() {
		displayMenu();
		
		ContactManager cm = new ContactManager();
		String id;
		
		do {
			System.out.println("Veuillez saisir l'identifiant du contact à modifier");
			id = ScannerUtil.getScanner().nextLine();
			if (!cm.exist(id)) System.err.println("Identifiant incorrect. Veuillez réessayer");
		} while(!cm.exist(id));
		Contact contact = cm.find(Integer.parseInt(id));
		
		System.out.println("Le contact suivant va être mis à jour :");
		System.out.println(cm.jolifie(contact));
		
		String nom, prenom, telephone, mail;
		boolean valid = false;
		
		do {
			System.out.println("Veuillez saisir le nom du contact");
			nom = ScannerUtil.getScanner().nextLine();
			
			System.out.println("Veuillez saisir le prénom du contact");
			prenom = ScannerUtil.getScanner().nextLine();
			
			System.out.println("Veuillez saisir le numéro de téléphone du contact");
			telephone = ScannerUtil.getScanner().nextLine();
			
			System.out.println("Veuillez saisir l'adresse mail du contact");
			mail = ScannerUtil.getScanner().nextLine();
			
			valid = cm.isValid(nom, prenom, telephone, mail);
			if(!valid)
				System.err.println("Les informations renseignées ne sont pas valides. Veuillez réessayer.");
		} while (!valid);
		
		contact.setNom(nom);
		contact.setPrenom(prenom);
		contact.setTelephone(telephone);
		contact.setMail(mail);
		
		new ApplicationConsole().run();
	}

	private void displayMenu() {
		System.out.println("**************************************");
		System.out.println("* Vous souhaitez modifier un contact *");
		System.out.println("**************************************");
	}
}
