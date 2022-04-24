import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class JeuDemineur extends JFrame implements ActionListener,
MouseListener {

int lignes = 9;
int colonnes = 9;
int nbreMines = 10;
GridLayout layout = new GridLayout(lignes, colonnes);

boolean[] mines = new boolean[lignes * colonnes];
boolean[] clickable = new boolean[lignes * colonnes];
boolean perdu = false;
boolean gagner = false;
int[] nombre = new int[lignes * colonnes];
JButton[] boutons = new JButton[lignes * colonnes];
boolean[] clicker = new boolean[lignes * colonnes];
JMenuItem nouveauJeu = new JMenuItem("nouveau jeu");
JMenuItem niveau = new JMenuItem("options");
JLabel compteur = new JLabel("mines: " + nbreMines + " drapeau: 0");
JPanel fenetre = new JPanel();

JButton Debutant = new JButton("Debutant");

JButton Inter = new JButton("Intermediaire");

JButton Exp = new JButton("Expert");

// Contrusctureur 
public JeuDemineur() {
    
    super("Jeu demineur");
    fenetre.setLayout(layout);
    initialiserGrille();
    for (int i = 0; i < (lignes * colonnes); i++) {
        fenetre.add(boutons[i]);
    }
    JMenuBar barMenu = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    nouveauJeu.addActionListener(this);
    menu.add(nouveauJeu);
    niveau.addActionListener(this);
    menu.add(niveau);
    barMenu.add(menu);
    this.setJMenuBar(barMenu);
    this.add(fenetre);
    this.add(compteur, BorderLayout.SOUTH);
    this.pack();
    this.setVisible(true);
}

// cette methode permet de répartir les mines 
public void repartirMines() {
    int mineRestReparti = nbreMines;
    while (mineRestReparti > 0) {
        int x = (int) Math.floor(Math.random() * lignes);
        int y = (int) Math.floor(Math.random() * colonnes);
        if (!mines[(lignes * y) + x]) {
            mines[(lignes * y) + x] = true;
            mineRestReparti--;
        }
    }
}
 
// cette methode permet de compter le nombre de mines autour d'une case et de placer ce nombre dans cette dernière
public void caseNumbers() {
    for (int x = 0; x < lignes; x++) {
        for (int y = 0; y < colonnes; y++) {
            int courant = (lignes * y) + x;
            if (mines[courant]) {
                nombre[courant] = 0;
                continue;
            }
            int compteur = 0;
            boolean gauche = (x - 1) >= 0;
            boolean droit = (x + 1) < lignes;
            boolean haut = (y - 1) >= 0;
            boolean bas= (y + 1) < colonnes;
            int caseGauche = (lignes * (y)) + (x - 1);
            int caseDroit = (lignes * (y)) + (x + 1);
            int caseHaut = (lignes * (y - 1)) + (x);
            int caseHautGauche = (lignes * (y - 1)) + (x - 1);
            int caseHautDroit = (lignes * (y - 1)) + (x + 1);
            int caseBas = (lignes * (y + 1)) + (x);
            int caseBasGauche = (lignes * (y + 1)) + (x - 1);
            int caseBasDroit = (lignes * (y + 1)) + (x + 1);
            if (haut) {
                if (mines[caseHaut]) {
                    compteur++;
                }
                if (gauche) {
                    if (mines[caseHautGauche]) {
                        compteur++;
                    }
                }
                if (droit) {
                    if (mines[caseHautDroit]) {
                        compteur++;
                    }
                }
            }
            if (bas) {
                if (mines[caseBas]) {
                    compteur++;
                }
                if (gauche) {
                    if (mines[caseBasGauche]) {
                        compteur++;
                    }
                }
                if (droit) {
                    if (mines[caseBasDroit]) {
                        compteur++;
                    }
                }
            }
            if (gauche) {
                if (mines[caseGauche]) {
                    compteur++;
                }
            }
            if (droit) {
                if (mines[caseDroit]) {
                    compteur++;
                }
            }
            nombre[courant] = compteur;
        }
    }
}

// cette méthode permet d'initialiser la grille par défaut
public void initialiserGrille() {
    for (int x = 0; x < lignes; x++) {
        for (int y = 0; y < colonnes; y++) {
            mines[(lignes * y) + x] = false;
            clicker[(lignes * y) + x] = false;
            clickable[(lignes * y) + x] = true;
            boutons[(lignes * y) + x] = new JButton( /*"" + ( x * y )*/);
            boutons[(lignes * y) + x].setPreferredSize(new Dimension(
                    45, 45));
            boutons[(lignes * y) + x].addActionListener(this);
            boutons[(lignes * y) + x].addMouseListener(this);
        }
    }
    repartirMines();
    caseNumbers();
}

// cette  méthode permet d'initialiser la grille à partir du niveau choisi par l'utilisateur
public void initialiserGrille2() {
    this.remove(fenetre);
    compteur = new JLabel("mines: " + nbreMines + " marked: 0");
    fenetre = new JPanel();
    layout = new GridLayout(lignes, colonnes);
    fenetre.setLayout(layout);
    boutons = new JButton[lignes * colonnes];
    mines = new boolean[lignes * colonnes];
    clicker = new boolean[lignes * colonnes];
    clickable = new boolean[lignes * colonnes];
    nombre = new int[lignes * colonnes];
    initialiserGrille();
    for (int i = 0; i < (lignes * colonnes); i++) {
        fenetre.add(boutons[i]);
    }
    this.add(fenetre);
    this.add(compteur, BorderLayout.SOUTH);
    this.pack();
    repartirMines();
    caseNumbers();
}

// cette méthode permet de lancer une nouvelle partie 
public void nouveauJeu() {
    for (int x = 0; x < lignes; x++) {
        for (int y = 0; y < colonnes; y++) {
            mines[(lignes * y) + x] = false;
            clicker[(lignes * y) + x] = false;
            clickable[(lignes * y) + x] = true;
            boutons[(lignes * y) + x].setEnabled(true);
            boutons[(lignes * y) + x].setText("");
        }
    }
    repartirMines();
    caseNumbers();
    perdu = false;
    compteur.setText("mines: " + nbreMines + " marké: 0");
}

// Cette méthode est un écouteur qui définit les différentes actions lorsqu'on clique sur les boutons du jeu
public void actionPerformed(ActionEvent e) {

    if (e.getSource() == niveau) {
        this.remove(fenetre);
        this.remove(compteur);
        fenetre = new JPanel();
        Debutant.setBounds(200, 20, 100, 30);
        Debutant.addActionListener(this);
        Inter.setBounds(200, 20, 100, 30);
        Inter.addActionListener(this);
        Exp.setBounds(200, 20, 100, 30);
        Exp.addActionListener(this);
        fenetre.setLayout(new FlowLayout());
        fenetre.add(Debutant);
        fenetre.add(Inter);
        fenetre.add(Exp);
        this.add(fenetre);
        this.validate();
        
    }
    
    if(e.getSource() == Debutant) {
        lignes = 9;
        colonnes = 9;
        nbreMines = 10;
        initialiserGrille2();
    }
    if(e.getSource() == Inter) {
        lignes = 16;
        colonnes = 16;
        nbreMines = 40;
        initialiserGrille2();
    }
    if(e.getSource() == Exp) {
        lignes = 16;
        colonnes = 30;
        nbreMines = 99;
        initialiserGrille2();
    }
    
    if (!gagner) {
        for (int x = 0; x < lignes; x++) {
            for (int y = 0; y < colonnes; y++) {
                if (e.getSource() == boutons[(lignes * y) + x]
                        && !gagner && clickable[(lignes * y) + x]) {
                    clickGauche(x, y);
                    break;
                }
            }
        }
    }
    if (e.getSource() == nouveauJeu) {
        nouveauJeu();
        gagner = false;
        return;

    }
    rechercheEtatJeu();
}

public void mouseEntered(MouseEvent e) {
}

public void mouseExited(MouseEvent e) {
}

// cette méthode est un écouteur permettant de définir les différentes actions qui s'appliquent lorsqu'on clique sur une case de la grille
public void mousePressed(MouseEvent e) {
    if (e.getButton() == 3) {
        int n = 0;
        for (int x = 0; x < lignes; x++) {
            for (int y = 0; y < colonnes; y++) {
                if (e.getSource() == boutons[(lignes * y) + x]) {
                    clickable[(lignes * y) + x] = !clickable[(lignes * y)
                            + x];
                }
                if (!clicker[(lignes * y) + x]) {
                    if (!clickable[(lignes * y) + x]) {
                        boutons[(lignes * y) + x].setText("X");
                        n++;
                    } else {
                        boutons[(lignes * y) + x].setText("");
                    }
                    compteur.setText("mines: " + nbreMines + " marké: "
                            + n);
                }
            }
        }
    }
}

public void mouseReleased(MouseEvent e) {
}

public void mouseClicked(MouseEvent e) {
}

// cette méthode définit les actions à appliquer lorsqu'on fait une clique gauche sur une case 
// Autrement dit, cette méthode permet de dévoiler une case
public void clickGauche(int x, int y) {
    int courant = (lignes * y) + x;


    clicker[courant] = true;
    boutons[courant].setEnabled(false);

    if(!mines[courant] && nombre[courant] !=0) {   
        boutons[courant].setText("" + nombre[courant]); }
    if (!mines[courant] && nombre[courant] == 0) {
            boutons[courant].setText("");
        }
    if (mines[courant] && !gagner) {
        boutons[courant].setText("-");
        AfficheDefaite(); 
    }
}

// cette méthode vérifie si l'utilisateur à gagner une partie ou non 
public void rechercheEtatJeu() { 
    for (int x = 0; x < lignes; x++) {
        for (int y = 0; y < colonnes; y++) {
            int courant = (lignes * y) + x;
            if (!clicker[courant]) {
                if (mines[courant]) {
                    continue;
                } else {
                    return;
                }
            }
        }
    }

    AfficheVictoire(); 
}

// cette méthode affiche un message en cas de réussite
// et relance un nouveau jeu si l'utilisateur clique sur OK 
public void AfficheVictoire() { 
    if (!perdu && !gagner) {
        gagner = true;
        JOptionPane.showMessageDialog(null,
                "Felicitation, Vous avez gagne !", "Victoire ", 
                JOptionPane.INFORMATION_MESSAGE);
        nouveauJeu.doClick();
    }
}

// cette méthode affiche un message en cas de défaite
// et relance un nouveau jeu si l'utilisateur clique sur OK
public void AfficheDefaite() { 
    if (!perdu && !gagner) {
        perdu = true;
        for (int i = 0; i < lignes * colonnes; i++) {
            if (!clicker[i]) {
                boutons[i].doClick(0);
            }
        }
        JOptionPane.showMessageDialog(null,
                "Desole, vous avez perdu ", "Defaite ",
                JOptionPane.ERROR_MESSAGE);
        nouveauJeu();
    }
}

}
