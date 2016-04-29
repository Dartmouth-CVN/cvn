package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by mrampiah on 4/28/16.
 */
public class LoadedScene {
    private AbsController controller;
    private FXMLLoader loader;
    private Pane pane;
    private Scene scene;

    public LoadedScene(AbsController controller, FXMLLoader loader, Pane pane) {
        this.controller = controller;
        this.loader = loader;
        this.pane = pane;
        this.scene = new Scene(pane);
    }

    public AbsController getController() {
        return controller;
    }

    public void setController(AbsController controller) {
        this.controller = controller;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Scene getScene(){
        return scene;
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
