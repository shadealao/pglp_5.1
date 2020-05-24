package fr.uvsq21506437.AnnuaireSerealizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class CompositeGroupe  extends DAO<Groupe>  implements Groupe, Iterable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	private ArrayList<Groupe> grpPersonnel = new ArrayList<Groupe>();
	private ArrayList<String> fonction = new ArrayList<String>();
	public ArrayList<Groupe> liste = new ArrayList<Groupe>();
	private String nom;

	public CompositeGroupe(String nom) {
		super();
		this.nom = nom;
	}
	
	

	public ArrayList<Groupe> getGrpPersonnel() {
		return grpPersonnel;
	}



	public void setGrpPersonnel(ArrayList<Groupe> grpPersonnel) {
		this.grpPersonnel = grpPersonnel;
	}



	public ArrayList<String> getFonction() {
		return fonction;
	}



	public void setFonction(ArrayList<String> fonction) {
		this.fonction = fonction;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public void add(Groupe p) {
		grpPersonnel.add(p);
	}

	public void remove(Groupe p) {
		grpPersonnel.remove(p);
	}
	
	public void affiche() {
		for(Groupe grp : grpPersonnel) {
			grp.affiche();
		}

	}

	@Override
	public Iterator<Groupe> iterator() {
		//ArrayList <Groupe> liste = new ArrayList<Groupe>();
		//liste.add(this);
		return grpPersonnel.iterator();
	}

	public void Parcoursgrp() { //parcours simple
		Iterator <Groupe>it = iterator();
		while(it.hasNext()) {
			Groupe grp = it.next();
			grp.affiche();
			//System.out.println(it.next());
		}
	}
	public void poste() {
		Iterator <Groupe>it = iterator();
		Groupe grp = it.next();
		ArrayList <String> in = new ArrayList<>();
		String poste = "";
		do {
			grp = it.next();
			poste = grp.Service();
			grp.affiche();
			System.out.println("poste : "+ poste);
			if(!poste.equals("")) {
				if(in.contains(poste) == false) {
					in.add(poste);
					Parcoursposte(poste);
				}
			}
			else {
				/*ArrayList<Groupe> CG = ((CompositeGroupe) grp).getGrpPersonnel();
				
				for (Groupe element : CG) {
					poste = CG.
				}*/
			}
		}while ((it.hasNext()));
		
		
		//return poste;
	}
	public void Parcoursposte(String poste) { //parcours simple
		Iterator <Groupe>it = iterator();
		
			while(it.hasNext()) {
				Groupe grp = it.next();
				if(grp.Service().equals(poste))grp.affiche();
				//System.out.println(it.next());
			}
		
	}

	public void listeFonction() {
		fonction.add("Directeur");
		fonction.add("Manager");
		fonction.add("Encadrant");
		fonction.add("Etudiant");
	}

	public void Parcoursfonction() {
		/*	listeFonction();
		Iterator <Groupe>it = iterator();
		for (String fnctn : fonction) {
			System.out.println("Fonction " + fnctn + " : ");

			while(it.hasNext()) {
				Groupe grp = it.next();

				//Personnel p = (Personnel)grp;
				//if(p.getFonction() == fnctn)
				//	p.affiche();
			    System.out.println(it.next());
			}


		}
		 */
	}

	@Override
	public String Service() {
		return "";
	}


	public void serealizerobjet(Groupe g) {
		try {
			final FileOutputStream fichier = new FileOutputStream(g.getNom()+".ser");
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(g);
			oos.flush();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Groupe readserealizerobjet(String nom) {
		Groupe g = null;
		try {
			final FileInputStream fichier = new FileInputStream(nom+".ser");
			ois = new ObjectInputStream(fichier);
			g = (Groupe) ois.readObject();
			System.out.println("Personne : ");
			g.affiche();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return g;
	}
	@Override
	public Groupe create(Groupe obj) {
		liste.add(obj);
		this.serealizerobjet(obj);
		return obj;
	}

	@Override
	public Groupe update(Groupe obj) {
		return obj;
	}

	@Override
	public void delete(Groupe obj) {
		liste.remove(obj);
		final String chemin = System.getProperty("user.dir") + "\\" + obj.getNom() + ".ser";
		ObjectInputStream reader = null;
		File file = new File(chemin);
		file.delete();
	}

	@Override
	public Groupe read(String nom) {
		for (Groupe a : liste) {
			if (a.getNom().equals(nom)) {
				return a;
			}
		}
		Groupe grp = this.readserealizerobjet(nom);
		return grp;
	}

}
