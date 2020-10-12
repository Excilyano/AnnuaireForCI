package bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.Contact;
import dal.ContactDao;

class ContactManagerTest {
	private ContactManager cm;
	
	private ContactDao dao;
	private ContactManager cmPourTestDao;
	
	private Contact contact1;
	private Contact contact2;
	
	@BeforeEach
	void setUp() {
		cm = new ContactManager();
		
		initialiserCmPourTestDao();
	}
	
	private void initialiserCmPourTestDao() {
		// Pour les tests de dao, je vais creer un manager en lui donnant un dao que j'ai renseigne moi-meme
		// De cette maniere, le resultat de mes tests sera toujours le meme, peu importe ce qu'il y a en base
		contact1 = new Contact("Dupont", "Jack", "0240367494", "jack.dupont@gmail.com");
		contact1.setId(0);
		contact2 = new Contact("Druker", "Michel", "0202020202", "michel.druker@orange.fr");
		contact2.setId(1);
		
		dao = new ContactDao();
		dao.getAll().clear();
		dao.getAll().add(contact1);
		dao.getAll().add(contact2);
		
		cmPourTestDao = new ContactManager(dao);
	}
	
	/*
	 * TUs de la methode jolifie
	 * Doit echouer : la methode d'affichage affiche un | de trop � la fin
	 */
	@Test
	void testJolifie_nominal() {
		assertEquals("0 | Dupont | Jack | 0240367494 | jack.dupont@gmail.com", cm.jolifie(contact1));
	}
	
	/*
	 * TUs de la methode checkMail
	 */
	@Test
	void testCheckMail_nominal() {
		assertTrue(cm.checkMail("test@test.com"));
		assertTrue(cm.checkMail("a@b.c"));
		assertFalse(cm.checkMail(""));
		assertFalse(cm.checkMail("@gmail.com"));
		assertFalse(cm.checkMail("test.com"));
		assertFalse(cm.checkMail("test@gmail"));
		assertThrows(NullPointerException.class, () -> {
			cm.checkMail(null);
		});
	}
	
	/*
	 * TUs de la methode checkTelephone avec numero en 0
	 */
	@Test
	void testCheckTelephone_nominal_en0() {
		// Une chaine de 10 caracteres commencant par un 0 doit marcher
		assertTrue(cm.checkTelephone("0000000000"));
		assertTrue(cm.checkTelephone("0123456789"));
		
		// Une chaine de 9 ou 11 caracteres doit echouer
		assertFalse(cm.checkTelephone("00000000000"));
		assertFalse(cm.checkTelephone("000000000"));
		
		// Une chaine de 10 caracteres commencant pas autre chose que 0 doit echouer
		assertFalse(cm.checkTelephone("1234567890"));
	}
	
	/*
	 * TUs de la methode checkTelephone avec numero en +XX
	 * Doit echouer : le cas des +XX n'est pas couvert dans l'application
	 */
	@Test
	void testCheckTelephone_nominal_enxx() {
		// Une chaine de 12 caracteres commencant par un +33 doit marcher
		assertTrue(cm.checkTelephone("+33000000000"));
		assertTrue(cm.checkTelephone("+33123456789"));
		
		// Une chaine de 11 ou 13 caracteres doit echouer
		assertFalse(cm.checkTelephone("+330000000000"));
		assertFalse(cm.checkTelephone("+3300000000"));
		
		// Une chaine de 12 caracteres commencant par autre chose qu'un + doit echouer
		assertFalse(cm.checkTelephone("012345678901"));
	}
	
	/*
	 * TIs de la methode isValid
	 * 4 cas : 
	 *   - telephone ok, mail ok --> ok
	 *   - telephone ko, mail ok --> ko
	 *   - telephone ok, mail ko --> ko
	 *   - telephone ko, mail ko --> ko
	 * le nom et le prenom ne font pas l'objet de test supplementaires
	 * puisqu'aucune verification ne doit etre faite dessus
	 */
	@Test
	void testIsValid_nominal() {
		assertTrue(cm.isValid(null, null, "+33123456789", "mail@gmail.com"));
		assertFalse(cm.isValid(null, null, "", "mail@gmail.com"));
		assertFalse(cm.isValid(null, null, "0123456789", "gmail.com"));
		assertFalse(cm.isValid(null, null, "012345678", "mail@gmail"));
	}
	
	/*
	 * 
	 * TIs des methodes appelant le dao
	 * 
	 */
	@Test
	void testExist() {
		assertTrue(cmPourTestDao.exist("0"));
		assertFalse(cmPourTestDao.exist("-1"));
		assertFalse(cmPourTestDao.exist("3"));
	}
	
	@Test
	void testFind() {
		assertEquals(contact2, cmPourTestDao.find(1));
		assertThrows(NoSuchElementException.class, () -> {
			cmPourTestDao.find(15);
		});
	}
	
	@Test
	void testGetAll() {
		List<Contact> contacts = cmPourTestDao.getAll();
		assertEquals(2, contacts.size());
		assertEquals(contact1, contacts.get(0));
		assertEquals(contact2, contacts.get(1));
	}
	
	@Test
	void testInsert() {
		int nbContactInitial = dao.getAll().size();
		Contact newContact = new Contact("Berger", "Philippe", "0111111111", "p@b.com");
		cmPourTestDao.insert(newContact);
		assertEquals(1, dao.getAll().size() - nbContactInitial);
		assertEquals(newContact, dao.getAll().get(nbContactInitial));
	}
	
	@Test
	void testDelete() {
		int nbContactInitial = dao.getAll().size();
		cmPourTestDao.delete(contact2);
		assertEquals(1, nbContactInitial - dao.getAll().size());
		assertEquals(contact1, dao.getAll().get(0));
	}
}
