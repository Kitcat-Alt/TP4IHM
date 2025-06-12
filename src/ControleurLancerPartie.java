import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une partie
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param p vue du jeu
     */
    public ControleurLancerPartie(MotMystere modelePendu, Pendu vuePendu) {
        // A implémenter
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
    
        Toggle choixUser = this.vuePendu.getIndiv().getSelectedToggle();
        if (choixUser != null) {
            //on récupère la valeur du radiobouton actif
            RadioButton selectedRadio = (RadioButton) choixUser;
            String choix = selectedRadio.getText(); 

            //en fonction de la valeur du bouton on met à jour le modèle
            switch (choix) {
                case "facile":
                    this.modelePendu.setNiveau(MotMystere.FACILE);
                    break;
                case "medium":
                    this.modelePendu.setNiveau(MotMystere.MOYEN);
                    break;
                case "difficile":
                    this.modelePendu.setNiveau(MotMystere.DIFFICILE);
                    break;
                case "expert":
                    this.modelePendu.setNiveau(MotMystere.EXPERT);
                    break;
            } 
        }
        //quand on relance la partie on vérifie si il n' y a pas de partie est en cours
        if (!(this.modelePendu.gagne())&& !(this.modelePendu.perdu())){
            Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait(); 
            // si la réponse est oui on lance une nouvelle partie
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
                this.vuePendu.lancePartie();
                System.out.println("Ok !");
            }
            else{
                System.out.println("D'ac !");
            }
        }
        else{
            this.vuePendu.lancePartie();
        }   
    }
}
