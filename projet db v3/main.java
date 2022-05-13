 import java.util.Scanner;
 class main {
		 
	public static String adresse = "sql11.freemysqlhosting.net";
	public static String bd ="sql11491980";
	public static String login = "sql11491980";
	public static String password = "akNmglgrFq";
	
	
	//requete de selection
	//entrée : requete -> une requete sql de type select
	//sortie : 0 si rien existe 1 sinon
	public static int requeteSelect(String requete) {
		int connexion = BD.ouvrirConnexion("sql11.freemysqlhosting.net", "sql11491980", "sql11491980","akNmglgrFq");
		int res = BD.executerSelect(connexion, requete);
		
		//BD.fermerResultat(res);
		BD.fermerConnexion(connexion);
		return res;
	}

	//requete d'insertion
	//entrée : requete -> une requete sql de type insert
	public static int requeteInsert(String requete) {
		int connexion = BD.ouvrirConnexion("sql11.freemysqlhosting.net", "sql11491980", "sql11491980","akNmglgrFq");
		int res = BD.executerUpdate(connexion, requete);
		
		//BD.fermerResultat(res);
		BD.fermerConnexion(connexion);
		return res;
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
		if(requeteInsert(req)!=-1){
			Ecran.afficher("Le client à bien été inseré\n\n");
		}else{
			Ecran.afficher("Il y a eu une erreure");
		}

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
		String test = Clavier.saisirString();
		boolean paye;
		while (test!="oui" && test!="non"){
			if (test=="oui"){
				paye=true;
			}else{
				paye=false;
			}
		}
		
		String preReq = "SELECT * FROM RESERVATION WHERE IDClient="+idClient+" AND IDHotel="+idHotel+" AND NumeroChambre="+numChambre+"AND Date="+date+"AND Paye="+paye+");";
		
		if(requeteSelect(preReq)==0){
			Ecran.afficher("La reservation existe deja, veuillez reessayer\n\n");
			reservation();
		}else{
			String req = "INSERT INTO RESERVATION (IDClient, IDHotel, NumeroChambre, Date, Paye) VALUES ("+idClient+","+idHotel+","+numChambre+","+date+","+paye+");" ;
			requeteInsert(req);
		if(requeteInsert(req)!=-1){
			Ecran.afficher("La reservation à bien été inseré\n\n");
		}else{
			Ecran.afficher("Il y a eu une erreure");
		}
			menu();
		}
	}



	public static void facture(){
		
		Ecran.afficher("Veuillez saisir l'id du client :");
		int idClient=Clavier.saisirInt();
		
		Ecran.afficher("Veuillez saisir l'id de l'hotel:");
		int idHotel=Clavier.saisirInt();
						
		Ecran.afficher("Veuillez saisir le numero de la chambre :");
		int numChambre=Clavier.saisirInt();
		
		Dates d=new Dates();
		d=saisirDate();
				
		int connexion = BD.ouvrirConnexion(adresse, bd, login, password);

		String nomClient;
		String prenomClient;
		String nomHotel;
		int prix;
		boolean paye;
		
		String sql = "SELECT NomClient, PrenomClient, NomHotel, Prix, Paye FROM RESERVATION, CLIENT, HOTEL, CHAMBRE WHERE RESERVATION.IDClient = CLIENT.IDClient AND RESERVATION.IDHotel = HOTEL.IDHotel AND HOTEL.IDHotel = CHAMBRE.IDHotel AND RESERVATION.NumeroChambre = CHAMBRE.NumeroChambre AND idClient ="+idClient+" AND idHotel ="+idHotel+" AND NumeroChambre ="+numChambre+" AND Date ="+d+" ;";

		int res = BD.executerSelect(connexion,sql);

		while (BD.suivant(res)) {
			nomClient = BD.attributString(res, "NomClient");
			prenomClient = BD.attributString(res, "PrenomClient");
			nomHotel = BD.attributString(res, "NomHotel");
			prix = BD.attributInt(res, "Prix");
			paye = BD.attributBoolean(res, "Paye");
		}

		System.out.println("Facture du "+d.toString()+"\nFait a l'hotel :"+nomHotel+". \n\nLe client "+prenomClient+" "+nomClient+" a reserve la chambre numero "+numChambre+" a ce jour.");

		if(paye) System.out.println("\nPrix payé : "+prix);
		else System.out.println("\nPrix restant a payer : "+prix+". Voulez vous regler maintenant ?(o/n)");

		char input = Clavier.saisirChar();

		if (input == 'o') {
			paye = true;
			requeteInsert("UPDATE RESERVATION SET Paye = true WHERE idClient ="+idClient+" AND idHotel ="+idHotel+" AND NumeroChambre ="+numChambre+" AND Date ="+d+" ;");
			System.out.println("Payement accepte\n");
		}else System.out.println("Erreur payement refuse\n");
		menu();
		}


	
	//le main
	public static void main(String[] args) {
		menu();
	}	
	
}