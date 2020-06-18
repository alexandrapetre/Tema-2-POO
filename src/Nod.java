

import java.io.PrintWriter;
import java.util.*;

/**
 * @author Alexandra Petre
 *
 */
public class Nod {

	int id_nod;
	int MaxCapacity;
	ArrayList<Entitate> vect_entitati = new ArrayList<Entitate>();
	
	public Nod(int max, int id_nod) {
		this.MaxCapacity = max;
		this.id_nod = id_nod;
	}

	
	/**
	 * @param nume1
	 * @param cheie_primara1
	 * metoda sterge entitatea care are cheia primara si numele egale cu cele date ca parametr
	 * @return
	 */
	
	public int stergeEntitate(String nume1, String cheie_primara1) {
		for(int j = 0; j < vect_entitati.size(); j++) {
			if(vect_entitati.get(j).nume.equals(nume1) && vect_entitati.get(j).cheie_primara.equals(cheie_primara1)) {
				vect_entitati.remove(j);
				return 1;
			}
		}
		return 0;
	}

	/**
	 * @param timestamp1
	 * metoda sterge fiecare entitate care are timestampul mai mic decat timestampul dat ca parametru
	 * se sorteaza lista de entitati dupa timestamp
	 */
	
	public void stergeEntTime(long timestamp1) {
		long dif;
		for(int j = 0; j < vect_entitati.size(); j++) {
			dif = vect_entitati.get(j).timestamp;
			if( dif < timestamp1) {
				vect_entitati.remove(j);
				j--;
			}	
		}
		Collections.sort(vect_entitati);
	}
	
	/**
	 * @param nume1
	 * @param cheie_primara1
	 * @param write
	 * @return
	 * se afiseaza fiecare nod in care se afla entitatea cu o anumita cheie primara
	 * functia intoarce 1 daca entitatea a fost gasita in nod
	 */
	
	public int cautaEntitate(String nume1, String cheie_primara1, PrintWriter write) {
		for(int j = 0; j < vect_entitati.size(); j++) {
			if(vect_entitati.get(j).nume.equals(nume1) && vect_entitati.get(j).cheie_primara.equals(cheie_primara1)) {
				write.print("Nod" + id_nod + " ");
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * @param nume1
	 * @param cheie_primara1
	 * @param writer	 
	 * daca se gaseste entiatea cu cheia primara egala cu cea data ca parametru 
	 * se apeleaza functia de afisare din clasa Entitate
	 */
	
	public void afisEntitate(String nume1, String cheie_primara1, PrintWriter writer) {
		for(int j = 0; j < vect_entitati.size(); j++) {
			if(vect_entitati.get(j).nume.equals(nume1) && vect_entitati.get(j).cheie_primara.equals(cheie_primara1)) {
				vect_entitati.get(j).afis(writer);
			}
		}
	}

	
	/**
	 * @param nume1
	 * @param cheie_primara1
	 * @param nume_atribut
	 * @param val_noua
	 * metoda modifica entitatea cu cheia primara data ca parametru
	 * si apoi sorteaza in functie de timestamp elementele din nod
	 * 
	 */
	
	public void updateEntitate(String nume1, String cheie_primara1, String nume_atribut, String val_noua) {

		for(int i = 0; i < vect_entitati.size(); i++) {
			if(vect_entitati.get(i).nume.equals(nume1) && vect_entitati.get(i).cheie_primara.equals(cheie_primara1)) {
				vect_entitati.get(i).timestamp = System.nanoTime();
				for(int j = 0; j < vect_entitati.get(i).obj.size(); j++) {
					if(vect_entitati.get(i).obj.get(j).nume.equals(nume_atribut)) {
						if(vect_entitati.get(i).obj.get(j).tip_element.equals("String")) {
							vect_entitati.get(i).obj.get(j).stg = val_noua;
						}
						if(vect_entitati.get(i).obj.get(j).tip_element.equals("Float")) {
							vect_entitati.get(i).obj.get(j).flt =Float.parseFloat(val_noua);
						}
						if(vect_entitati.get(i).obj.get(j).tip_element.equals("Integer")) {
							vect_entitati.get(i).obj.get(j).intg = Integer.parseInt(val_noua);
						}
					}
				}
			}
		}
		Collections.sort(vect_entitati);
	}	

}
