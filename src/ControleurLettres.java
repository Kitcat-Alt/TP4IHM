import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    private Set<String> ensemble;


    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu){
        this.modelePendu= modelePendu;
        this.vuePendu = vuePendu;
        this.ensemble = new HashSet<>();
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button button = (Button) (actionEvent.getSource());
        String lettreATrouver = button.getText();
        this.ensemble.add(lettreATrouver);
        this.vuePendu.getClavier().desactiveTouches(ensemble);
        this.modelePendu.essaiLettre(lettreATrouver.charAt(0));
        this.vuePendu.majAffichage();
        if (this.modelePendu.gagne()) {
            this.vuePendu.popUpMessageGagne().showAndWait();
        }
        if (this.modelePendu.perdu()) {
            this.vuePendu.popUpMessagePerdu().showAndWait();
        }
    }
}
