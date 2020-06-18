

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;



/**
 * @author Alexandra Petre 325CB
 *
 */
public class Tema2 {

	/**
	 * @param argv
	 * @throws FileNotFoundException
	 */
	public static void main(String[] argv) throws FileNotFoundException {
		
		File inFile = new File(argv[0]); // fisier de intrare
		String numeFisier = argv[0];
		String numeFisierOut =  numeFisier + "_out";
		PrintWriter writer = new PrintWriter(numeFisierOut); // fisier de iesire
		Scanner sc = null;
		String line = new String();
		int max_capacity;
		int nr_nodes;
		int index = 1;
		ArrayList<Entitate> entitati_create = new ArrayList<Entitate>();
		ArrayList<Nod> nodes = new ArrayList<Nod>();
		ArrayList<Entitate> vector_entitati =  new ArrayList<Entitate>();
		
		try {
			sc = new Scanner(inFile);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			String[] parts = line.split(" ");
			
			if(parts[0].equals("CREATEDB")) {
				nr_nodes = Integer.parseInt(parts[2]);           // pastreaza numarul de noduri
				max_capacity =  Integer.parseInt(parts[3]);		//	capacitatea maxima din noduri		
				
				for(int i = 0; i < nr_nodes; i++) {
					Nod nod1 =  new Nod(max_capacity, index);      //se creaza nodurile
					index++;
					nodes.add(nod1);	
				}
			}
			
			/*
			 *  se creaza vectorul de entitati
			 *  fiecare atribut al entiatilor stocheaza tipul de date
			 */
			if(parts[0].equals("CREATE")) {
				
				int rf = Integer.parseInt(parts[2]);
				Entitate ent1 = new Entitate(parts[1] , rf);
				int count = Integer.parseInt(parts[3]);
				
				for(int i = 0; i < count; i++) {
					
					Obiecte ob = new Obiecte();
					ob.nume = parts[4 + i*2];
					
					if(parts[5+ i*2].equals("String")) {
						ob.tip_element = "String";
					}
					
					if(parts[5+ i*2].equals("Integer")) {
						ob.tip_element = "Integer";
					}
					
					if(parts[5+ i*2].equals("Float")) {
						ob.tip_element = "Float";
					}
					ent1.obj.add(ob);
				}
				
				entitati_create.add(ent1);
			}
			/*
			 * se citesc atributele si numele atributelor si se insereaza in fiecare nod entitatile
			 */
			if(parts[0].equals("INSERT")) {
				
				for(int i = 0; i < entitati_create.size(); i++) {
					
					if(parts[1].equals(entitati_create.get(i).nume)) {
						Entitate ent2 = new Entitate(entitati_create.get(i).nume, entitati_create.get(i).rf);
						ent2.cheie_primara = parts[2];
						
						for(int j = 0; j < entitati_create.get(i).obj.size(); j++) {
							Obiecte ob = new Obiecte();
							ob.nume = entitati_create.get(i).obj.get(j).nume;
							
							if(entitati_create.get(i).obj.get(j).tip_element.equals("String")) {
								ob.tip_element = "String";
								ob.stg = parts[2 + j];
							}
							if(entitati_create.get(i).obj.get(j).tip_element.equals("Integer")) {
								ob.tip_element = "Integer";
								ob.intg = Integer.parseInt(parts[2 + j]);	
							}
							if(entitati_create.get(i).obj.get(j).tip_element.equals("Float")) {
								ob.tip_element = "Float";
								ob.flt = Float.parseFloat(parts[2 + j]);
							}
							ent2.obj.add(ob);
						}
						vector_entitati.add(ent2);
						int count = 0;
						for(int k = 0;  k < nodes.size(); k++) {
							if(nodes.get(k).vect_entitati.size() < nodes.get(k).MaxCapacity) {
								if(count == ent2.rf) {
									break;
								}
								nodes.get(k).vect_entitati.add(0,ent2);
								count++;
							}
						}
					}
				}	
			}
			/*
			 * se afiseaza elementele din baza de date 
			 * se parcurg toate nodurile
			 * daca nu sunt elemente in nod se trece la urmatorl nod
			 * si se afiseaza fiecare entitate si atributele ei din fiecare nod
			 * cu ajutorul functiei afis din clasa entitate
			 */
			if(parts[0].equals("SNAPSHOTDB")) {
				int k = 0;
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i).vect_entitati.size() > 0) {
						k++;
						writer.print("Nod" + nodes.get(i).id_nod + "\n");
					}
					for(int j = 0; j < nodes.get(i).vect_entitati.size(); j++) {
						nodes.get(i).vect_entitati.get(j).afis(writer);
					}
				}
				if(k == 0) {
					writer.print("EMPTY DB" + "\n"); 			// daca baza de date este goala
				}
			}
			
			/*
			 * se parcurg toate nodurile
			 * se cauta elementul dupa cheia primara si se elimina elementul din lista de entitati
			 */
			
			if(parts[0].equals("DELETE")) {
				int k = 0;
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i).stergeEntitate(parts[1], parts[2]) == 1) {
						k = 1;
					}
				}
				if( k == 0 ) {
					writer.print("NO INSTANCE TO DELETE" + "\n");			//entitatea nu exista in baza de date
				}
			}
			/*
			 * se cauta entitatea in fiecare nod
			 * in functia cautaEntitate se cauta entitatea cu cheia primara data ca parametru 
			 * se afiseaza nodurile in care se gaseste si atributele entitatii
			 */
			if(parts[0].equals("GET")) {
				int k = 0;
				index = 0;
				
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i).cautaEntitate(parts[1], parts[2], writer) == 1) {
						index = i;
						k = 1;
					}
				}
				if(k == 0) {
					writer.print("NO INSTANCE FOUND" + "\n");
				}
				else {
					nodes.get(index).afisEntitate(parts[1], parts[2], writer );
				}
			}
			
			/*
			 * se cauta in fiecare nod entiatea
			 * se modifica atributele entitatii in fiecare nod
			 * se reseteaza timestampul entiatii
			 * se sorteaza lista de entitati in functie de timestamp
			 */
			if(parts[0].equals("UPDATE")) {
				
				for(int i = 3; i < parts.length - 1; i++) {
					for(int j = 0; j < nodes.size(); j++) {
						nodes.get(j).updateEntitate(parts[1], parts[2], parts[i], parts[i+1]);
					}
				}
			}
			/*
			 * parcurge fiecare nod si cauta elementele cu timestampul mai mic decat cel dat ca parametru
			 */
			if(parts[0].equals("CLEANUP")) {
				for(int i = 0; i < nodes.size(); i++ ) {
					long timestamp = Long.parseLong(parts[2]);
					nodes.get(i).stergeEntTime(timestamp);
				}
			}
		}

		writer.close(); // inchiderea fisierului in care se scrie
	}
}