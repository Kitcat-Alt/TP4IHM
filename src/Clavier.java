import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.scene.Node;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une liste de chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(List<String> touches, EventHandler<ActionEvent> actionTouches, int tailleLigne) {
        super();
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(2);
        this.setHgap(2);
        this.setPrefRows(4);
        this.setPrefColumns(tailleLigne);
        this.setMaxWidth(425);
        //this.setPrefRows(tailleLigne);
        //this.setMaxWidth(400);

        //on parcours la liste des touches, pour chaque touche on créer un bouton, que l'on lie au controlleur lettre et on ajoute ce bouton au tilepane
        this.clavier = new ArrayList<>();
        for(String lettre : touches){
            Button boutonLettre = new Button(lettre);
            boutonLettre.setShape(new Circle(1.5));
            boutonLettre.setOnAction(actionTouches);
            this.getChildren().addAll(boutonLettre);
            this.clavier.add(boutonLettre);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees un ensemble contenant les touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
        // A implémenter
        //on parcours les touches du clavier
        for (Node node : this.getChildren()){
            if (node instanceof Button){
                Button btn = (Button) node;
                //Si la touche fait partie des touches déjà préssées on désactive le bouton correspondant
                if (touchesDesactivees.contains(btn.getText())){
                    btn.setDisable(true);
                }
                else{
                    btn.setDisable(false);
                }
            }
        } 
    }
}
