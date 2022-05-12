import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Classe BD : encapsule des fonctions utilitaires pour travailler sur une base de données.
 */
public class BD {

    /** Chemin vers le connecteur MySQL (fichier jar) */
    public static String connecteur = "./mysql-connector-java.jar";

    /** Utilisation d'un entier pretexte pour lancer l'initialisation */
    private static int init = init();

    /** Gestion des connexions : mapping entre un identifiant (entier) et l'objet Connexion associé */
    static HashMap<Integer,Connection> connexions = new HashMap<Integer,Connection>();

    /** Gestion des resultats renvoyés par des requêtes SELECT : mapping entre un identifiant (entier) et l'objet ResultSet associé */
    static HashMap<Integer,ResultSet> resultats = new HashMap<Integer,ResultSet>();


    /**
     * Initialisation: chargement du connecteur Java <--> Mysql (.jar) dynamiquement
     * pour éviter de modifier le classpath à la compilation et à l'exécution.
     * @return 0 si tout s'est bien passé. -1 si une exception a eu lieu (ne devrait jamais arriver).
     */
    private static int init() {
        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        try{
            if (! (new File(connecteur)).exists()) {
                System.err.println("/!\\ Erreur, assurez-vous que le fichier " + connecteur + " existe.");
                System.exit(0);
            }
            URL url = new File(connecteur).toURI().toURL();
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class<?>[]{URL.class});
            addURL.setAccessible(true);
            addURL.invoke(systemClassLoader, new Object[]{url});
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }


    /*
     * ******** GESTION DES CONNEXIONS A LA BASE DE DONNEES ********
     */


    /**
     * Ouverture d'une connexion au serveur MySQL.
     * @param serveur L'adresse du serveur MySQL.
     * @param base La base de données.
     * @param login Le login utilisé pour se connecter.
     * @param password Le mot de passe utilisé pour se connecter.
     * @return -1 si une erreur s'est produite, ou une entier positif ou nul identifiant la connexion ouverte.
     */
    public static int ouvrirConnexion(String serveur, String base, String login, String password) {
        int r = -1;
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + serveur + "/" + base, login, password);
            do {
                r++;
            }
            while (connexions.containsKey(r));
            connexions.put(r,conn);

        } catch (Exception ex) {
            System.err.println("/!\\ Erreur dans l'ouverture de la connexion : " + ex.getMessage());
            ex.printStackTrace();
        }
        return r;
    }


    /**
     * Fermeture d'une connexion à la base de données.
     * @param conn L'identifiant de la connexion à fermer.
     */
    public static void fermerConnexion(int conn) {
        try {
            if (connexions.containsKey(conn)) {
                connexions.get(conn).close();
                connexions.remove(conn);
            }
        }
        catch (Exception ex) {
            System.err.println("/!\\ Erreur dans la fermeture de la connexion id=" + conn + ": " + ex.getMessage());
        }
    }


    /*
     * ******** EXECUTION DE REQUETES SQL ********
     */

    /**
     * Exécuter une requête de type SELECT (DQL) sur la base de données.
     * @param conn L'identifiant de la connexion à utiliser.
     * @param sql Le code SQL à exécuter sur la base.
     * @return -1 si l'exécution de la requête a échoué, ou un entier positif ou nul identifiant le Resultat de la requête.
     */
    public static int executerSelect(int conn, String sql) {
        Connection conObj = connexions.get(conn);
        int r = -1;
        if (conObj == null) {
            return r;
        }
        try {
            PreparedStatement stmt = conObj.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            do {
                r++;
            }
            while (resultats.containsKey(r));
            resultats.put(r,res);
        }
        catch (Exception e) {
            System.err.println("/!\\ Erreur lors de l'exécution de la requête \"" + sql + "\" : " + e.getMessage());
        }
        return r;
    }


    /**
     * Exécuter une requête de type INSERT, DELETE, UPDATE (DML) sur la base de données.
     * @param conn L'identifiant de la connexion à utiliser.
     * @param sql Le code SQL à exécuter sur la base.
     * @return -1 si l'exécution de la requête a échoué, ou un entier positif ou nul donnant le nombre d'enregistrements modifiés ou l'identifiant du nouvel enregistrement (si numéro auto).
     */
    public static int executerUpdate(int conn, String sql) {
        Connection conObj = connexions.get(conn);
        int r = -1;
        if (conObj == null) {
            return r;
        }
        try {
            PreparedStatement stmt = conObj.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            r = stmt.executeUpdate();
            ResultSet rr = stmt.getGeneratedKeys();
            if (rr != null && rr.next()) {
                r = rr.getInt(1);
                rr.close();
            }
        }
        catch (Exception e) {
            System.err.println("/!\\ Erreur lors de l'exécution de la requête \"" + sql + "\" : " + e.getMessage());
            e.printStackTrace();
        }
        return r;
    }


    /*
     * ******** GESTION DES RESULTATS ********
     */


    /**
     * Supprime le résultat passé en paramètre (libération de la mémoire).
     * @param result Le résultat à supprimer.
     */
    public static void fermerResultat(int result) {
        if (resultats.containsKey(result)) {
            try {
                resultats.get(result).close();
            }
            catch (Exception e) {
                System.err.println("/!\\ Erreur dans la fermeture du résultat id=" + result + " : " + e.getMessage());
            }
            resultats.remove(result);
        }
    }


    /**
     * Passe à l'enregistrement suivant pour un résultat donné.
     * @param result Le résultat à considérer.
     * @return true si l'enregistrement suivant est atteint, false en cas d'erreur.
     */
    public static boolean suivant(int result) {
        ResultSet res = resultats.get(result);
        if (res == null) {
            System.err.println("Le resultat " + result + " n'existe pas.");
            return false;
        }
        try {
            return res.next();
        }
        catch (Exception e) {
            System.err.println("/!\\ Erreur dans le passage à l'enregistrement suivant :" + e.getMessage());
        }
        return false;
    }


    /**
     * Réinitialise le résultat (avant le premier enregistrement).
     * @param result le résultat à réinitialiser.
     * @return true si l'opération a réussi, false sinon.
     */
    public static boolean reinitialiser(int result) {
        ResultSet res = resultats.get(result);
        if (res == null) {
            System.err.println("Le résultat " + result + " n'existe pas.");
            return false;
        }
        try {
            res.beforeFirst();
            return true;
        }
        catch (Exception e) {
            System.err.println("/!\\ Erreur dans le passage au premier enregistrement : " + e.getMessage());
        }
        return false;
    }


    /**
     * Renvoie la valeur de l'attribut passé en paramètre sous la forme d'une chaîne.
     * @param result Le résultat à considérer.
     * @param attribut L'attribut pour lequel on souhaite avoir la valeur.
     * @return la valeur de l'attribut sous forme d'une chaîne.
     */
    public static String attributString(int result, String attribut) {
        String res = (String) attribut(result, attribut, TYPE_STRING);
        return (res == null) ? "" : res;
    }

    /**
     * Renvoie la valeur de l'attribut passé en paramètre sous la forme d'un entier.
     * @param result Le résultat à considérer.
     * @param attribut L'attribut pour lequel on souhaite avoir la valeur.
     * @return la valeur de l'attribut sous forme d'un entier.
     */
    public static int attributInt(int result, String attribut) {
        Integer res = (Integer) attribut(result, attribut, TYPE_INT);
        return (res == null) ? 0 : res.intValue();
    }

    /**
     * Renvoie la valeur de l'attribut passé en paramètre sous la forme d'un entier long.
     * @param result Le résultat à considérer.
     * @param attribut L'attribut pour lequel on souhaite avoir la valeur.
     * @return la valeur de l'attribut sous forme d'un entier long.
     */
    public static long attributLong(int result, String attribut) {
        Long res = (Long) attribut(result, attribut, TYPE_LONG);
        return (res == null) ? 0 : res.longValue();
    }

    /**
     * Renvoie la valeur de l'attribut passé en paramètre sous la forme d'un réel float.
     * @param result Le résultat à considérer.
     * @param attribut L'attribut pour lequel on souhaite avoir la valeur.
     * @return la valeur de l'attribut sous forme d'un réel float.
     */
    public static float attributFloat(int result, String attribut) {
        Float res = (Float) attribut(result, attribut, TYPE_FLOAT);
        return (res == null) ? 0 : res.floatValue();
    }

    private static final int TYPE_STRING = 0;
    private static final int TYPE_LONG = 1;
    private static final int TYPE_INT = 2;
    private static final int TYPE_FLOAT = 3;

    /**
     * Fonction privée : renvoie la valeur d'un attribut donné au format demandé.
     * @param result Le résultat à considérer.
     * @param attribut L'attribut demandé.
     * @param type Le type de retour souhaité (TYPE_STRING, TYPE_INT, TYPE_LONG)
     * @return
     */
    private static Object attribut(int result, String attribut, int type) {
        ResultSet res = resultats.get(result);
        if (res == null) {
            System.err.println("Le resultat " + result + " n'existe pas.");
            return null;
        }
        try {
            switch (type) {
                case TYPE_STRING: return res.getString(attribut);
                case TYPE_LONG: return res.getLong(attribut);
                case TYPE_INT: return res.getInt(attribut);
                case TYPE_FLOAT: return res.getFloat(attribut);
            }
        }
        catch (Exception e) {
            System.err.println("/!\\ Erreur dans la récupération de la valeur de l'attribut \"" + attribut + "\" : " + e.getMessage());
        }
        return null;
    }


     /*
     * ******** GESTION DES DATES ********
     *
     * Ici, toutes les dates sont considérées comme étant des entiers long (suffisant pour faire des comparaisons simples).
     */

    /**
     * Envoie la date courante sous la forme d'un entier long.
     * @return la date courante sous la forme d'un entier long.
     */
    public static long maintenant() {
        return System.currentTimeMillis();
    }


    public static int jour(long date) {
        return DateParGenre(date, Calendar.DAY_OF_MONTH);
    }

    public static int mois(long date) {
        return DateParGenre(date, Calendar.MONTH);
    }

    public static int annee(long date) {
        return DateParGenre(date, Calendar.YEAR);
    }

    public static int heures(long date) {
        return DateParGenre(date, Calendar.HOUR_OF_DAY);
    }

    public static int minutes(long date) {
        return DateParGenre(date, Calendar.MINUTE);
    }

    public static int secondes(long date) {
        return DateParGenre(date, Calendar.SECOND);
    }

    private static int DateParGenre(long l, int kind) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(l));
        return c.get(kind);
    }


    public static long date(int jour, int mois, int annee, int heure, int min, int sec) {
        Calendar c = Calendar.getInstance();
        c.set(annee,mois,jour,heure,min,sec);
        return c.getTimeInMillis();
    }

    /* ** TIMING ** */

    public static void pause(int millis) {
        try {
            long l = millis;
            Thread.sleep(l);
        }
        catch (InterruptedException e) {
            // Ne devrait pas arriver.
        }
    }

}
