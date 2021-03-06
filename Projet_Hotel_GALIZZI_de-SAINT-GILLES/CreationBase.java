public class CreationBase{
	public static String adresse = "sql11.freemysqlhosting.net";
	public static String bd =" 	sql11491980";
	public static String login = "sql11491980";
	public static String password = "akNmglgrFq";
	
	public static void main(String[] args) {
		
		int connexion = BD.ouvrirConnexion(adresse, bd, login, password);	
		
		String createHotel = "CREATE TABLE IF NOT EXISTS HOTEL (IDHotel INTEGER NOT NULL UNIQUE DEFAULT 0 , NomHotel CHAR(30) NOT NULL, VilleH CHAR(100) NOT NULL, CPH INTEGER(5) NOT NULL, NumRueH CHAR(10) NOT NULL, RueH CHAR (100) NOT NULL, categorie INTEGER NOT NULL, PRIMARY KEY (IDHotel)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";

		int tab1 = BD.executerUpdate(connexion, createHotel);	

		String createChambre = "CREATE TABLE IF NOT EXISTS CHAMBRE (IDHotel INTEGER NOT NULL DEFAULT 0, NumeroChambre INTEGER NOT NULL DEFAULT 1, Prix FLOAT NOT NULL, NbPlaces INTEGER NOT NULL, PRIMARY KEY (IDHotel, NumeroChambre)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		//CONSTRAINT Fk_Hotel_Chambre FOREIGN KEY (IDHotel) REFERENCES CHAMBRE(IDHotel)ON UPDATE CASCADE ON DELETE CASCADE
		
		int tab2 = BD.executerUpdate(connexion, createChambre);
		
		String createClient = "CREATE TABLE IF NOT EXISTS CLIENT(IDClient INTEGER NOT NULL UNIQUE AUTO_INCREMENT() , NomClient CHAR(50) NOT NULL, PrenomClient CHAR(50), VilleCl CHAR(100) NOT NULL, CPCl INTEGER(5) NOT NULL, NumCl CHAR(10) NOT NULL, RueCl CHAR (100) NOT NULL, PRIMARY KEY (IDClient)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		
		int tab3 = BD.executerUpdate(connexion, createClient);
		
		String createReservation = "CREATE TABLE IF NOT EXISTS RESERVATION(IDClient INTEGER NOT NULL DEFAULT 0, IDHotel INTEGER NOT NULL DEFAULT 0, NumeroChambre INTEGER NOT NULL DEFAULT 1, DateReservation DATE NOT NULL, Paye BOOLEAN DEFAULT FALSE, PRIMARY KEY (IDClient, IDHotel, NumeroChambre, DateReservation)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		//CONSTRAINT Fk_Reservation_Client FOREIGN KEY (IDClient)REFERENCES CLIENT(IDClient)ON UPDATE CASCADE ON DELETE CASCADE,
		//CONSTRAINT Fk_Reservation_Hotel FOREIGN KEY (IDHotel)REFERENCES HOTEL(IDHotel)ON UPDATE CASCADE ON DELETE CASCADE, 
		//CONSTRAINT Fk_Reservation_Chambre FOREIGN KEY (NumeroChambre)REFERENCES CHAMBRE(NumeroChambre)ON UPDATE CASCADE ON DELETE CASCADE

		int tab4 = BD.executerUpdate(connexion, createReservation);
		
		BD.fermerConnexion(connexion);
	}
}