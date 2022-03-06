/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import interfaces.IcartefideliteService;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.CarteFideliteService;
import service.CodeReductionService;
import service.MailingService;
import util.MyConnexion;

/**
 * FXML Controller class
 *
 * @author EYA
 */
public class Cartefideliteclient2Controller implements Initializable {

    @FXML
    private AnchorPane onecarte;
    @FXML
    private Button supprimer;
    @FXML
    private Button convert;
        Connection cnx = MyConnexion.getInstance().getCnx(); // appliquer la connexion 
        IcartefideliteService crt= new CarteFideliteService(); 
    @FXML
    private Label affiche;
    @FXML
    private AnchorPane param;
    @FXML
    private ImageView carte;
    @FXML
    private ImageView panier;
    @FXML
    private Button gestioncmd;
    @FXML
    private Button gestioncarte;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      


        affiche.setText(crt.afficherbyid("20").toString());
    };

    @FXML
    private void clicked(ActionEvent event) throws Exception {
        if(event.getSource() == supprimer)
        {
   System.out.print(crt.ChercherCartebyClient("20").getNumCarte());
            
  crt.supprimer(crt.ChercherCartebyClient("20").getNumCarte());
        }else if (event.getSource() == convert)
        {
           TextInputDialog dialog = new TextInputDialog("");
dialog.setTitle("Convertisseur des points de fidélité");
dialog.setHeaderText("Convertir vos points de fidélité en un bon de réduction:");
dialog.setContentText("Donner le nombre des  points à convertir:");

// Traditional way to get the response value.
Optional<String> result = dialog.showAndWait();
if (result.isPresent()){
    System.out.println("Nombre de points : " + result.get());
}

// The Java 8 way to get the response value (with lambda expression).
result.ifPresent(nbre -> System.out.println("Nombre de points: " + nbre));
            String promo=crt.convertirlespoints(crt.ChercherCartebyClient("20"),Integer.parseInt(result.get()));
       affiche.setText(crt.afficherbyid("20").toString());
       CodeReductionService code=new CodeReductionService();
int pourcentage=code.pourcentagecode(promo);
            Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Code de réduction crée");
alert.setHeaderText(null);
alert.setContentText("Votre code promo est disponible tapez "+ promo + " pour l'utiliser et obtenir jusqu'a "+pourcentage + " % de reduction sur l'achat");

alert.showAndWait();

MailingService.sendMail("eyabensalem@gmail.com",promo,pourcentage);

        }
    }

    @FXML
    private void onclick(MouseEvent event) {
    }

 



    
    }    


    

