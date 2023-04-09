package com.example.rwatch.controllers;

import com.example.rwatch.db.JdbcDao;
import com.example.rwatch.models.Person;
import com.example.rwatch.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePersonController {

    @FXML
    private Label fName, sName, lName, bDay, serial, num, city;
    @FXML
    private TextField phone;
    @FXML
    private DatePicker regfrom, regto, patfrom, patto;

    private Person person;
    private Stage dialogStage;
    private boolean okClicked = false;

    public ChangePersonController() {}

    @FXML
    private void initialize() {}

    public void setPerson(Person person) {
        this.person = person;

        fName.setText(person.getFirstName());
        sName.setText(person.getSureName());
        lName.setText(person.getLastName());
        bDay.setText(String.valueOf(DateUtil.format(person.getBirthDay())));
        phone.setText(person.getPhoneNumber());
        serial.setText(person.getSerialPass());
        num.setText(person.getNumPass());
        city.setText(person.getCity());
        regfrom.setValue(person.getRegFrom());
        regto.setValue(person.getRegTo());
        patfrom.setValue(person.getPatFrom());
        patto.setValue(person.getPatTo());
    }

    public void setDialogStage(Stage dialogStage) {this.dialogStage = dialogStage;}

    public boolean isOkClicked() {return okClicked;}

    @FXML
    private void btnSave() throws SQLException, ClassNotFoundException {
        if (isInputValid()) {
            person.setPhoneNumber(phone.getText());
            person.setRegFrom(DateUtil.parse(String.valueOf(regfrom.getValue())));
            person.setRegTo(DateUtil.parse(String.valueOf(regto.getValue())));
            person.setPatFrom(DateUtil.parse(String.valueOf(patfrom.getValue())));
            person.setPatTo(DateUtil.parse(String.valueOf(patto.getValue())));

            String phoneNumber = phone.getText();
            String regFrom = String.valueOf(DateUtil.format(regfrom.getValue()));
            String regTo = String.valueOf(DateUtil.format(regto.getValue()));
            String patFrom = String.valueOf(DateUtil.format(patfrom.getValue()));
            String patTo = String.valueOf(DateUtil.format(patto.getValue()));

            String sql = "UPDATE person SET phonenumber = '"+phoneNumber+"', regfrom = '"+regFrom+"', " +
                    "regto = '"+regTo+"', patfrom = '"+patFrom+"', patto = '"+patTo+"' WHERE fisrtname = ?";

            JdbcDao dao = new JdbcDao();
            Connection connection = dao.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fName.getText());
            statement.execute();

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void btnCancel() {dialogStage.close();}

    private boolean isInputValid() {
        String errorMessage = "";
        if (regfrom.getConverter().toString() == null || regfrom.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата начала регистрации!\n";
        }
        if (regto.getConverter().toString() == null || regto.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата окончания регистрации!\n";
        }
        if (patfrom.getConverter().toString() == null || patfrom.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата регистрации патента!\n";
        }
        if (patto.getConverter().toString() == null || patto.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата окончания патента!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Поля введены некорректно!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
