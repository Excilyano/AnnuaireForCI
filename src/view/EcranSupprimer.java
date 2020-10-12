package view;

import bll.ContactManager;
import bo.Contact;
import util.ScannerUtil;

public class EcranSupprimer {

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
		
		System.out.println("Le contact suivant va être supprimé :");
		System.out.println(cm.jolifie(contact));
		System.out.println("Confirmer ? (o/n)");
		
		String choix = ScannerUtil.getScanner().nextLine();
		while (!"o".equalsIgnoreCase(choix) && !"n".equalsIgnoreCase(choix)) {
			System.err.println("Le choix saisi n'est pas reconnu. Veuillez réessayer");
			System.out.println("Le contact suivant va être supprimé :");
			System.out.println(cm.jolifie(contact));
			System.out.println("Confirmer ? (o/n)");
			choix = ScannerUtil.getScanner().nextLine();
		}
		
		if ("o".equalsIgnoreCase(choix)) {
			System.out.println("Suppression du contact");
			cm.delete(contact);
		} else {
			System.out.println("Opération annulée");
		}
		
		new ApplicationConsole().run();
	}

	private void displayMenu() {
		System.out.println("***************************************");
		System.out.println("* Vous souhaitez supprimer un contact *");
		System.out.println("***************************************");
	}

}
