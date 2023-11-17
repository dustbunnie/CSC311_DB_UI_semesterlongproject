package viewmodel;

import dao.DbConnectivityClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Person;
import service.MyLogger;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class DB_GUI_Controller implements Initializable {

    boolean flag = false;

    @FXML
    TextField first_name, last_name, department, email, imageURL;
    @FXML
    ComboBox major;
    @FXML
    ImageView img_view;
    @FXML
    MenuBar menuBar;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, Integer> tv_id;
    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_department, tv_major, tv_email;

    @FXML
    Label BottomText;
    private final DbConnectivityClass cnUtil = new DbConnectivityClass();
    private final ObservableList<Person> data = cnUtil.getData();

    @FXML
     Button EditButton, DeleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tv_department.setCellValueFactory(new PropertyValueFactory<>("department"));
            tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));
            tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tv.setItems(data);

            major.getItems().addAll("CS", "CPIS", "English", "Math", "Science");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Validate Fields

        first_name.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                first_name.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");


                flag = false;
            }

        });
        first_name.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (first_name.getText().matches("[A-Za-z_\\s]{2,25}")) {
               // first_name.setEditable(false);
                first_name.setBorder(null);
            } else {

                first_name.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                first_name.setVisible(true);
                first_name.requestFocus();
                flag = true;
            }});

        // Last name field

        last_name.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                last_name.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");


                flag = false;
            }

        });
        last_name.focusedProperty().addListener((observable2, oldValue2, newValue2) -> {

            if (last_name.getText().matches("[A-Za-z_\\s]{2,25}")) {
                //last_name.setEditable(false);
                last_name.setBorder(null);
            } else {

                last_name.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                last_name.setVisible(true);
                last_name.requestFocus();
                flag = true;
            }


        });
//dept field

        department.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                department.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");


                flag = false;
            }

        });
        department.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (department.getText().matches("[A-Za-z_\\s]{2,25}")) {
                //department.setEditable(false);
                department.setBorder(null);
            } else {

                department.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                department.setVisible(true);
                department.requestFocus();
                flag = true;
            }});
//email field

        email.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                email.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");


                flag = false;
            }

        });
        email.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (email.getText().matches("[A-Za-z0-9]{3,15}@farmingdale.edu")) {
                //email.setEditable(false);
                email.setBorder(null);
            } else {

                email.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                email.setVisible(true);
                email.requestFocus();
                flag = true;
            }});


        first_name.setBorder(null);
        last_name.setBorder(null);
        email.setBorder(null);
        department.setBorder(null);

    }

    @FXML
    public void importCSV(){

    }
    @FXML
    public void exportCSV() {

        }



    @FXML
    protected void addNewRecord() {

            Person p = new Person(first_name.getText(), last_name.getText(), department.getText(),
                    major.getSelectionModel().getSelectedItem().toString(), email.getText(), imageURL.getText());
            cnUtil.insertUser(p);
            cnUtil.retrieveId(p);
            p.setId(cnUtil.retrieveId(p));
            data.add(p);

        first_name.setBorder(null);
        last_name.setBorder(null);
        email.setBorder(null);
        department.setBorder(null);

        BottomText.setText("Record Successfully Added");
    }

    @FXML
    protected void clearForm() {
        first_name.setText("");
        last_name.setText("");
        department.setText("");

        email.setText("");
        imageURL.setText("");

        EditButton.setDisable(true);
        DeleteButton.setDisable(true);

        first_name.setBorder(null);
        last_name.setBorder(null);
        email.setBorder(null);
        department.setBorder(null);

        BottomText.setText("");
    }



    @FXML
    protected void logOut(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").getFile());
            Stage window = (Stage) menuBar.getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    @FXML
    protected void displayAbout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/about.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void editRecord() {
        Person p = tv.getSelectionModel().getSelectedItem();
        int index = data.indexOf(p);
        Person p2 = new Person(index + 1, first_name.getText(), last_name.getText(), department.getText(),
                major.getSelectionModel().getSelectedItem().toString(), email.getText(),  imageURL.getText());
        cnUtil.editUser(p.getId(), p2);
        data.remove(p);
        data.add(index, p2);
        tv.getSelectionModel().select(index);

        first_name.setBorder(null);
        last_name.setBorder(null);
        email.setBorder(null);
        department.setBorder(null);

        BottomText.setText("Record Successfully Edited");
    }

    @FXML
    protected void deleteRecord() {
        Person p = tv.getSelectionModel().getSelectedItem();
        int index = data.indexOf(p);
        cnUtil.deleteRecord(p);
        data.remove(index);
        tv.getSelectionModel().select(index);

        first_name.setBorder(null);
        last_name.setBorder(null);
        email.setBorder(null);
        department.setBorder(null);

        BottomText.setText("Record Successfully Deleted");
    }

    @FXML
    protected void showImage() {
        File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if (file != null) {
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    protected void addRecord() {
        showSomeone();
    }

    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p = tv.getSelectionModel().getSelectedItem();
        first_name.setText(p.getFirstName());
        last_name.setText(p.getLastName());
        department.setText(p.getDepartment());
        major.setSelectionModel(major.getSelectionModel());
        major.getSelectionModel().select(p.getMajor());
        major.setSelectionModel(major.getSelectionModel());
        email.setText(p.getEmail());
        imageURL.setText(p.getImageURL());

        EditButton.setDisable(false);
        DeleteButton.setDisable(false);

        first_name.setBorder(null);
        last_name.setBorder(null);
        email.setBorder(null);
        department.setBorder(null);

        BottomText.setText("");
    }

    public void lightTheme(ActionEvent actionEvent) {
        try {
            Scene scene = menuBar.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            System.out.println("light " + scene.getStylesheets());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void darkTheme(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/darkTheme.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSomeone() {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("New User");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField1 = new TextField("Name");
        TextField textField2 = new TextField("Last Name");
        TextField textField3 = new TextField("Email ");
        ObservableList<Major> options =
                FXCollections.observableArrayList(Major.values());
        ComboBox<Major> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(new VBox(8, textField1, textField2,textField3, comboBox));
        Platform.runLater(textField1::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Results(textField1.getText(),
                        textField2.getText(), comboBox.getValue());
            }
            return null;
        });
        Optional<Results> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Results results) -> {
            MyLogger.makeLog(
                    results.fname + " " + results.lname + " " + results.major);
        });
    }

    private static enum Major {Business, CSC, CPIS}

    private static class Results {

        String fname;
        String lname;
        Major major;

        public Results(String name, String date, Major venue) {
            this.fname = name;
            this.lname = date;
            this.major = venue;
        }
    }

}