package manager.solveurs.PL;

import manager.solveurs.Solveur;

import data.DataRecours;
import data.solution.SolutionEnergieRecours;

/**
 * Cette classe permet d'�crire un programme lin�aire � partir de donn�es pour le probl�me de management de la production d'�nergie.
 * Ce PL est utilis� par un solveur de programmes lin�aires.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 */
public abstract class PLEnergieRecours implements Solveur {

	/** Variable contenant les donn�es du probl�me */
	protected DataRecours donnees;
	/** La solution */
	protected SolutionEnergieRecours solution;
	/** Le vecteur de co�ts par p�riode et par centrale */
	protected double[] couts;
	/** La matrice qui pour chaque sc�nario et chaque p�riode associe la liste des facteurs de disponibilit� */
	protected double[][][] facteursDisponibilite;
	/** Les gains des apports en eau */
	protected double gains;
	
	/**
	 * Cr�e un nouveau PLEnergie.
	 * @param donnees les donn�es du probl�me
	 */
	public PLEnergieRecours(DataRecours donnees)
	{
		this.donnees = donnees;
		solution = new SolutionEnergieRecours(donnees);
		genererPL();
	}

	/**
	 * G�n�re le programme lin�aire � partir des donn�es.
	 * Remplit les tableaux du probl�me.
	 */
	private void genererPL()
	{
		facteursDisponibilite = new double[100][7][5];
		for(int s = 0; s < 100; s++)
		{
			for(int p = 0; p < 7; p++)
			{
				for(int c = 0; c < 4; c++)
				{
					facteursDisponibilite[s][p][c] = donnees.getScenario(s).getPaliersPeriodeCentrale(p, c);
				}
			}
		}
		
		couts = new double[7*5];		
		for(int p = 0; p < 7; p++)
		{
			for(int c = 0; c < 5; c++)
			{
				couts[p*5 + c] = donnees.getCoutCentrale(c);
			}
			gains += donnees.getApportsPeriode(p) *  donnees.getCoutCentrale(4);
		}
	}
	
	/**
	 * Renvoie la solution calcul�e.
	 * @return la solution calcul�e
	 */
	public SolutionEnergieRecours getSolution() {
		return solution;
	}
	
	public abstract void lancer();
}
