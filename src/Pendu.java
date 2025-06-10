import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /** 
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button boutoninfo;
    /**
     * le bouton qui permet d'afficher les règles
     */ 
    private Button bJouer;
    
    private Stage stage;

    private int cptImage;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        //this.modelePendu = new MotMystere("/home/Kitcat/TP4IHM/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.dessin = new ImageView();
        this.cptImage = 0;
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        //fenetre.setCenter(this.fenetreAccueil());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane titre(){
        // A implementer          
        BorderPane banniere = new BorderPane();
        banniere.setStyle("-fx-background-color:rgb(218, 230, 243);");
        Text TxtTop = new Text("Jeu du Pendu");
        banniere.setPadding(new Insets(20));
        TxtTop.setFont(Font.font("Arial", 30));
        banniere.setLeft(TxtTop);
        //---------------------------------------------
        HBox hboxRight = new HBox();
        hboxRight.setSpacing(2.);
        banniere.setRight(hboxRight);
        //---------------------------------------------
        Image imgMaison = new Image("home.png");
        ImageView imgwMaison = new ImageView();
        imgwMaison.setFitWidth(20);
        imgwMaison.setFitHeight(20);
        imgwMaison.setImage(imgMaison);
        this.boutonMaison = new Button("",imgwMaison);
        this.boutonMaison.setOnAction(new RetourAccueil(this.modelePendu, this));

        Image imgPara = new Image("parametres.png");
        ImageView imgwPara = new ImageView();
        imgwPara.setImage(imgPara);
        imgwPara.setFitHeight(20);
        imgwPara.setFitWidth(20);
        this.boutonParametres = new Button("",imgwPara);

        Image imgInfo = new Image("info.png");
        ImageView imgwInfo = new ImageView();
        imgwInfo.setImage(imgInfo);
        imgwInfo.setFitHeight(20);
        imgwInfo.setFitWidth(20);
        this.boutoninfo = new Button("",imgwInfo);
        this.boutoninfo.setOnAction(new ControleurInfos(this));
        //---------------------------------------------
        hboxRight.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutoninfo);
        return banniere;
    }
    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private BorderPane fenetreJeu(){
        this.boutonMaison.setDisable(false);
        this.boutonParametres.setDisable(true);
        this.panelCentral = new BorderPane();
        VBox barreClavier = new VBox();
        barreClavier.setFillWidth(false);
        List<String> alphabet = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","-");
        this.clavier = new Clavier(alphabet, new ControleurLettres(modelePendu, this),8);
        
        
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        
        this.dessin.setImage(lesImages.get(this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants()));

        barreClavier.getChildren().addAll(clavier);
        this.panelCentral.setTop(this.motCrypte);
        this.panelCentral.setCenter(this.dessin);
        this.panelCentral.setBottom(barreClavier);        
        return this.panelCentral;

    }

    /**
    / * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
    / */ 
    private BorderPane fenetreAccueil(){
        this.panelCentral = new BorderPane();
        this.bJouer = new Button("Lancer une partie");

        VBox vBoxCenter = new VBox(15);
        //this.panelCentral.setTop(bJouer);

        TitledPane tpDifficulte = new TitledPane();

        VBox vBoxRadio = new VBox(10);

        ToggleGroup tgRadio = new ToggleGroup();
        RadioButton facile = new RadioButton("Facile");
        RadioButton medium = new RadioButton("Médium");
        RadioButton difficile = new RadioButton("Difficile");
        RadioButton expert = new RadioButton("Expert");

        facile.setToggleGroup(tgRadio);
        medium.setToggleGroup(tgRadio);
        difficile.setToggleGroup(tgRadio);
        expert.setToggleGroup(tgRadio);

        facile.setOnAction(new ControleurNiveau(this.modelePendu));
        medium.setOnAction(new ControleurNiveau(this.modelePendu));
        difficile.setOnAction(new ControleurNiveau(this.modelePendu));
        expert.setOnAction(new ControleurNiveau(this.modelePendu));

        this.bJouer.setOnAction(new ControleurLancerPartie(modelePendu, this));

        vBoxRadio.getChildren().addAll(facile, medium, difficile, expert);
        tpDifficulte.setContent(vBoxRadio);

        vBoxCenter.setPadding(new Insets(10,10,10,10));
        vBoxCenter.getChildren().addAll(bJouer, tpDifficulte);

        this.boutonMaison.setDisable(true);
        this.panelCentral.setCenter(vBoxCenter);
        //this.panelCentral.setCenter(tpDifficulte);
        return this.panelCentral;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.fenetreAccueil();
        this.stage.setScene(laScene());
        

    }
    
    public void modeJeu(){
        this.fenetreJeu();
        this.stage.setScene(laScene());
        // A implementer
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        modeJeu();
        this.cptImage = 0;
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        this.cptImage++;
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
