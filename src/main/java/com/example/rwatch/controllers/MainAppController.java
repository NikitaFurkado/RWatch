package com.example.rwatch.controllers;

import com.example.rwatch.MainApplication;
import com.example.rwatch.db.JdbcDao;
import com.example.rwatch.models.Person;
import com.example.rwatch.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {

        @FXML
        private TableView<Person> tableColumn;
        @FXML
        private TableColumn<Person, String> firstName;
        @FXML
        private TableColumn<Person, String> lastName;
        @FXML
        private Label fNameL, sNameL, lNameL, bDayL, regMess, patMess;
        @FXML
        private Label numL;
        @FXML
        private Label serialL, numsL, cityL;
        @FXML
        private Label regL, regForL, patL, patForL;
        private MainApplication mainApplication;
        ObservableList<Person> personObservableList;

        public MainAppController() {}


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            JdbcDao jdbcDao = new JdbcDao();
            try {
                Connection connection = jdbcDao.getConnection();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            tableColumn.setItems(personObservableList);

            showPersonDetails(null);
            tableColumn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                showPersonDetails(newValue);
            });
        }

        public void setMainApplication(MainApplication mainApplication) {
            this.mainApplication = mainApplication;

            tableColumn.setItems(mainApplication.getPersonData());
        }
        @FXML
        private void daTa() throws ClassNotFoundException{
            try {
                JdbcDao jdbcDao = new JdbcDao();
                Connection connection = jdbcDao.getConnection();
                personObservableList = FXCollections.observableArrayList();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM person");
                while (resultSet.next()) {
                    personObservableList.add(new Person(
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    DateUtil.parse(resultSet.getString(4)),
                                    resultSet.getString(5),
                                    resultSet.getString(6),
                                    resultSet.getString(7),
                                    DateUtil.parse(resultSet.getString(8)),
                                    DateUtil.parse(resultSet.getString(9)),
                                    DateUtil.parse(resultSet.getString(10)),
                                    DateUtil.parse(resultSet.getString(11)),
                                    resultSet.getString(12)
                            )
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastName.setCellValueFactory(celData -> celData.getValue().lastNameProperty());

            tableColumn.setItems(personObservableList);
        }

        private void showPersonDetails(Person person) {

            LocalDate today = LocalDate.now();

            if (person != null) {
                LocalDate endReg = person.getRegTo().minusDays(30);
                LocalDate regFin = person.getRegTo();
                LocalDate endPat = person.getPatTo().minusDays(5);
                LocalDate patFin = person.getPatTo();
                if (today.equals(endReg) || today.compareTo(endReg) > 0) {
                    this.regMess.setText("Нужно обновить\n регистрацию!");
                }
                if (today.compareTo(regFin) > 0) {
                    this.regMess.setText("Просрочено!");
                }
                if (today.compareTo(endReg) < 0) {
                    this.regMess.setText("");
                }
                if (today.equals(endPat) || today.compareTo(endPat) > 0) {
                    this.patMess.setText("Нужно оплатить патент!");
                }
                if (today.compareTo(patFin) > 0) {
                    this.patMess.setText("Просрочено!");
                }
                if (today.compareTo(endPat) < 0) {
                    this.patMess.setText("");
                }
                fNameL.setText(person.getFirstName());
                sNameL.setText(person.getSureName());
                lNameL.setText(person.getLastName());
                numL.setText(person.getPhoneNumber());
                serialL.setText(person.getSerialPass());
                numsL.setText(person.getNumPass());
                cityL.setText(person.getCity());
                bDayL.setText(String.valueOf(DateUtil.format(person.getBirthDay())));
                regL.setText(String.valueOf(DateUtil.format(person.getRegFrom())));
                regForL.setText(String.valueOf(DateUtil.format(person.getRegTo())));
                patL.setText(String.valueOf(DateUtil.format(person.getPatFrom())));
                patForL.setText(String.valueOf(DateUtil.format(person.getPatTo())));
            } else {
                fNameL.setText("");
                sNameL.setText("");
                lNameL.setText("");
                numL.setText("");
                serialL.setText("");
                numsL.setText("");
                numsL.setText("");
                cityL.setText("");
                bDayL.setText("");
                regL.setText("");
                regForL.setText("");
                patL.setText("");
                patForL.setText("");
                regMess.setText("");
                patMess.setText("");
            }
        }
        @FXML
        private void btnMake() throws ClassNotFoundException {
            Person person = new Person();
            boolean okClicked = mainApplication.showAddPerson(person);
            daTa();
            if (okClicked) {
                mainApplication.getPersonData().add(person);
            }
        }
        @FXML
        private void btnChange() throws ClassNotFoundException {
            Person person = tableColumn.getSelectionModel().getSelectedItem();
            if (person != null) {
                boolean okClicked = mainApplication.showChangePerson(person);
                if (okClicked) {
                    showPersonDetails(null);
                    daTa();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApplication.getPrimaryStage());
                alert.setTitle("Ничего не выбрано!");
                alert.setHeaderText("Нет данных!");
                alert.setContentText("Создать!");

                alert.showAndWait();
            }
        }
        @FXML
        private void btnDelete() throws ClassNotFoundException {
            try {
                JdbcDao jdbcDao = new JdbcDao();
                Connection connection = jdbcDao.getConnection();
                String sql = "DELETE FROM person WHERE fisrtname = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, fNameL.getText());
                statement.execute();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int selectionIndex = tableColumn.getSelectionModel().getSelectedIndex();
            if (selectionIndex >= 0) {
                tableColumn.getItems().remove(selectionIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApplication.getPrimaryStage());
                alert.setHeaderText("Нет данных!");
                alert.setTitle("Нет данных!");
                alert.setContentText("Заполните данные!");

                alert.showAndWait();
            }
        }
}