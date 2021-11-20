package dad.email;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private EmailController emailController;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.primaryStage = new Stage();
        emailController = new EmailController();
        Scene scene = new Scene(emailController.getView());
        primaryStage.setTitle("EnviarEmail");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/email-send-icon-32x32.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}