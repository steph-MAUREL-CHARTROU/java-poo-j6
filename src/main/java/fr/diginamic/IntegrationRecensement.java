package fr.diginamic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;


public class IntegrationRecensement {

	// au chargement, je charge le Driver

	static {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Impossible de charger le Driver MySQL");
		}

	}

	// Création d'une classe Connection

	public static Connection getConnection() {
		try {

			ResourceBundle fichierConfig = ResourceBundle.getBundle("databdd");
			String url = fichierConfig.getString("databdd.url");
			String user = fichierConfig.getString("databdd.user");
			String password = fichierConfig.getString("databdd.passeword");
			Connection connection = DriverManager.getConnection(url, user, password);
			return connection;

		} catch (Exception e) {
			System.out.println("Impossible de se connecter à la base de données" + e.getMessage());
		}
		return null;

	}

	// Création de la classe pour insérer des lignes dans la base
	public void insertVille() throws IOException {

		try {
			Connection connection = getConnection();

			//Création du statement ( pour faire des requetes sql)
			Statement statement = connection.createStatement();
			
			// Récupération des infos de base di fichier recensement.csv
			Path pathBase= Paths.get("C:/Users/33782/Desktop/DiginamicWork/java/java-poo-j6/src/main/resources/recensement.csv");
			
			// Création d'une liste avec les infos de bases du fichier recensement CSV + attention à l'UTF_8
			List<String> lines= Files.readAllLines(pathBase, StandardCharsets.UTF_8);
			lines.remove(0); // j'enlève la première ligne qui correspond à l'entête
			
			for ( int i = 0; i<lines.size(); i++) {
				
				String[] tabVille = lines.get(i).split(";"); // Attention on retire le ;
				int codeCommune = Integer.parseInt(tabVille[5].replace(" ", ""));
				String nomCommune = tabVille[6];
				int popTotale = Integer.parseInt(tabVille[9].replace(" ", ""));
				
				// préparation du statement + requete SQL
				
				PreparedStatement prepStatement = connection.prepareStatement
						(" INSERT INTO ville ( nom_ville, code_ville, population )VALUES (?,?,?)");
				
				prepStatement.setString(1,nomCommune);
				prepStatement.setInt(2,codeCommune);
				prepStatement.setInt(3, popTotale);
				
				
				prepStatement.executeUpdate();
				
			}
			statement.close();// attention de bien fermer le statement
			connection.close();// pareil pour la connection
			
			
			
		} catch (SQLException e) {
			System.err.println("Impossible de se connecter à la base de données" + e.getMessage());

		}

	}
}
