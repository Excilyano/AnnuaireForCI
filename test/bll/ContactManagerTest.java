package bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import bo.Contact;
import dal.ContactDao;

@RunWith(MockitoJUnitRunner.class)
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
		// Pour les tests de dao, je vais créer un manager en lui donnant un dao que j'ai renseigné moi-même
		// De cette manière, le résultat de mes tests sera toujours le même, peu importe ce qu'il y a en base
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
	 * TUs de la méthode jolifie
	 * Doit échouer : la méthode d'affichage affiche un | de trop à la fin
	 */
	@Test
	void testJolifie_nominal() {
		assertEquals("0 | Dupont | Jack | 0240367494 | jack.dupont@gmail.com", cm.jolifie(contact1));
	}
	
	/*
	 * TUs de la méthode checkMail
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
	 * TUs de la méthode checkTelephone avec numéro en 0
	 */
	@Test
	void testCheckTelephone_nominal_en0() {
		// Une chaine de 10 caracteres commencant par un 0 doit marcher
		assertTrue(cm.checkTelephone("0000000000"));
		assertTrue(cm.checkTelephone("0123456789"));
		
		// Une chaine de 9 ou 11 caracteres doit echouer
		assertFalse(cm.checkTelephone("00000000000"));
		assertFalse(cm.checkTelephone("000000000"));
		
		// Une chaine de 10 caracteres commençant pas autre chose que 0 doit echouer
		assertFalse(cm.checkTelephone("1234567890"));
	}
	
	/*
	 * TUs de la méthode checkTelephone avec numéro en +XX
	 * Doit échouer : le cas des +XX n'est pas couvert dans l'application
	 */
	@Test
	void testCheckTelephone_nominal_enxx() {
		// Une chaine de 12 caracteres commencant par un +33 doit marcher
		assertTrue(cm.checkTelephone("+33000000000"));
		assertTrue(cm.checkTelephone("+33123456789"));
		
		// Une chaine de 11 ou 13 caracteres doit echouer
		assertFalse(cm.checkTelephone("+330000000000"));
		assertFalse(cm.checkTelephone("+3300000000"));
		
		// Une chaine de 12 caractères commencant par autre chose qu'un + doit echouer
		assertFalse(cm.checkTelephone("012345678901"));
	}
	
	/*
	 * TIs de la méthode isValid
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
	 * TUs de la méthode isValid
	 * Meme scenarios que pour la version non mockée
	 */
	@Test
	void testIsValid_nominal_mocke() {
		cm = mock(ContactManager.class);
		doCallRealMethod().when(cm).isValid(anyString(), anyString(), anyString(), anyString());
		when(cm.checkMail(anyString())).thenReturn(true);
		when(cm.checkTelephone(anyString())).thenReturn(true);
		
		assertTrue(cm.isValid("", "", "0123456789", "mail@gmail.com"));
		
		when(cm.checkMail(anyString())).thenReturn(false);
		when(cm.checkTelephone(anyString())).thenReturn(true);
		assertFalse(cm.isValid("", "", "", "mail@gmail.com"));
		
		when(cm.checkMail(anyString())).thenReturn(true);
		when(cm.checkTelephone(anyString())).thenReturn(false);
		assertFalse(cm.isValid("", "", "0123456789", "gmail.com"));
		
		when(cm.checkMail(anyString())).thenReturn(false);
		when(cm.checkTelephone(anyString())).thenReturn(false);
		assertFalse(cm.isValid("", "", "012345678", "mail@gmail"));
		
		/*
		 * Pourquoi ne pas avoir écrit :
		 *   when(cm.checkMail(anyString())).thenReturn(true, true, false, false);
		 *   when(cm.checkTelephone(anyString())).thenReturn(true, false, true, false);
		 * ?
		 * C'est parce que Java ne consulte pas le deuxième membre d'un "&&" si le premier est false
		 * C'est le "lazy interpretation" de Java : si j'ai "false & quelque chose", le resultat sera false dans tous les cas, donc je ne joue meme pas le "quelque chose"
		 * Du coup, les résultats mockés de checkMail et checkTelephone sont décalés
		 * 
		 * Pour éviter ça, avant chaque appel à isValid, je redéfini le résultat attendu pour checkMail et checkTelephone
		 */
	}
	
	/*
	 * 
	 * TIs des méthodes appelant le dao
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
