
# Gestion Agricole – Java + Swing + MySQL

## Prérequis
- JDK 17
- MySQL 8+ (utilisateur ayant droits sur la base)
- Créez la base et les tables : `mysql -u root -p < schema_mysql.sql`

## Configuration
- Éditez `src/main/resources/db.properties` (user, password, host/port).

## Lancer l'app
```bash
mvn -q clean package
mvn -q exec:java -Dexec.mainClass=com.agriapp.ui.MainFrame
```
> Ou lancez `MainFrame` depuis votre IDE.

## Modules
- **model** : entités Java
- **dao** : accès MySQL (CRUD, JDBC)
- **service** : logique métier (validation, calculs)
- **ui** : interface Swing (onglets : Cultures, Travaux, Intrants, Finances, Récoltes)
- **util** : utilitaires (connexion DB, helpers)

## Notes
- Chaque onglet permet : lister, ajouter, supprimer, (modifier simple).
- Les suppressions de `culture` suppriment aussi ses `travaux`/`récoltes` (FK CASCADE).
- 
pour realiser mon projet j'ai eu recours l'intelligence artificiel car mes connaissance en Java sont limités
quelques tutoriel sur youtube mon aussi aidé a mettre en place mon projet 