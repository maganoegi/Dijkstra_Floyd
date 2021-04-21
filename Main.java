import java.io.*;
import java.util.*;

public class Main {


	public static void main(String[] args) throws IOException {
		// permet de prendre les entrées pour le menu
		// soit du clavier, d'un fichier ou de la ligne de commande		    
		Scanner in;
	   	switch(args.length) {
			case 0:
				in = new Scanner(System.in);
				break;
			case 1:
				in = new Scanner(new File(args[0]));
				break;
			default:
				String source = args[0];
				for (int i=1;i<args.length;i++) source += " " + args[i];
				in = new Scanner(source);
			} 
				
		String filePath = System.getProperty("user.dir") + File.separator + "villes.xml";
		//lire le fichier villes.xml avec votre code
		System.err.println("Le fichier XML " + filePath + " a été chargé\n");

		MyXmlHandler fh = new MyXmlHandler(filePath, "");
		LinkedHashMap<String, City> cities = fh.extractCities();

		ConnectionMatrix cMatrix = new ConnectionMatrix(cities);
		
		fh.fillConnections(cMatrix);

		ConnectionMatrix floydSolved;
		ConnectionMatrix precedenceMatrix;
		String output;
		LinkedHashMap<String, String> precedenceDijkstra;
		LinkedHashMap<String, Integer> distDijkstra;

		int choix = 0;		
		do {
		   // les impressions du menu sont envoyées sur le canal d'erreur
		   // pour les différencier des sorties de l'application 
		   // lesquelles sont envoyées sur la sortie standard
			System.err.println("Choix  0: quitter");
			System.err.println("Choix  1: liste des villes");
			System.err.println("Choix  2: matrice des poids");
			System.err.println("Choix  3: liste des poids");			
			System.err.println("Choix  4: matrice des temps de parcours (Floyd)");
			System.err.println("Choix  5: matrice des précédences (Floyd)");
			System.err.println("Choix  6: temps de parcours entre deux villes (Floyd)");
			System.err.println("Choix  7: parcours entre deux villes (Floyd)");
			System.err.println("Choix  8: tableau des temps de parcours (Dijkstra)");
			System.err.println("Choix  9: tableau des précédences (Dijkstra)");
			System.err.println("Choix 10: temps de parcours entre deux villes (Dijkstra)");
			System.err.println("Choix 11: parcours entre deux villes (Dijkstra)");
			System.err.println("Choix 12: ajout d'une ville");
			System.err.println("Choix 13: ajout d'une liaison");
			System.err.println("Choix 14: suppression d'une ville");
			System.err.println("Choix 15: suppression d'une liaison");
			System.err.println("Choix 16: graphe connexe?");
         	System.err.println("Choix 17: sauver (format XML)");
         
			System.err.println("Entrez votre choix: ");
			choix = in.nextInt();
			String str1, str2, str3;
			switch(choix) {
			case 1:
				Set set = cities.entrySet();
				Iterator it = set.iterator();


				String outputString = "";
				int i = 0;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry)it.next();
					outputString += "[" + i  + ":" + me.getKey() + "] ";
					i++;
				}
				System.out.println(outputString.trim());
				break;

			case 2:
				System.out.println(cMatrix.toString());
				break;

			case 3:
				output = "";
				for (String origin : cities.keySet()) {
					output += origin;
					for (String destination : cities.keySet()) {

						Optional<Integer> v = cMatrix.queryDistanceBetweenIfExists(
												origin, 
												destination
												);
						
						if (v.isPresent() && v.get() != 0) {
							output += " [" + destination + ":" + v.get() + "]";
						}
					}
					output += "\n";
				}
				System.out.println(output);
				break;

			case 4:
				floydSolved = cMatrix.duplicate();
				precedenceMatrix = cMatrix.duplicateWithPrecedence();
				Algorithm.floydWarshall(floydSolved, precedenceMatrix);
				System.out.println(floydSolved.toString());
				break;

			case 5:
				floydSolved = cMatrix.duplicate();
				precedenceMatrix = cMatrix.duplicateWithPrecedence();
				Algorithm.floydWarshall(floydSolved, precedenceMatrix);
				System.out.println(precedenceMatrix.toString());
				break;

			case 6:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				System.err.println("Ville de destination:");
				str2 = in.next();
				System.err.print("Distance: ");
				// format de sortie -> à générer avec votre code
				floydSolved = cMatrix.duplicate();
				precedenceMatrix = cMatrix.duplicateWithPrecedence();
				Algorithm.floydWarshall(floydSolved, precedenceMatrix);
			   	Optional<Integer> d = floydSolved.queryDistanceBetweenIfExists(str1, str2);
				System.out.println((d.isPresent()) ? Integer.valueOf(d.get()) : "inf"); // valeur pour Geneve à Delemont
				break;	

			case 7:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				System.err.println("Ville de destination:");
				str2 = in.next();
				System.err.print("Parcours: ");

				floydSolved = cMatrix.duplicate();
				precedenceMatrix = cMatrix.duplicateWithPrecedence();
				Algorithm.floydWarshall(floydSolved, precedenceMatrix);
				String ancestors = Algorithm.backTrackPrecedencesFloyd(precedenceMatrix, str1, str2);
				// format de sortie -> à générer avec votre code
				System.out.println("[" + ancestors + "]");
				break;

			case 8:
			   System.err.println("Ville d'origine:");
				str1 = in.next();
				// format de sortie -> à générer avec votre code
				precedenceDijkstra = new LinkedHashMap<>();
				for (String city : cMatrix.cities().keySet()) {
					precedenceDijkstra.put(city, "inf");
				}

				distDijkstra = Algorithm.dijkstra(cMatrix, precedenceDijkstra, str1);
				output = "";
				for (String city : distDijkstra.keySet()) {
					output += "[" + city + ":" + distDijkstra.get(city) + "] ";
				}
				System.out.println(output.trim());
				break;

			case 9:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				// // format de sortie -> à générer avec votre code
				// System.out.println("[Geneve<-Lausanne] [Lausanne<-Neuchatel] [Neuchatel<-Delemont] [Delemont<-Bale] [Lausanne<-Berne] [Berne<-Lucerne] [Berne<-Zurich] [Zurich<-Schaffouse] [Zurich<-St.-Gall] [Zurich<-Coire] [Coire<-St.-Moritz] [Lucerne<-Bellinzone] [Sion<-Andermatt] [Lausanne<-Sion]"); // résultat pour Geneve
				// break;

				precedenceDijkstra = new LinkedHashMap<>();
				for (String city : cMatrix.cities().keySet()) {
					precedenceDijkstra.put(city, "inf");
				}

				distDijkstra = Algorithm.dijkstra(cMatrix, precedenceDijkstra, str1);
				output = "";
				for (String city : precedenceDijkstra.keySet()) {
					output += "[" + precedenceDijkstra.get(city) + "<-" + city + "] ";
				}
				System.out.println(output.trim());
				break;

			case 10:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				System.err.println("Ville de destination:");
				str2 = in.next();
				System.err.print("Distance: ");
				// format de sortie -> à générer avec votre code
				// imprimer "inf" à la place Integer.MAX_VALUE
				System.out.println(267); // résultat pour Bale à St.-Moritz
				break;	

			case 11:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				System.err.println("Ville de destination:");
				str2 = in.next();
				System.err.print("Parcours: ");
				// format de sortie -> à générer avec votre code
				System.out.println("[Bale:Zurich:Coire:St.-Moritz]"); // résultat pour Bale à St.-Moritz
				break;
			case 12: 
				System.err.println("Nom de la ville:");
				str1 = in.next();
				// mise à jour à faire avec votre code	
				break;
			case 13:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				System.err.println("Ville de destination:");
				str2 = in.next();
				System.err.println("Temps de parcours:");
				str3 = in.next();
				// mise à jour à faire avec votre code
				break;
			case 14:
				System.err.println("Nom de la ville:");
				str1 = in.next();
				// mise à jour à faire avec votre code
				break;
			case 15:
				System.err.println("Ville d'origine:");
				str1 = in.next();
				System.err.println("Ville de destination:");
				str2 = in.next();
				// mise à jour à faire avec votre code
				break;
			case 16:
			   // format de sortie -> à générer avec votre code
				System.out.println(true); // réponse true ou false
				break;				
			case 17:
				System.err.println("Nom du fichier XML:");
				str1 = in.next();
				// sauvegarde à faire avec votre code
				break;
			}
		} while (choix!=0);	
	}
}











