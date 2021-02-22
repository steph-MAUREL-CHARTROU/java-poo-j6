package fr.diginamic.recensement.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import fr.diginamic.IntegrationRecensement;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/**
 * Recherche et affichage de la population d'une ville
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationVilleService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) {

		System.out.println("Quel est le nom de la ville recherchée ? ");
		String choix = scanner.nextLine();
		
		try {
			Connection connection = getConnection();
			// etape 3 // Création du statement ( pour faire des requetes sql)
			Statement statement  = connection.createStatement();
			ResultSet curseur = statement.executeQuery("select nom, prenom FROM abonne ORDER BY nom");
			while ( curseur.next()) {
				
				String nom = curseur.getString("nom");
				String prenom = curseur.getString("prenom");
				System.out.println(" abonne " + nom + " ," + prenom);
				
			}
			curseur.close(); // attention il faut toujours fermer un curseur ! on l'utilise une seule fois.
				
			
			
			System.out.println(connection.isClosed());
		} catch (SQLException e) {
			System.out.println("Impossible de se connecter à la base de données" + e.getMessage());

		}

	}
}

