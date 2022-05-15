//GALIZZI_Matéo_deSAINTGILLES_Tristan

class Dates{
		
	int day;
	int month;
	int year;
	
	//permet de savoir si le jour existe en fonction d'un jour d'un mois et d'une année
	//entrée : d -> un jour
	//m -> un mois
	//y -> une année
	//sortie : true si le jour existe faux sinon
	boolean jourExiste(int y,int m,int d){
		if(((y%4==0 && y%100!=0) || y%400==0) && m==2 && (d<0 || d>29)){
			return false;
		}
		if ((m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12) && (d<0 || d>31)){
			return false;
		}
		if ((m==4 || m==6 || m== 9 || m==11) && (d<0 || d>30)){
			return false;
		}
		if (m==2 && (d<0 || d>28)){
			return false;
		}
		return true;
	}

	//permet de saisir une date
	//sortie la date saisie
	void saisirDate(){
		
		Ecran.afficher("veuillez saisir l'année: ");
		this.year=Clavier.saisirInt(); 
				
		Ecran.afficher("veuillez saisir le mois: ");
		this.month=Clavier.saisirInt(); 
		while(this.month>12 && this.month<0){
			Ecran.afficher("veuillez saisir un mois valide: ");
			this.month=Clavier.saisirInt();
		}

		Ecran.afficher("veuillez saisir le jour: ");
		this.day=Clavier.saisirInt(); 
		while (this.jourExiste(this.year, this.month, this.day)==false){
			Ecran.afficher("veuillez saisir un jour valide: ");
			this.day=Clavier.saisirInt(); 
		}
	}

	//permet de transformer une date en String de la forme : yyyy-mm-dd
	String versChaine() {

		String dayS = ""+day;
		String monthS = ""+month;

		if (this.day<10) {dayS = "0"+this.day;}
		if (this.month <10) {monthS = "0"+this.month;}

		return year + "-" + monthS + "-" + dayS;
	}

}