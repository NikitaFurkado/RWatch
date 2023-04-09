package com.example.rwatch.controllers;

import com.example.rwatch.db.JdbcDao;
import com.example.rwatch.models.Person;
import com.example.rwatch.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPersonController {

    @FXML
    private TextField fNameF;
    @FXML
    private TextField sNameF;
    @FXML
    private TextField lNameF;
    @FXML
    private TextField numF;
    @FXML
    private TextField serialF, numsF, cityF;
    @FXML
    private DatePicker bDayP, regP, regForP, patP, patForP;

    private Person person;
    private Stage dialogStage;
    private boolean okClicked = false;

    public AddPersonController(){}

    @FXML
    private void initialize() {}

    public void setPerson(Person person) {
        this.person = person;

        fNameF.setText(person.getFirstName());
        sNameF.setText(person.getSureName());
        lNameF.setText(person.getLastName());

        numF.setText(person.getPhoneNumber());

        serialF.setText(person.getSerialPass());
        numsF.setText(person.getNumPass());
        cityF.setText(person.getCity());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isOkClicked(){
        return okClicked;
    }

    @FXML
    private void btnSave() throws Exception {
        if (isInputValid()) {
            person.setFirstName(fNameF.getText());
            person.setSureName(sNameF.getText());
            person.setLastName(lNameF.getText());

            person.setPhoneNumber(numF.getText());

            person.setSerialPass(serialF.getText());
            person.setNumPass(numsF.getText());
            person.setCity(cityF.getText());

            person.setBirthDay(DateUtil.parse(String.valueOf(bDayP.getValue())));
            person.setRegFrom(DateUtil.parse(String.valueOf(regP.getValue())));
            person.setRegTo(DateUtil.parse(String.valueOf(regForP.getValue())));
            person.setPatFrom(DateUtil.parse(String.valueOf(patP.getValue())));
            person.setPatTo(DateUtil.parse(String.valueOf(patForP.getValue())));

            String firstName = fNameF.getText();
            String sureName = sNameF.getText();
            String lastName = lNameF.getText();
            String phoneNumber = numF.getText();
            String serialPass = serialF.getText();
            String numPass = numsF.getText();
            String city = cityF.getText();
            String birthDay = String.valueOf(DateUtil.format(bDayP.getValue()));
            String regOne = String.valueOf(DateUtil.format(regP.getValue()));
            String regTo = String.valueOf(DateUtil.format(regForP.getValue()));
            String patOne = String.valueOf(DateUtil.format(patP.getValue()));
            String patTo = String.valueOf(DateUtil.format(patForP.getValue()));

            okClicked = true;
            JdbcDao jdbcDao = new JdbcDao();
            jdbcDao.insertQuery(firstName, sureName, lastName, birthDay, phoneNumber, serialPass, numPass,
                    regOne, regTo, patOne, patTo, city);
            dialogStage.close();
        }
    }

    @FXML
    private void btnCancel() {dialogStage.close();}

    private boolean isInputValid() {
        String errorMessage = "";

        if (fNameF.getText() == null || fNameF.getText().length() == 0) {
            errorMessage += "Не введено имя!\n";
        }
        if (sNameF.getText() == null || sNameF.getText().length() == 0) {
            errorMessage += "Не введено отчество!\n";
        }
        if (lNameF.getText() == null || lNameF.getText().length() == 0 ) {
            errorMessage += "Не введена фамилич!\n";
        }
        if (numF.getText() == null || numsF.getText().length() == 0) {
            errorMessage += "Не введен номер телефона!\n";
        }
        if (serialF.getText() == null || serialF.getText().length() == 0) {
            errorMessage += "Не введена серия паспорта!\n";
        }
        if (numsF.getText() == null || numsF.getText().length() == 0) {
            errorMessage += "Не введен номер паспорта!\n";
        }
        if (cityF.getText() == null || cityF.getText().length() == 0) {
            errorMessage += "Не введен город!\n";
        }
        if (bDayP.getConverter().toString() == null || bDayP.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата рождения!\n";
        }
        if (regP.getConverter().toString() == null || regP.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата начала регистрации!\n";
        }
        if (regForP.getConverter().toString() == null || regForP.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата окончания регистрации!\n";
        }
        if (patP.getConverter().toString() == null || patP.getConverter().toString().length() == 0) {
            errorMessage += "Не введена дата регистрации патента!\n";
        }
        if (patForP.getConverter().toString() == null || patForP.getConverter().toString().length() == 0) {
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
