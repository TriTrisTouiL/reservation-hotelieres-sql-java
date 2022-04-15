public class hotel {
	public static String adresse = "sql4.freemysqlhosting.net";
	public static String bd = "sql4485888";
	public static String login = "sql4485888";
	public static String password = "uKFeupzmWW";
	
	public static void main(String[] args) {
		int connexion = BD.ouvrirConnexion(adresse, bd, login,password);
		String sql = "requette sql";
		int res = BD.executerSelect(connexion, sql);

		BD.fermerResultat(res);
		BD.fermerConnexion(connexion);
	}
}