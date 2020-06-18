


import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Alexandra Petre 325CB
 * 
 */

public class Entitate implements Comparable<Entitate> {
	
	int rf;
	String nume;
	String cheie_primara;
	long timestamp;
	ArrayList<Obiecte> obj = new ArrayList<Obiecte>();
	
	public Entitate() {
	}
	
	public Entitate(String nume, int rf) {
		this.nume = nume;
		this.rf = rf;
		this.timestamp = System.nanoTime();
	}

	
	/**
	 * @param writer
	 * se afiseaza fiecare atribut al entitatii si cheia primara 
	 * in functie de tipul de date al atributelor
	 * 
	 */
	public void afis(PrintWriter writer) {
		
		writer.print(nume + " " );
		for(int  i = 0; i < obj.size() - 1; i++) {
			if(obj.get(i).tip_element.equals("Integer")) {
				writer.print(obj.get(i).nume + ":" + obj.get(i).intg + " ");
			}
			
			if(obj.get(i).tip_element.equals("Float")) {
				DecimalFormat df = new DecimalFormat("###.##");
				writer.print(obj.get(i).nume + ":" + df.format(obj.get(i).flt) + " ");
			}
			
			if(obj.get(i).tip_element.equals("String")) {
				writer.print(obj.get(i).nume + ":" + obj.get(i).stg + " ");
			}
		}
		
		if(obj.get(obj.size()-1).tip_element.equals("Integer")) {
			writer.print(obj.get(obj.size()-1).nume + ":" + obj.get(obj.size()-1).intg );
		}
		
		if(obj.get(obj.size()-1).tip_element.equals("Float")) {
			DecimalFormat df = new DecimalFormat("###.##");
			writer.print(obj.get(obj.size()-1).nume + ":" + df.format(obj.get(obj.size()-1).flt) );
		}
		
		if(obj.get(obj.size()-1).tip_element.equals("String")) {
			writer.print(obj.get(obj.size()-1).nume + ":" + obj.get(obj.size()-1).stg );
		}
		
		writer.print("\n");
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * se compara timestampul entitatii si cel al entitatii date ca parametru
	 */
	public int compareTo(Entitate e) {
		
		long compareTimestamp = ((Entitate) e).timestamp; 
		return (int)( compareTimestamp - this.timestamp);
	}
	
}
	