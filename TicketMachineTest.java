package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}
	@Test
	//S3 on n’imprime pas leticket si le montant inséré est insuffisant
	void notPrintTickets() {
		machine.insertMoney(PRICE-1);

		assertFalse(machine.printTicket(), "Pas assez d'argent donc la machine ne doit pas imprimer");
	}

	@Test
	//S4 on imprime le ticket si le montant inséré est suffisant
	void printTicket() {
		machine.insertMoney(PRICE+1);

		assertTrue(machine.printTicket(), "la machine doit imprimer le ticket");
	}

	@Test
	//S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void majBalanceAfterPrintTicket() {
		machine.insertMoney(PRICE + 20);
		machine.printTicket();
		assertEquals(20, machine.getBalance() , "la balance n'est pas decrementé du prix du ticket");
	}
	@Test
	//S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void montantCollectPrintTicket() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(machine.getTotal(), PRICE, "la collecte ne se fait pas apres l'impression du ticket");

		machine.insertMoney(PRICE+20);
		
		assertEquals(machine.getTotal(), PRICE, "la collecte se fait avant l'impression du ticket");
	}

	@Test
	//S7 : refund()rendcorrectement la monnaie
	void verifeRefund() {
		machine.insertMoney(PRICE);
		assertEquals(machine.refund(), PRICE, "la machine ne rend pas correctemet la money");
	}

	@Test
	//S8 : refund()remet la balance à zéro
	void verifieRefundNull() {
		machine.insertMoney(PRICE);
		machine.refund();
		assertEquals(machine.getBalance(), 0, "la machine ne remet aps la balance a 0");
	}


	@Test
	//S9 : on ne peut pas insérerun montant négatif
	void insererMontantNegatif() {
  	Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
        machine.insertMoney(-50);
    });
	}

	@Test
	//S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void priceNegatif() {
		  Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
        TicketMachine machine1 = new TicketMachine(-50);
    });
	}
}
