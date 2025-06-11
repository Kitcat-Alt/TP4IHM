import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long tempsPrec;
    /**
     * temps écoulé depuis le début de la mesure
     */
    private long tempsEcoule;
    /**
     * Vue du chronomètre
     */
    private Chronometre chrono;

    /**
     * Constructeur du contrôleur du chronomètre
     * noter que le modèle du chronomètre est tellement simple
     * qu'il est inclus dans le contrôleur
     * @param chrono Vue du chronomètre
     */
    public ControleurChronometre (Chronometre chrono){
        // A implémenter
        this.chrono = chrono;
        this.tempsEcoule = 0;
        this.tempsPrec = -1;
    }

    /**
     * Actions à effectuer tous les pas de temps
     * essentiellement mesurer le temps écoulé depuis la dernière mesure
     * et mise à jour de la durée totale
     * @param actionEvent événement Action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
        long tmpAct = System.currentTimeMillis();
        tempsEcoule += (tmpAct - tempsPrec);
        tempsPrec = tmpAct;

        int totalSec = (int)(tempsEcoule / 1000);
        int h = totalSec / 3600;
        int min = (totalSec % 3600) / 60;
        int sec = totalSec % 60;
        if (totalSec < 60) {
            this.chrono.setText(String.format("%ds", sec));
        } 
        if (totalSec < 3600) {
            this.chrono.setText(String.format("%dmin %ds", min, sec));
        } 
        else {
            this.chrono.setText(String.format("%dh %dmin %ds", h, min, sec));
        }
    }

    /**
     * Remet la durée à 0
     */
    public void reset(){
        // A implémenter
        this.tempsEcoule = 0;
        this.tempsPrec = System.currentTimeMillis();
    }
}
