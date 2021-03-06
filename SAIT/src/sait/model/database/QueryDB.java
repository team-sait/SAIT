package sait.model.database;

/*
 Uses Code by  Jdbc10.java Tutorial,
 Copyright 2004, R.G.Baldwin

*/
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class QueryDB {

	/*
	 * This Method grabs predefined tables from the database and dumps every row
	 */
	public static String[] queryDB(String requestType) {

		try {
			Statement stmt;
			ResultSet rs;

			//Use Linked List for Hints so we can later update list in runtime
			List<String> queryResult = new ArrayList<String>();


			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = "jdbc:mysql://chat.juckt-mich.net:3306/sait";

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "sait",
					"omfgklartextpw");

			// Get a Statement object
			stmt = con.createStatement();

			// Get another statement object initialized as shown.
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// Query the database, storing the result
			// in an object of type ResultSet

			rs = stmt.executeQuery("SELECT * "
					+ "from " + requestType + " ORDER BY id");

			switch (requestType) {
			case "hinweise":

				rs = stmt.executeQuery("SELECT * "
						+ "from " + requestType + " ORDER BY id");

				while (rs.next()) {
					String str = rs.getString("html_filename");
					queryResult.add(str);
				}

				break;
			case "hinweise_kategorien"	:

				rs = stmt.executeQuery("SELECT * "
						+ "from " + requestType + " ORDER BY id");

				while (rs.next()) {
					String str = rs.getString("Kategorie");
					queryResult.add(str);
				}
				break;
			case "Hersteller":

				rs = stmt.executeQuery("SELECT * "
						+ "from " + requestType + " ORDER BY id");

				while (rs.next()) {
					String str = rs.getString("Herstellername");
					queryResult.add(str);
				}
				break;
			case "Baugruppen":
				rs = stmt.executeQuery("SELECT * "
						+ "from " + requestType + " ORDER BY id");

				while (rs.next()) {
					String str = rs.getString("Baugruppe");
					queryResult.add(str);
				}
				break;

			default: System.out.println("Error, Will Robinson!");
			}

			//Transform List to Array: First create new array of list size, then safe list to array
			String[] returnQueryResult;
			returnQueryResult = new String[queryResult.size()];
			queryResult.toArray(returnQueryResult);


//			for(String s : HintFilenames){
//				System.out.println(s);
//				}


			con.close();

			return returnQueryResult;

		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
		return null;

	}// end main

	//Method to grab Dataset with specific ID
	public static String[] queryDBByID(String tableName, int ID) {

		try {
			Statement stmt;
			ResultSet rs;


			//Use Linked List for Hints so we can later update list in runtime
			List<String> queryResult = new ArrayList<String>();


			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = "jdbc:mysql://localhost:3306/sait";

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "sait",
					"omfgklartextpw");


			// Get a Statement object
			stmt = con.createStatement();

			// Get another statement object initialized as shown.
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// Query the database, storing the result
			// in an object of type ResultSet
			// rs = stmt.executeQuery("SELECT * "
			//		+ "from hinweise ORDER BY id");

			//rs = stmt.executeQuery("SELECT * "
					//+ "from " + tableName + " ORDER BY id");

			switch (tableName) {
			case "hinweise":

				rs = stmt.executeQuery("SELECT * "
						+ "from `" + tableName + "` WHERE `Kategorie_ID` =" + ID);

				while (rs.next()) {
					String str = rs.getString("html_filename");
					queryResult.add(str);
				}
				break;
			case "PKW":
				rs = stmt.executeQuery("SELECT * "
						+ "from `" + tableName + "` WHERE `Hersteller_ID` =" + ID);

				while (rs.next()) {
					String str = rs.getString("Modell") + "," + rs.getString("Baujahr") + "," +  rs.getString("Motorisierung") + "," +  rs.getString("Leistung") + "," +  rs.getString("Motorkennbuchstaben");
					queryResult.add(str);
				}
				break;

			default: System.out.println("Table not in database.");
				return null;
			}


			//Transform List to Array: First create new array of list size, then safe list to array
			String[] returnQueryResult;
			returnQueryResult = new String[queryResult.size()];
			queryResult.toArray(returnQueryResult);

			con.close();

			return returnQueryResult;

		} catch (Exception e) {
			System.out.println("Query: SELECT * "
					+ "from `" + tableName + "` WHERE `Kategorie_ID` =" + ID);
			e.printStackTrace();
		}// end catch
		return null;

	}// end main

	public static int GetCategoryIDByCategory(String tableName, String CategoryName) {
		// TODO Auto-generated method stub

		try {
			Statement stmt;
			ResultSet rs;
			int CategoryID = 1;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = "jdbc:mysql://localhost:3306/sait";

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "sait",
					"omfgklartextpw");


			// Get a Statement object
			stmt = con.createStatement();

			// Get another statement object initialized as shown.
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// Query the database
			switch (tableName) {
			case "hinweise_kategorien":

				rs = stmt.executeQuery("SELECT * "
						+ "from hinweise_kategorien WHERE Kategorie = '"
						+ CategoryName + "' ORDER BY id");
				if (rs.next()) {

					CategoryID = rs.getInt("ID");

				} else {
					System.out
							.println("Warning: Category number SQL Result set empty.");
					CategoryID = 1;
				}
				break;
			case "PKW":
				rs = stmt.executeQuery("SELECT * "
						+ "from PKW WHERE Hersteller_ID = '"
						+ CategoryName + "' ORDER BY id");
				if (rs.next()) {

					CategoryID = rs.getInt("Hersteller_ID");

				} else {
		//			System.out
			//				.println("Warning: PKW number SQL Result set empty.");

					CategoryID = 14;
				}
				break;
			default:
				System.out.println("Wrong Request Type " + tableName + " .");
			}

			con.close();

			return CategoryID;

		} catch (Exception e) {
			System.out.println("Query: SELECT * "
					+ "from hinweise_kategorien WHERE Kategorie = '" + CategoryName + "' ORDER BY id");
			e.printStackTrace();
		}// end catch
		return 1;
	}

	public static String GetPartListHTML(String partGroupName) {
		// TODO Auto-generated method stub

		try {
			Statement stmt;
			ResultSet rs;

			String ersatzteil;
			String Beschreibung;
			String BildType;
			String PartsListHTMLString = " ";

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = "jdbc:mysql://localhost:3306/sait";

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "sait",
					"omfgklartextpw");


			// Get a Statement object
			stmt = con.createStatement();

			// Get another statement object initialized as shown.
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);


				//Grab current part group parts
				rs = stmt.executeQuery("SELECT * from " + partGroupName);
				// ID 	Ersatzteil 	Beschreibung 	Bild_Data 	Bild_Type
				while (rs.next()) {
					String str = "<b>" + rs.getString("Ersatzteil") + "</b>" + ": " + "<img src=\"http://chat.juckt-mich.net/SAIT/BILDER/" + URLEncoder.encode(rs.getString("Bild_Type").toString(), "ISO-8859-1").replace("+", "%20") + "\" height=\"128\" width=\"128\">" + "</br>\n";
					PartsListHTMLString += str ;

				}

			con.close();
			System.out.println(PartsListHTMLString);
			return PartsListHTMLString;

		} catch (Exception e) {
			//System.out.println("Query: SELECT * "
			//		+ "from hinweise_kategorien WHERE Kategorie = '" + CategoryName + "' ORDER BY id");
			e.printStackTrace();
		}// end catch
		return "Error in GetPartsListHTML";
	}

}// end class