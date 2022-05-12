import java.util.Scanner;
 class main {

	public static String adresse = "";
	public static String bd = "";
	public static String login = "";
	public static String password = "";
	
	
	//requete de selection
	//entrée : requete -> une requete sql de type select
	//sortie : 0 si rien existe 1 sinon
	public static int requeteSelect(String requete) {
		int connexion = BD.ouvrirConnexion(adresse, bd, login,password);
		int res = BD.executerSelect(connexion, requete);
		
		BD.fermerResultat(res);
		BD.fermerConnexion(connexion);
		return res;
	}

	//requete d'insertion
	//entrée : requete -> une requete sql de type insert
	public static void requeteInsert(String requete) {
		int connexion = BD.ouvrirConnexion(adresse, bd, login,password);
		int res = BD.executerUpdate(connexion, requete);
		
		BD.fermerResultat(res);
		BD.fermerConnexion(connexion);
	}
	 
	//permet de saisir une date
	//sortie la date saisie
	public static Dates saisirDate(){
		Dates date=new Dates();
		
		Ecran.afficher("veuillez saisir l'année : ");
		date.year=Clavier.saisirInt(); 
				
		Ecran.afficher("veuillez saisir le mois : ");
		date.month=Clavier.saisirInt(); 
		while(date.month>12 && date.month<0){
			Ecran.afficher("veuillez saisir un mois valide : ");
			date.month=Clavier.saisirInt();
		}

		Ecran.afficher("veuillez saisir le jour : ");
		date.day=Clavier.saisirInt(); 
		while (date.jourExiste(date.year, date.month, date.day)==false){
			Ecran.afficher("veuillez saisir un jour valide : ");
			date.day=Clavier.saisirInt(); 
		}

		return date;
	}
	 

	//permet d'afficher le menu
	public static void menu(){
		char choix;
		Ecran.afficherln("Veuillez selectionner : \n");
		Ecran.afficherln("1. Inserer un client");
		Ecran.afficherln("2. Inserer une reservation");
		Ecran.afficherln("3. Editer une facture");
		Ecran.afficherln("4. Quitter\n");
		choix=Clavier.saisirChar();
		switch(choix){
			case '1':
				client();
			break;
			case '2':
				reservation();
			break;			
			case '3':
				facture();
			break;	
			case '4':
				Ecran.afficherln("Au revoir");
				System.exit(1);
			break;			
			default:
				Ecran.afficherln("Veuillez saisir un charactere valide !");
				menu();
			break;
		}
	}
	 
	
	//permet l'insertion d'un client
	public static void client(){
		Ecran.afficher("Veuillez saisir le nom :");
		String nom=Clavier.saisirString();
		
		Ecran.afficher("Veuillez saisir le prenom :");
		String prenom=Clavier.saisirString();
		
		Ecran.afficher("Veuillez saisir la ville :");
		String ville=Clavier.saisirString();
		
		Ecran.afficher("Veuillez saisir le code postal :");
		int cp=Clavier.saisirInt();
		
		Ecran.afficher("Veuillez saisir le numero de rue :");
		int numRue=Clavier.saisirInt();
		
		Ecran.afficher("Veuillez saisir le nom de la rue :");
		String nomRue=Clavier.saisirString();

		String req = "INSERT INTO CLIENT (NomClient, PrenomClient, VilleCl, CPCl, NumCl, RueCl) VALUES ("+nom+","+prenom+","+ville+","+cp+","+numRue+","+nomRue+");" ;
		requeteInsert(req);

		Ecran.afficher("Le client à bien été inseré\n\n");
		menu();
	}
	 
	
	//permet l'insertion d'une reservation
	public static void reservation(){
		Ecran.afficher("Veuillez saisir l'id du client :");
		int idClient=Clavier.saisirInt();
		
		Ecran.afficher("Veuillez saisir l'id de l'hotel:");
		int idHotel=Clavier.saisirInt();
						
		Ecran.afficher("Veuillez saisir le numero de la chambre :");
		int numChambre=Clavier.saisirInt();
		
		Dates date=new Dates();
		date=saisirDate();
		
		Ecran.afficher("Veuillez saisir si il a payé ou non (oui ou non) :");
		String paye=Clavier.saisirString();

		String preReq = "SELECT * FROM RESERVATION WHERE IDClient="+idClient+" AND IDHotel="+idHotel+" AND NumeroChambre="+numChambre+"AND Date="+date+"AND Paye="+paye+");";
		
		if(requeteSelect(preReq)==0){
			Ecran.afficher("La reservation existe deja, veuillez reessayer\n\n");
			reservation();
		}else{
			String req = "INSERT INTO RESERVATION (IDClient, IDHotel, NumeroChambre, Date, Paye) VALUES ("+idClient+","+idHotel+","+numChambre+","+date+","+paye+");" ;
			requeteInsert(req);
			Ecran.afficher("La reservation à bien été inseré\n\n");
			menu();
		}
	}



	 
	

	public static void facture(int idClient, int idHotel, int numChambre, Dates d){
		int connexion = BD.ouvrirConnexion(adresse, bd, login, password);

	    String sql = "SELECT NomClient, PrenomClient, NomHotel, Prix, Paye FROM RESERVATION, CLIENT, HOTEL, CHAMBRE WHERE RESERVATION.IDClient = CLIENT.IDClient AND RESERVATION.IDHotel = HOTEL.IDHotel AND HOTEL.IDHotel = CHAMBRE.IDHotel AND RESERVATION.NumeroChambre = CHAMBRE.NumeroChambre AND idClient ="+idClient+" AND idHotel ="+idHotel+" AND NumeroChambre ="+numChambre+" AND Date ="+d+" ;";

	    int res = BD.executerSelect(connexion,sql);

	    while (BD.suivant(res)) {
			String nomClient = BD.attributString(res, "NomClient");
			String prenomClient = BD.attributString(res, "PrenomClient");
			String nomHotel = BD.attributString(res, "NomHotel");
			int prix = BD.attributInt(res, "Prix");
			String paye = BD.attributString(res, "Paye");
	    }

	    System.out.println("Facture du "+d.toString()+"\nFait a l'hotel :"+nomHotel+". \n\nLe client "+prenomClient+" "+nomClient+" a reserve la chambre numero "+numChambre+" a ce jour.");

	    if(paye=="oui") System.out.println("\nPrix payé :"+prix);
	    else System.out.println("\nPrix restant a payer :"+prix+"Voulez vous regler maintenant ?(o/n)");

	    Scanner reader = new Scanner(System.in);
	    char input = reader.nextChar();
	    reader.close();

	    if (input == 'o') {
		paye = "oui";
		System.out.println("Payement accepte !");
	    }
	    else System.out.println("Erreur payement refuse");

	}


	
	//le main
	public static void main(String[] args) {
		menu();
	}	
	
}