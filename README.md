# AIR Nomads
Ce répertoire contient notre projet de JEE, ses sources et ses documents.

AIR Nomads est une plateforme à destination des voyageurs
et des compagnies aériennes. Elle permet tout autant de
trouver le vol idéal que de gérer sa compagnie aérienne.

## Rapport
Le rapport présentant les changements depuis la soutenance
(il y en a beaucoup) est présent dans le dossier `Document`. 

https://github.com/ShaitanLyss/projetJEE/blob/main/Documents/Rapport%20JEE.pdf

## Utilisation
1) Télécharger le dossier `Release`
2) Modifier le fichier `.env` avec vos informations
3) Exécutez le jar avec java
   - `java -jar .\airportmadness-1.0.0.jar`
4) Vous pouvez accéder au site sur http://localhost:8080

Des utilisateurs précréés sont disponibles pour pouvoir 
expérimenter les différents aspects de l'application :
- airline, mot de passe : 1234
  - page `Compagnie aérienne`
- admin, mot de passe : 1234
  - page `Adminstration`

Sur `Voyager`, vous pourrez rechercher une destination puis
choisir un point de départ. La ligne aérienne idéale vous
sera alors proposée et il vous sera possible de créer un
compte client et de réserver un vol.

Sur `Réservations`, vous pourrez consulter vos réservations,
les modifier ou les supprimer.

Enfin, vous pouvez vous connecter ou vous déconnecter. En
fonction de votre rôle, la connexion vous redirigera vers
différentes pages.

## Compilation
Les sources sont disponibles dans le dossier `AirportMadness`.
Un autre README y est présent.