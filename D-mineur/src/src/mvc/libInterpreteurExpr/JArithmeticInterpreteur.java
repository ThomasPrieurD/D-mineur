package src.mvc.libInterpreteurExpr;
//***
//***		INTERPRETEUR ARITHMETIQUE version 2.1			Version Java
//***
//***
//***
//******************************************************************************

/*
Historique:

2.1:
  - Version Java disponible:
  # les operateurs mathematiques disponibles sont ceux de Java donc certains manquent.

2.0: 
  - Portage en C++ et debut en Java

Version C++:
	 
  - Meilleure gestion memoire lors de la construction de l'expression.
  - Acceleration de certaines operations.

Version Java:
	 
  - Premiere version. Normalement ca doit marcher

1.3b: ajoute les fonctions suivantes: (NON DISTRIBUEE)
    - reconnaissance du symbole ž
    - ecriture formatee d'expression ( ameliorer)

1.2: corrige les bugs suivants:
    - erreur sur l'interpretation de fonctions unaires imbriquees telles que ln(exp(x)).
    - la fonction puissance (pour les puissances entieres).
     ajoute:
    - la verification de la chaine entree (voir code d'erreur)
    - la verification de l'existence de l'evaluation (voir code d'erreur)
     

1.1: corrige un bug au niveau de l'interpretation des fonctions du type:

      exp(-(x+y))

1.0: premiere version

Le code source peut etre librement modifie et distribue.

Puisqu'il s'agit d'un  essai en Java, le code ne doit pas etre super genial et merite sans doute
quelques modifications.En particulier, il serait interessant de rajouter le support des Exceptions
pour les calculs (division par zero, etc...).
 
*/





public class JArithmeticInterpreteur {

	static Node toto;

	public static void main(String args[]) {
		StringBuffer input;
		StringBuffer output;
		
		//input=new StringBuffer("cos(x)^2+sin(x)^2");
                
                input=new StringBuffer("(x+y)*x");
		output=new StringBuffer(255);

		toto= Node.Construct_Tree(input,input.length(),0);
		toto.Write_Tree(output);
		System.out.println(output);
                
		//System.out.println(toto.Compute_Tree(3,4,0));
                System.out.println(toto.Compute_Tree(3,1,0));
	}
}
