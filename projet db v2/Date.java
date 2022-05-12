class Dates{
		
	int day;
	int month;
	int year;
	
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
}