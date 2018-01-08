package com.krzykocz.qbfsolverinterface;

import java.io.File;
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

public class FXMLController implements Initializable {

    @FXML
    private TextField VariablesField;
    @FXML
    private TextArea ClausesArea;
    @FXML
    private TextArea CnfArea;
    @FXML
    private Alert info_popup = new Alert(AlertType.INFORMATION);

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
        info_popup.setHeaderText(depOut);
        info_popup.showAndWait();
//        System.err.println(new File("target/classes/qbfsolvers/depqbf").getAbsolutePath().toString());
    }
    
    @FXML
    private void rareqsButtonAction(ActionEvent event) throws URISyntaxException {
        String rareOut = solve.solverRare(chtc.getQbfinput());
        info_popup.setHeaderText(rareOut);
        info_popup.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        
    }
}
