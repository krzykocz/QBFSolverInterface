package com.krzykocz.qbfsolverinterface;

import java.io.File;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML
    private TextField VariablesField;
    @FXML
    private TextArea ClausesArea;
    @FXML
    private TextArea CnfArea;
    @FXML
    private Alert infodeb_popup = new Alert(AlertType.INFORMATION);
    @FXML
    private Alert inforar_popup = new Alert(AlertType.INFORMATION);
    @FXML
    private Alert error_info = new Alert(AlertType.ERROR);
    @FXML
    FileChooser fileChooser = new FileChooser();

    private ConvertHToCnf chtc = new ConvertHToCnf();
    private Solve solve = new Solve();

    @FXML
    private void convertButtonAction(ActionEvent event) {
        chtc.setVariables(VariablesField.getText().split(" "));
        chtc.setClauses(ClausesArea.getText());
        chtc.setUsed(1);
        chtc.convertToCnf();
        CnfArea.setText(chtc.readFile(chtc.getQbfinput()));

    }

    @FXML
    private void depqbfButtonAction(ActionEvent event) throws URISyntaxException {
//        String output = solve.solvers(chtc.getQbfinput());
//        System.err.println(output);
        String depOut = solve.solverDep(chtc.getQbfinput());
        infodeb_popup.setHeaderText(depOut);
        infodeb_popup.showAndWait();
//        System.err.println(new File("target/classes/qbfsolvers/depqbf").getAbsolutePath().toString());
    }

    @FXML
    private void rareqsButtonAction(ActionEvent event) throws URISyntaxException {
        String rareOut = solve.solverRare(chtc.getQbfinput());
        inforar_popup.setHeaderText(rareOut);
        inforar_popup.showAndWait();
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        File saveFile = fileChooser.showSaveDialog(new Stage());
        if (saveFile != null) {
            try (PrintWriter out = new PrintWriter(saveFile)) {
                out.println(CnfArea.getText());
            } catch (java.io.FileNotFoundException ex) {
                error_info.setTitle("ERROR");
                error_info.setHeaderText("Error writing to file!");
            }
        }
    }
    
    @FXML
    private void loadButtonAction(ActionEvent event) {
        File loadFile = fileChooser.showOpenDialog(new Stage());
        if (loadFile !=null) {
            chtc.setQbfinput(loadFile);
            CnfArea.setText(chtc.readFile(chtc.getQbfinput()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }
}
