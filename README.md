# reservation-hotelieres-sql-java

Le but est de creer une bdd de reservation d'hotels 

Sujet 1 : reservations hotelieres
- HOTEL(IDHotel*, NomHotel, VilleH, CPH, NumRueH, RueH, categorie) : toutes les in-
formations sur un hotel ;
- CHAMBRE(IDHotel*, NumeroChambre*, Prix, NbPlaces) : une chambre possedant un cer-
tain numero dans une certain hotel ;
- CLIENT(IDClient*, NomClient, PrenomClient, VilleCl, CPCl, NumCl, RueCl) : infor-
mations sur le client ;
- RESERVATION(IDClient*, IDHotel*, NumeroChambre*, Date*, Paye) : reservations faites
par les clients a certaines dates, payees ou non.

Taches : 
1. Instancier quelques hˆotels et quelques chambres dans ces hˆotels.
2. Interface pour ins ́erer un client dans la base.
3. Interface pour ajouter une reservation.
4. Interface pour  ́editer une facture avec possibilite de la payer.



INFORMATIONS SUR LA BASE :

    Hotels :
        (ID : 1, Nom : 'California Hotel',       Ville : 'Besancon',                 CP : 25000, Numero : 36, 'rue des Potiers', cat : 3),
        (ID : 2, Nom : 'Antenne Hotel',          Ville : 'Besancon',                 CP : 25260, Numero : 2,  'rue des Amandes', cat : 2),
        (ID : 3, Nom : 'Maitre Kebabiste Hotel', Ville : 'Saint Maurice en Riviere', CP : 71620, Numero : 10, 'rue Fugace',      cat : 1),
        (ID : 4, Nom : 'California Hotel',       Ville : 'Besancon',                 CP : 25000, Numero : 12, 'rue du Chien',    cat : 4);

    Chambres :
        California (id 1), NumChambre : 23, Prix : 69.99,  Places : 2),
        California (id 1), NumChambre : 42, Prix : 124.99, Places : 2),
        California (id 1), NumChambre : 18, Prix : 39.99,  Places : 1),

        Antenne, NumChambre : 101, Prix : 99.99,  Places : 1),
        Antenne, NumChambre : 222, Prix : 222.22,  Places : 2),
        Antenne, NumChambre : 432, Prix : 349.99,  Places : 4),

        Maitre K, NumChambre : 1, Prix : 999.99,  Places : 1),
        Maitre K, NumChambre : 2, Prix : 10.0,  Places : 2),
        Maitre K, NumChambre : 3, Prix : 87379.02,  Places : 10),
        
        California (id 4), NumChambre : 1, Prix : 79.99,  Places : 2),
        

Autres info :

    Server: sql11.freemysqlhosting.net
    Name: sql11491980
    Username: sql11491980
    Password: akNmglgrFq
    Port number: 3306

    email free db: kuydulaknu@vusra.com
