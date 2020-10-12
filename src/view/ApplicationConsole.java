package view;

import util.ScannerUtil;

public class ApplicationConsole {
	public static void main(String[] args) {
		new ApplicationConsole().run();
	}

	public void run() {
		displayMenu();
		String choix = ScannerUtil.getScanner().nextLine();
		
		while (	   !"1".equals(choix)
				&& !"2".equals(choix)
				&& !"3".equals(choix)
				&& !"4".equals(choix)) {
			System.err.println("Votre choix [" + choix + "] est invalide. Veuillez recommencer.");
			choix = ScannerUtil.getScanner().nextLine();
		}
		
		switch (choix) {
		case "1" :
			new EcranAfficher().run();
			break;
		case "2" : 
			new EcranAjouter().run();
			break;
		case "3" : 
			new EcranMettreAJour().run();
			break;
		case "4" : 
			new EcranSupprimer().run();
			break;
		}
	}

	private void displayMenu() {
		System.out.println("****************************************");
		System.out.println("* Bienvenue sur votre annuaire         *");
		System.out.println("* Veuillez saisir l'action a realiser  *");
		System.out.println("*   1. Afficher mes contacts           *");
		System.out.println("*   2. Ajouter un contact              *");
		System.out.println("*   3. Modifier un contact             *");
		System.out.println("*   4. Supprimer un contact            *");
		System.out.println("****************************************");
	}
}
