package dad.email;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailController implements Initializable {
    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnVaciar;

    @FXML
    private CheckBox checkSSL;

    @FXML
    private Label labelAsuntoi;

    @FXML
    private Label labelDestinatario;

    @FXML
    private Label labelRemitente;

    @FXML
    private Label labelSMTP;

    @FXML
    private Label labelSSL;

    @FXML
    private TextArea textAreaMensaje;

    @FXML
    private TextField textAsunto;

    @FXML
    private TextField textDestinatario;

    @FXML
    private TextField textRemitente;

    @FXML
    private TextField textSMTP;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textPuerto;

    @FXML
    private GridPane gridPane;

    //1º Creo las property
    private StringProperty smtpProperty;
    private BooleanProperty sslProperty;
    private StringProperty remitenteProperty;
    private StringProperty puertoProperty;
    private StringProperty destinatarioProperty;
    private StringProperty passwordProperty;
    private StringProperty asuntoProperty;
    private StringProperty mensajeProperty;

    public EmailController() throws IOException {
        //Creo la vista
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/viewEmail.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    private void iniProperty(){
        //2º Inicializo las property
        smtpProperty = new SimpleStringProperty();
        sslProperty = new SimpleBooleanProperty();
        remitenteProperty = new SimpleStringProperty();
        puertoProperty = new SimpleStringProperty();
        destinatarioProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        asuntoProperty = new SimpleStringProperty();
        mensajeProperty = new SimpleStringProperty();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniProperty();

        //3º Creo los bindeos
        smtpProperty.bind(textSMTP.textProperty());
        sslProperty.bind(checkSSL.selectedProperty());
        remitenteProperty.bind(textRemitente.textProperty());
        puertoProperty.bind(textPuerto.textProperty());
        destinatarioProperty.bind(textDestinatario.textProperty());
        passwordProperty.bind(textPassword.textProperty());
        asuntoProperty.bind(textAsunto.textProperty());
        mensajeProperty.bind(textAreaMensaje.textProperty());

    }

    @FXML
    void onActionCerrar(ActionEvent event) throws IOException {
       Stage stage = (Stage) btnCerrar.getScene().getWindow();
       stage.close();
    }

    @FXML
    void onActionEnviar(ActionEvent event) {
        try {
           Email email = new SimpleEmail();
            email.setHostName(smtpProperty.get());
            email.setSmtpPort(Integer.parseInt(puertoProperty.get()));
            email.setAuthenticator(new DefaultAuthenticator(remitenteProperty.get(), passwordProperty.get()));
            email.setSSLOnConnect(sslProperty.get());
            email.setFrom(remitenteProperty.get());
            email.setSubject(destinatarioProperty.get());
            email.setMsg(mensajeProperty.get());
            email.addTo(destinatarioProperty.get());
            email.send();

            alertSuccess();
        } catch (EmailException e) {
            alertError();
            //e.printStackTrace();
        }
    }

    @FXML
    void onActionVaciar(ActionEvent event) {
        textSMTP.clear();
        textPuerto.clear();
        checkSSL.setSelected(false);
        textRemitente.clear();
        textPassword.clear();
        textAreaMensaje.clear();
        textDestinatario.clear();
        textAsunto.clear();
    }

    private void alertSuccess() {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Mensaje enviado con éxito a \'" + destinatarioProperty.get() +"\'");
        alert.setTitle("Mensaje enviado");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("images/email-send-icon-32x32.png").toString()));
        //alert.setGraphic(new ImageView(this.getClass().getResource("email-send-icon-32x32.png").toString()));
        alert.showAndWait();

        textDestinatario.clear();
        textAsunto.clear();
        textAreaMensaje.clear();
    }

    private void alertError() {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("No se pudo enviar el email");
        alert.setTitle("Error");
        alert.setContentText("Invalid message supplied");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("images/email-send-icon-32x32.png").toString()));
        alert.showAndWait();
    }


    public GridPane getView(){
        return gridPane;
    }


}