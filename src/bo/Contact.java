package bo;

public class Contact {
	private int id;
	private String nom;
	private String prenom;
	private String telephone;
	private String mail;
	
	private static int cpt = 0;
	
	public Contact() {
		id = cpt++;
	}
	
	public Contact(String nom, String prenom, String telephone, String mail) {
		super();
		this.id = cpt++;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.mail = mail;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
