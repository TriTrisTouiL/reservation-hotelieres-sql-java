# reservation-hotelieres-sql-java

Le but est de créer une bdd de reservation d'hotels 

Sujet 1 : r ́eservations hˆoteli`eres
— HOTEL(IDHotel*, NomHotel, VilleH, CPH, NumRueH, RueH, categorie) : toutes les in-
formations sur un hˆotel ;
— CHAMBRE(IDHotel*, NumeroChambre*, Prix, NbPlaces) : une chambre poss ́edant un cer-
tain num ́ero dans une certain hˆotel ;
— CLIENT(IDClient*, NomClient, PrenomClient, VilleCl, CPCl, NumCl, RueCl) : infor-
mations sur le client ;
— RESERVATION(IDClient*, IDHotel*, NumeroChambre*, Date*, Paye) : r ́eservations faites
par les clients `a certaines dates, pay ́ees ou non.
1. Instancier quelques hˆotels et quelques chambres dans ces hˆotels.
2. Interface pour ins ́erer un client dans la base.
3. Interface pour ajouter une r ́eservation.
4. Interface pour  ́editer une facture avec possibilit ́e de la payer.
