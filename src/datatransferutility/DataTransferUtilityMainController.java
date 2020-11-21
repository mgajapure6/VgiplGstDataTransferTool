/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatransferutility;

import animatefx.animation.SlideInLeft;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Mayur gajapure
 */
public class DataTransferUtilityMainController implements Initializable {
    
    private static final Logger LOGGER = Logger.getLogger(DataTransferUtilityMainController.class);
    

    @FXML
    StackPane root;

    @FXML
    Button transferBtn;

//    @FXML
//    Button closeWindowBtn;
    @FXML
    Button connectNowBtn;

    @FXML
    Button clearTblBtn;

    @FXML
    JFXDialog confirmDialog;

    @FXML
    JFXDialog editQueryDialog;

    @FXML
    JFXDialog alertDialog;

    @FXML
    Label connectingErr;

    @FXML
    ComboBox<DBType> databaseTypeSelect;

    @FXML
    TextField dbhost;

    @FXML
    TextField dbport;

    @FXML
    TextField dbname;

    @FXML
    TextField gstin_no;

    @FXML
    TextField userName;

    @FXML
    TextField password;

//    @FXML
//    TextArea sqlTextBox;
    @FXML
    Label err_db_type;

    @FXML
    Label err_db_name;

    @FXML
    Label err_host;

    @FXML
    Label err_port;

    @FXML
    Label err_username;

    @FXML
    Label err_gstn;

    @FXML
    Label err_password;

    @FXML
    Button loadDataBtn;

    @FXML
    Button connectDbBtn;

    ObservableList<DBType> dbtypeObservableList = FXCollections.observableArrayList();

    @FXML
    VBox tableViewBox;

    @FXML
    DatePicker datepicker;

    @FXML
    ProgressIndicator progressIndicator;

    @FXML
    Label dataPostMsg;

    @FXML
    TextField loginUsergstin;

    @FXML
    TextField loginUsername;

    @FXML
    PasswordField loginUserpass;

    @FXML
    Button loginBtn;

    @FXML
    Button editQueryBtn;

    @FXML
    Button loginCancelBtn;

    @FXML
    JFXDialog loginDialog;

    @FXML
    JFXDialog paramDialog;

    @FXML
    VBox paramListVbox;

    @FXML
    Label ErrorMessage;

    @FXML
    ProgressIndicator loginProgress;

    Connection connection;

    @FXML
    TabPane queryTabPane;

//    @FXML
//    Button addNewQueryBtn;
    @FXML
    Button closeQueryDialogBtn;

    @FXML
    ComboBox<JsonObject> querySelector;

    ObservableList<JsonObject> queryObservableList = FXCollections.observableArrayList();

    @FXML
    Button exportExcelBtn;

    @FXML
    Button saveSellQueryBtn;

    @FXML
    Button savePurchaseQueryBtn;

    @FXML
    Tab sellTab;

    @FXML
    Tab purchaseTab;

    @FXML
    Button paramSaveBtn;

    @FXML
    ComboBox<String> monthSelect;

    @FXML
    ComboBox<String> yearSelect;

    private String globalSqlTobeExecute = null;

    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //tableViewBox.setF
        datepicker.setValue(LocalDate.now());
        //loadNowBtn.setVisible(false);
        transferBtn.setDisable(true);
        progressIndicator.setVisible(false);
        dataPostMsg.setText("");
        progressIndicator.setStyle("-fx-progress-color: red;");

        confirmDialog.setDialogContainer(root);
        editQueryDialog.setDialogContainer(root);
        editQueryDialog.setPadding(new Insets(0, 0, 0, 0));
        alertDialog.setDialogContainer(root);
        loginDialog.setDialogContainer(root);
        paramDialog.setDialogContainer(root);
        //Map<String, String> mapV = readProperty();
        //sqlTextBox.setText(mapV.get("sql"));

        dbtypeObservableList.add(new DBType("", "Select Database Server"));
        dbtypeObservableList.add(new DBType("MSSQL", "Mircosoft SQL Server"));
        dbtypeObservableList.add(new DBType("MYSQL", "MySQL Database Server"));
        dbtypeObservableList.add(new DBType("ORACLE", "Oracle Databse Server"));
        dbtypeObservableList.add(new DBType("POSTGRES", "Postgres SQL Server"));

        databaseTypeSelect.getItems().addAll(dbtypeObservableList);

        Map<String, String> mapValues = readProperty();
        dbhost.setText(mapValues.get("host"));
        dbport.setText(mapValues.get("port"));
        userName.setText(mapValues.get("username"));
        password.setText(mapValues.get("password"));
        gstin_no.setText(mapValues.get("gstin"));
        dbname.setText(mapValues.get("dbname"));

        dbtypeObservableList.stream().filter((dBType) -> (dBType.getType().equals(mapValues.get("server")))).forEachOrdered((dBType) -> {
            databaseTypeSelect.getSelectionModel().select(dBType);
        });

        connectDbBtn.setOnAction(e -> {
            confirmDialog.setTransitionType(DialogTransition.CENTER);
            confirmDialog.show();
        });

        err_db_type.setVisible(false);
        err_db_name.setVisible(false);
        err_host.setVisible(false);
        err_port.setVisible(false);
        err_username.setVisible(false);
        err_gstn.setVisible(false);
        err_password.setVisible(false);
        //err_sql.setVisible(false);

        connectNowBtn.setOnAction(e -> {
            createConnection();
        });

        databaseTypeSelect.valueProperty().addListener(new ChangeListener<DBType>() {
            @Override
            public void changed(ObservableValue<? extends DBType> observable, DBType oldValue, DBType newValue) {
                connectingErr.setText("");
                if (!isValidDatabaseType()) {
                    if (newValue.getType().isEmpty()) {
                        err_db_type.setVisible(true);
                        new SlideInLeft(err_db_type).play();
                    } else {
                        err_db_type.setVisible(false);
                    }
                } else {
                    err_db_type.setVisible(false);
                }
            }
        });

        dbhost.focusedProperty().addListener((observable, oldValue, newValue) -> {
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidTextField(dbhost)) {
                if (!newValue) {
                    err_host.setVisible(true);
                    new SlideInLeft(err_host).play();
                } else {
                    err_host.setVisible(false);
                }
            } else {
                err_host.setVisible(false);
            }
        });

        dbport.focusedProperty().addListener((observable, oldValue, newValue) -> {
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidTextField(dbhost)) {
                if (!newValue) {
                    err_port.setVisible(true);
                    new SlideInLeft(err_port).play();
                } else {
                    err_port.setVisible(false);
                }
            } else {
                err_port.setVisible(false);
            }
        });

        dbname.focusedProperty().addListener((observable, oldValue, newValue) -> {
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidTextField(dbhost)) {
                if (!newValue) {
                    err_db_name.setVisible(true);
                    new SlideInLeft(err_db_name).play();
                } else {
                    err_db_name.setVisible(false);
                }
            } else {
                err_db_name.setVisible(false);
            }
        });

        userName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidTextField(dbhost)) {
                if (!newValue) {
                    err_username.setVisible(true);
                    new SlideInLeft(err_username).play();
                } else {
                    err_username.setVisible(false);
                }
            } else {
                err_username.setVisible(false);
            }
        });

        password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidTextField(dbhost)) {
                if (!newValue) {
                    err_password.setVisible(true);
                    new SlideInLeft(err_password).play();
                } else {
                    err_password.setVisible(false);
                }
            } else {
                err_password.setVisible(false);
            }
        });

        gstin_no.focusedProperty().addListener((observable, oldValue, newValue) -> {
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidTextField(gstin_no)) {
                if (!newValue) {
                    err_gstn.setVisible(true);
                    new SlideInLeft(err_gstn).play();
                } else {
                    err_gstn.setVisible(false);
                }
            } else {
                err_gstn.setVisible(false);
            }
        });

        paramSaveBtn.setOnAction(e -> {
            ListView<String> paramList = (ListView) paramListVbox.lookup(".paramList");
            System.out.println("paramList size::" + paramList.getItems().size());
            System.out.println("paramList::" + Arrays.toString(paramList.getItems().toArray()));

//            String sqlToBeExecuted = querySelector.getSelectionModel().getSelectedItem().get("text").getAsString();
//            List<String> paramValues = new ArrayList<>();
//            String whereStr = sqlToBeExecuted.substring(sqlToBeExecuted.toLowerCase().indexOf("where") + 5);
//            Expression expr = null;
//            try {
//                expr = CCJSqlParserUtil.parseCondExpression(whereStr);
//            } catch (JSQLParserException ex) {
//                Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            expr.accept(new ExpressionVisitorAdapter() {
//
//                @Override
//                protected void visitBinaryExpression(BinaryExpression expr) {
//                    if (expr.getRightExpression().toString().contains(":") && ) {
//                        paramNames.add(expr.getLeftExpression().toString());
//                    }
//                    super.visitBinaryExpression(expr);
//                }
//            });
//            System.out.println("paramNames::" + paramNames);
//            for (String name : paramNames) {
//                paramList.getItems().add(new PARAM(name.trim(), new TextField()));
//            }

            Object[] paramVals = paramList.getItems().toArray();
            if (querySelector.getSelectionModel().getSelectedItem() != null) {
                String sqlToBeExecuted = querySelector.getSelectionModel().getSelectedItem().get("text").getAsString();
                for (int i = 0; i < paramList.getItems().size(); i++) {
                    System.out.println("paramList.getItems().get(i)::" + paramVals[i]);
                    sqlToBeExecuted = sqlToBeExecuted.replace(":" + (i + 1), String.valueOf(paramVals[i]));
                }
                System.out.println("new Query::" + sqlToBeExecuted);
                globalSqlTobeExecute = sqlToBeExecuted;
                try {
                    ResultSet rs = getResultSetFromDb();
                    if (rs != null) {
                        queryToTable(rs);
                        confirmDialog.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                }
            }
            paramDialog.close();
        });

        loadDataBtn.setOnAction(e -> {
            System.out.println("loadDataBtn Action");
            if (querySelector.getSelectionModel().getSelectedItem() != null) {
                String sqlToBeExecuted = querySelector.getSelectionModel().getSelectedItem().get("text").getAsString();
                if (sqlToBeExecuted.contains(":")) {
                    paramListVbox.getChildren().clear();
                    if (sqlToBeExecuted.toLowerCase().contains("where")) {
                        ListView<PARAM> paramList = new ListView<>();
                        paramList.getStyleClass().add("paramList");
                        paramList.setCellFactory(new Callback<ListView<PARAM>, ListCell<PARAM>>() {
                            @Override
                            public ListCell<PARAM> call(ListView<PARAM> param) {
                                return new XCell();
                            }
                        });

                        List<String> paramNames = new ArrayList<>();
                        String whereStr = sqlToBeExecuted.substring(sqlToBeExecuted.toLowerCase().indexOf("where") + 5);
                        Expression expr = null;
                        try {
                            expr = CCJSqlParserUtil.parseCondExpression(whereStr);
                        } catch (JSQLParserException ex) {
                            Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                        }
                        expr.accept(new ExpressionVisitorAdapter() {

                            @Override
                            protected void visitBinaryExpression(BinaryExpression expr) {
                                if (!expr.getStringExpression().equalsIgnoreCase("AND") && !expr.getStringExpression().equalsIgnoreCase("OR") && !expr.getStringExpression().equalsIgnoreCase("||")) {
                                    if (expr.getRightExpression().toString().contains(":")) {
                                        paramNames.add(expr.getLeftExpression().toString());
                                        System.out.println("left->" + expr.getLeftExpression() + "  op->" + expr.getStringExpression() + "  right->" + expr.getRightExpression());
                                    }
                                }

                                super.visitBinaryExpression(expr);
                            }
                        });
                        System.out.println("paramNames::" + paramNames);
                        for (String name : paramNames) {
                            paramList.getItems().add(new PARAM(name.trim(), new TextField()));
                        }

                        paramListVbox.getChildren().add(paramList);
                        paramDialog.show();
                    }else if(sqlToBeExecuted.toLowerCase().contains("exec")){
                        ListView<PARAM> paramList = new ListView<>();
                        paramList.getStyleClass().add("paramList");
                        paramList.setCellFactory(new Callback<ListView<PARAM>, ListCell<PARAM>>() {
                            @Override
                            public ListCell<PARAM> call(ListView<PARAM> param) {
                                return new XCell();
                            }
                        });
                        
                        List<String> paramNames = new ArrayList<>();
                        String whereStr = sqlToBeExecuted.substring(sqlToBeExecuted.toLowerCase().indexOf(sqlToBeExecuted.toLowerCase().trim().split(" ")[2]), sqlToBeExecuted.length());
                        if(whereStr.contains(",")){
                            whereStr = whereStr.replaceAll(",", " AND ");
                        }
                        Expression expr = null;
                        try {
                            expr = CCJSqlParserUtil.parseCondExpression(whereStr);
                        } catch (JSQLParserException ex) {
                            Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                        }
                        expr.accept(new ExpressionVisitorAdapter() {

                            @Override
                            protected void visitBinaryExpression(BinaryExpression expr) {
                                if (!expr.getStringExpression().equalsIgnoreCase("AND") && !expr.getStringExpression().equalsIgnoreCase("OR") && !expr.getStringExpression().equalsIgnoreCase("||")) {
                                    if (expr.getRightExpression().toString().contains(":")) {
                                        String pName = expr.getLeftExpression().toString().contains("@") ? expr.getLeftExpression().toString().replace("@","") : expr.getLeftExpression().toString();
                                        paramNames.add(pName);
                                        System.out.println("left->" + expr.getLeftExpression() + "  op->" + expr.getStringExpression() + "  right->" + expr.getRightExpression());
                                    }
                                }
                                super.visitBinaryExpression(expr);
                            }
                        });
                        System.out.println("paramNames::" + paramNames);
                        for (String name : paramNames) {
                            paramList.getItems().add(new PARAM(name.trim(), new TextField()));
                        }

                        paramListVbox.getChildren().add(paramList);
                        paramDialog.show();
                    }
                } else {
                    globalSqlTobeExecute = sqlToBeExecuted;
                    try {
                        ResultSet rs = getResultSetFromDb();
                        if (rs != null) {
                            queryToTable(rs);
                            confirmDialog.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                    }
                }

            } else {
                dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                dataPostMsg.setText("Please select query to execute.");
            }

        });

        transferBtn.setOnAction(e -> {
            dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
            dataPostMsg.setText("Please wait...Data Transfer in progress");
            //root.setDisable(true);
            progressIndicator.setVisible(true);

            setTimeout(() -> {
                Platform.runLater(() -> {
                    Map<String, String> mValues = readProperty();
                    loginUsergstin.setText(mValues.get("gstin"));
                    loginUsername.setText("");
                    loginUserpass.setText("");
                    loginProgress.setVisible(false);
                    ErrorMessage.setText("");
                    dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
                    dataPostMsg.setText("We need your login.. to comunicate with server");
                    loginDialog.setTransitionType(DialogTransition.CENTER);
                    loginDialog.show();
                    loginDialog.setOverlayClose(false);
                });

            }, 1000);
        });

        loginBtn.setOnAction(e -> {
            if (isValidTextField(loginUsername) && isValidTextField(loginUserpass)) {
                loginProgress.setVisible(true);
                setTimeout(() -> {
                    try {

                        String encodedPassword = loginUserpass.getText();
                        for (int i = 0; i < loginUsername.getText().length(); i++) {
                            encodedPassword = Base64.getEncoder().encodeToString(encodedPassword.getBytes());
                        }
                        JsonObject obj = new JsonObject();
                        obj.addProperty("gstn", loginUsergstin.getText());
                        obj.addProperty("username", loginUsername.getText());
                        obj.addProperty("password", encodedPassword);
                        String result = VgiplHttpClient.authenticateClient(obj);
                        JsonParser parser = new JsonParser();
                        JsonElement respObj = parser.parse(result);
                        JsonObject jobj = respObj.getAsJsonObject();
                        System.out.println("login jobj::" + jobj);
//                        returnMap.put("status", "success");
//			returnMap.put("userName", userObj.getUserName());
//			returnMap.put("userId", userObj.getUserId());
//			returnMap.put("gstno", userObj.getGstinUinNoBranchWise());
//			returnMap.put("gstnId", userObj.getGstinIdBranchWise());
                        if (jobj.get("status").getAsString().equals("success")) {
                            Platform.runLater(() -> {
                                ErrorMessage.setText("Login Success.");
                                dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
                                dataPostMsg.setText("Login Success.. now communicating with server..");
                                loginProgress.setVisible(false);
                            });
                            setTimeout(() -> {
                                Platform.runLater(() -> {
                                    loginDialog.close();
                                });
                            }, 500);

                            root.setDisable(true);
                            setTimeout(() -> {
                                try {
                                    ResultSet rs = getResultSetFromDb();
                                    String data = convertResultSetToJson(rs);
                                    String flag = querySelector.getSelectionModel().getSelectedItem().get("flag").getAsString();
                                    String monthYear = (monthSelect.getSelectionModel().getSelectedIndex() + 1) + "-" + (yearSelect.getSelectionModel().getSelectedItem());
                                    System.out.println("monthYear::" + monthYear);
                                    System.out.println("date::" + datepicker.getValue().format(DateTimeFormatter.ISO_DATE));
                                    String sendResult = VgiplHttpClient.sendData(data, jobj, datepicker.getValue().format(DateTimeFormatter.ISO_DATE), flag, monthYear);
                                    root.setDisable(false);
                                    progressIndicator.setVisible(false);
                                    if (sendResult.equals("success")) {
                                        Platform.runLater(() -> {
                                            dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
                                            dataPostMsg.setText("Data Transfered successfully");
                                        });
                                    } else {
                                        Platform.runLater(() -> {
                                            dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                                            dataPostMsg.setText("Server error occured");
                                        });
                                    }
                                } catch (Exception ex) {
                                    Platform.runLater(() -> {
                                        root.setDisable(false);
                                        progressIndicator.setVisible(false);
                                        dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                                        dataPostMsg.setText("Exception occured while transfering data to server.");
                                    });
                                    Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                                }
                            }, 1000);

                        } else {
                            Platform.runLater(() -> {
                                ErrorMessage.setText("Invalid Username or Password");
                                loginProgress.setVisible(false);
                            });
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                    }
                }, 2000);
            } else {
                ErrorMessage.setText("Please enter Username and Password");
            }
        });

        loginCancelBtn.setOnAction(e -> {
            ErrorMessage.setText("");
            dataPostMsg.setText("");
            loginProgress.setVisible(false);
            loginDialog.close();
            progressIndicator.setVisible(false);
        });

        clearTblBtn.setOnAction(e -> {
            transferBtn.setDisable(true);
            tableViewBox.getChildren().clear();
            TableView tableView = new TableView();
            tableView.setStyle("-fx-focus-color:TRANSPARENT");
            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            HBox.setHgrow(tableView, Priority.ALWAYS);
            VBox.setVgrow(tableView, Priority.ALWAYS);
            tableViewBox.getChildren().add(tableView);
            dataPostMsg.setText("");
        });

//        clearTblBtn.getScene().getWindow().setOnHiding( event -> {
//            System.err.println("Closing");
//             try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            Platform.exit();
//            System.exit(0);
//        });
//        closeWindowBtn.setOnAction(e -> {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            Platform.exit();
//            System.exit(0);
//        });
        editQueryBtn.setOnAction(e -> {
            //queryTabPane.getTabs().removeAll(queryTabPane.getTabs());
            List<Tab> removableTabs = new ArrayList<>();
            for (Tab tab : queryTabPane.getTabs()) {
                TextField flagF = (TextField) tab.getContent().lookup(".flag_f");
                if (flagF.getText().equals("O")) {
                    removableTabs.add(tab);
                }
            }

            if (removableTabs.size() > 0) {
                queryTabPane.getTabs().removeAll(removableTabs);
            }
            JsonArray ja = readJsonFile();
            System.out.println("ja" + ja.size());
            if (!ja.isJsonNull() && ja.size() > 0) {
                for (JsonElement je : ja) {
                    if (je.getAsJsonObject().get("flag").getAsString().equals("S")) {
                        TextField t1 = (TextField) sellTab.getContent().lookup(".id_f");
                        t1.setText("1");
                        TextField t2 = (TextField) sellTab.getContent().lookup(".title_f");
                        t2.setText("Sell");
                        ComboBox c1 = (ComboBox) sellTab.getContent().lookup(".query_type_f");
                        c1.getSelectionModel().select(je.getAsJsonObject().get("type").getAsString());
                        TextArea t3 = (TextArea) sellTab.getContent().lookup(".query_f");
                        t3.setText(je.getAsJsonObject().get("text").getAsString());
                        TextField t4 = (TextField) sellTab.getContent().lookup(".flag_f");
                        t4.setText("S");
                    } else if (je.getAsJsonObject().get("flag").getAsString().equals("P")) {
                        TextField t1 = (TextField) purchaseTab.getContent().lookup(".id_f");
                        t1.setText("2");
                        TextField t2 = (TextField) purchaseTab.getContent().lookup(".title_f");
                        t2.setText("Purchase");
                        ComboBox c1 = (ComboBox) purchaseTab.getContent().lookup(".query_type_f");
                        c1.getSelectionModel().select(je.getAsJsonObject().get("type").getAsString());
                        TextArea t3 = (TextArea) purchaseTab.getContent().lookup(".query_f");
                        t3.setText(je.getAsJsonObject().get("text").getAsString());
                        TextField t4 = (TextField) purchaseTab.getContent().lookup(".flag_f");
                        t4.setText("P");
                    } else {
                        //addNewTab(je.getAsJsonObject());
                    }
                }
            } else {
                //addNewTab(null);
            }
            editQueryDialog.setTransitionType(DialogTransition.CENTER);
            editQueryDialog.show();
        });

//        addNewQueryBtn.setOnAction(e -> {
//            addNewTab(null);
//        });
        closeQueryDialogBtn.setOnAction(e -> {
            editQueryDialog.close();
        });

        querySelector.getItems().addAll(queryObservableList);

        updateQuerySelection();

        querySelector.setConverter(new StringConverter<JsonObject>() {

            @Override
            public String toString(JsonObject object) {
                return object.get("title").getAsString();
            }

            @Override
            public JsonObject fromString(String string) {
                return querySelector.getItems().stream().filter(jo
                        -> jo.get("title").getAsString().equals(string)).findFirst().orElse(null);
            }
        });

        exportExcelBtn.setOnAction(e -> {

            TableView tableView = (TableView) exportExcelBtn.getScene().lookup("#tableView");
            if (tableView != null) {
                FileChooser excelFileChooser = new FileChooser();
                excelFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                //excelFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
                excelFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xls", "*.xls"));
                excelFileChooser.setInitialFileName(querySelector.getSelectionModel().getSelectedItem().get("title").getAsString() + "_" + DateFormate.dateTimeWithSecondWithoitSpaceToString(new Date()) + "");
                excelFileChooser.setTitle("Save Excel File");
                File file = excelFileChooser.showSaveDialog(exportExcelBtn.getScene().getWindow());
                ExcelExport ee = new ExcelExport();
                ee.export(tableView, file);
            }
        });

        saveSellQueryBtn.setOnAction(e -> {
            //TextField idF = (TextField) sellTab.getContent().lookup(".id_f");
            Label err_lbl = (Label) sellTab.getContent().lookup(".err_lbl");
            TextField titleF = (TextField) sellTab.getContent().lookup(".title_f");
            TextArea queryF = (TextArea) sellTab.getContent().lookup(".query_f");

            saveQueryToFile(titleF, queryF, err_lbl);
        });

        savePurchaseQueryBtn.setOnAction(e -> {
            //TextField idF = (TextField) purchaseTab.getContent().lookup(".id_f");
            Label err_lbl = (Label) purchaseTab.getContent().lookup(".err_lbl");
            TextField titleF = (TextField) purchaseTab.getContent().lookup(".title_f");
            TextArea queryF = (TextArea) purchaseTab.getContent().lookup(".query_f");

            saveQueryToFile(titleF, queryF, err_lbl);
        });

        for (int i = 0; i < months.length; i++) {
            monthSelect.getItems().add(months[i]);
        }

        yearSelect.getItems().add(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 2));
        yearSelect.getItems().add(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1));
        yearSelect.getItems().add(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        monthSelect.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
        yearSelect.getSelectionModel().select(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

    }

    private boolean isValidTextField(TextField tf) {
        if (tf.getText() == null || tf.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidTextArea(TextArea ta) {
        if (ta.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidDatabaseType() {
        System.out.println("databaseTypeSelect.getSelectionModel().isEmpty():" + databaseTypeSelect.getSelectionModel().isEmpty());
        if (databaseTypeSelect.getSelectionModel().isEmpty() || databaseTypeSelect.getSelectionModel().getSelectedItem().toString().equals("")) {
            return false;
        } else {
            return true;
        }

    }

    private boolean isValidDatabaseName() {
        if (dbname.getText().isEmpty()) {
            err_db_name.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidUsername() {
        if (userName.getText().isEmpty()) {
            err_username.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPassword() {
        if (password.getText().isEmpty()) {
            err_password.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    private void saveToPropertyFile() {
        File f = new File("app.properties");

        try {
            PropertiesConfiguration properties = new PropertiesConfiguration(f);
            properties.setProperty("db.server", databaseTypeSelect.getSelectionModel().getSelectedItem().getType());
            properties.setProperty("db.host", dbhost.getText().trim());
            properties.setProperty("db.port", dbport.getText().trim());
            properties.setProperty("db.username", userName.getText().trim());
            properties.setProperty("db.password", password.getText().trim());
            properties.setProperty("db.name", dbname.getText().trim());
            properties.setProperty("gstin", gstin_no.getText().trim());
            //properties.setProperty("sql", sqlTextBox.getText().trim());
            //properties.setProperty("url", "http://localhost/GSTS/json/api/jsonClientData");
            //properties.setProperty("url", "http://203.192.225.25:8085/GSTS/json/api/jsonClientData");
            //properties.setProperty("loginUrl", "http://localhost/GSTS/json/api/clientAuthentication");
            //properties.setProperty("branchId", "1");
            properties.save();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveEmptyPropertyFile() {
        try {
            File f = new File("app.properties");
            f.createNewFile();

            try {
                PropertiesConfiguration properties = new PropertiesConfiguration(f);
                properties.setProperty("db.server", "");
                properties.setProperty("db.host", "");
                properties.setProperty("db.port", "");
                properties.setProperty("db.username", "");
                properties.setProperty("db.password", "");
                properties.setProperty("db.name", "");
                properties.setProperty("sql", "");
                properties.setProperty("url", "http://localhost/GSTS/json/api/jsonClientData");
                //properties.setProperty("url", "http://203.192.225.25:8085/GSTS/json/api/jsonClientData");
                properties.setProperty("loginUrl", "http://localhost/GSTS/json/api/clientAuthentication");
                properties.setProperty("branchId", "1");
                properties.save();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
        }
    }

    private Map<String, String> readProperty() {
        Map<String, String> mp = new HashMap<>();
        File f = new File("app.properties");
        if (!f.exists()) {
            saveEmptyPropertyFile();
        }
        try (InputStream in = new FileInputStream(f)) {
            Properties prop = new Properties();
            prop.load(in);
            mp.put("server", prop.getProperty("db.server"));
            mp.put("username", prop.getProperty("db.username"));
            mp.put("host", prop.getProperty("db.host"));
            mp.put("port", prop.getProperty("db.port"));
            mp.put("password", prop.getProperty("db.password"));
            mp.put("dbname", prop.getProperty("db.name"));
            mp.put("gstin", prop.getProperty("gstin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mp;
    }

    private ResultSet getResultSetFromDb() {
        ResultSet rs = null;
        System.out.println("getResultSetFromDb Action");
        try {
            if (connection == null) {
                dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                dataPostMsg.setText("Connection Not Found");
                System.out.println("createConnection Action 1");
                createConnection();
                System.out.println("createConnection Action 2");
            }
            System.out.println("createConnection Action 3");
            if (connection != null) {
                System.out.println("createConnection Action 4");
                //String sql = querySelector.getSelectionModel().getSelectedItem().get("text").getAsString();
                String queryType = querySelector.getSelectionModel().getSelectedItem().get("type").getAsString();
                System.out.println("getResultSetFromDb sqlToBeExecuted::" + globalSqlTobeExecute);
                if (queryType.equalsIgnoreCase("Query")) {
                    PreparedStatement ps = connection.prepareStatement(globalSqlTobeExecute.trim(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                        dataPostMsg.setText("No data found");
                    } else {
                        dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
                        dataPostMsg.setText("Query executed successfully");
                    }
                } else {
                    CallableStatement stmt = connection.prepareCall(globalSqlTobeExecute);
                    stmt.execute();
                    rs = stmt.getResultSet();
                    if (!rs.next()) {
                        dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                        dataPostMsg.setText("No data found");
                    } else {
                        dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
                        dataPostMsg.setText("Procedure executed successfully");
                    }
                }

            }

            return rs;
        } catch (SQLException ex) {
            System.out.println("ex::" + ex.getMessage());
            connectingErr.setText(ex.getMessage());
            dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
            dataPostMsg.setText(ex.getMessage());
            Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
            return null;
        }
    }

    public int countResultSetRows(ResultSet res) {
        int totalRows = 0;
        try {
            res.last();
            totalRows = res.getRow();
            res.beforeFirst();
        } catch (Exception ex) {
            return 0;
        }
        return totalRows;
    }

    public static String convertResultSetToJson(ResultSet resultSet) throws SQLException {
        JsonArray jsonArr = new JsonArray();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int numColumns = metadata.getColumnCount();

        while (resultSet.next()) {
            JsonObject obj = new JsonObject();      //extends HashMap
            //iterate columns
            for (int i = 1; i <= numColumns; ++i) {
                String column_name = metadata.getColumnName(i);
                obj.addProperty(column_name, String.valueOf(resultSet.getObject(column_name)));
            }
            jsonArr.add(obj);
        }
        return jsonArr.toString();
    }

    public void queryToTable(ResultSet resultSet) throws SQLException {
        System.out.println("queryToTable");
        //ResultsetToJson.writeResultSetToJson(resultSet);
        //System.out.println("Total row:"+countResultSetRows(resultSet));
        tableViewBox.getChildren().clear();
        TableView tableView = new TableView();
        tableView.setStyle("-fx-focus-color:TRANSPARENT");
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setId("tableView");
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(tableView, Priority.ALWAYS);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        ObservableList allRowsData = FXCollections.observableArrayList();

        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        //TableColumn srcol = new TableColumn("#");
        //tableView.getColumns().addAll(srcol);
//        srcol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
//                return new SimpleStringProperty(String.valueOf(param.getValue().get(j) == null ? "" : param.getValue().get(j)));
//            }
//        });
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(String.valueOf(param.getValue().get(j) == null ? "" : param.getValue().get(j).toString()));
                }
            });
            tableView.getColumns().addAll(col);
        }

        //int rowCount = 0;
        resultSet.beforeFirst();
        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            //row.add(String.valueOf(rowCount));
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                //System.err.println("col_name::"+resultSet.getNString(i));
                //System.err.println("col_name::"+resultSet.getString(i));
                row.add(String.valueOf(resultSet.getObject(i)==null ? "" : resultSet.getObject(i)));
            }
            allRowsData.add(row);
            //rowCount = rowCount + 1;
        }

        tableView.getItems().addAll(allRowsData);

        MenuItem item = new MenuItem("Copy");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList rowList = (ObservableList) tableView.getSelectionModel().getSelectedItems();
                StringBuilder clipboardString = new StringBuilder();
                for (Iterator it = rowList.iterator(); it.hasNext();) {
                    ObservableList<Object> row = (ObservableList<Object>) it.next();
                    for (Object cell : row) {
                        if (cell == null) {
                            cell = "";
                        }
                        clipboardString.append(cell);
                        clipboardString.append('\t');
                    }
                    clipboardString.append('\n');
                }
                final ClipboardContent content = new ClipboardContent();
                content.putString(clipboardString.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(item);
        tableView.setContextMenu(menu);

        //tableViewBox.setContent(tableView);
        tableViewBox.getChildren().add(tableView);
        if (tableView.getItems().size() > 0) {
            transferBtn.setDisable(false);
        } else {
            transferBtn.setDisable(true);
        }

        dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
        dataPostMsg.setText("Query executed successfully. Total " + countResultSetRows(resultSet) + " records found.");

    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();

    }

//    private void addNewTab(JsonObject data) {
//
//        Label err_lbl = new Label();
//        err_lbl.setStyle("-fx-text-fill: #ff3535;");
//        err_lbl.getStyleClass().add("err_lbl");
//
//        TextField idField = new TextField();
//        idField.setVisible(false);
//        idField.setEditable(false);
//        idField.getStyleClass().add("id_f");
//        idField.prefHeight(0);
//        idField.prefWidth(0);
//        
//        TextField flagField = new TextField();
//        flagField.setVisible(false);
//        flagField.setEditable(false);
//        flagField.getStyleClass().add("flag_f");
//        flagField.prefHeight(0);
//        flagField.prefWidth(0);
//        flagField.setText("O");
//
//        Label titleLabel = new Label("Query Title :");
//        titleLabel.setPrefSize(150, 22);
//        TextField titleField = new TextField();
//        titleField.setPromptText("Enter Query Title");
//        HBox.setHgrow(titleField, Priority.ALWAYS);
//        titleField.getStyleClass().add("title_f");
//        HBox h1 = new HBox(titleLabel,titleField);
//        h1.spacingProperty().add(10);
//        
//        Label queryTypeLabel = new Label("Query Type :");
//        queryTypeLabel.setPrefSize(150, 22);
//        ComboBox queryTypeField = new ComboBox();
//        queryTypeField.getItems().addAll(Arrays.asList("Query","Procedure"));
//        queryTypeField.getSelectionModel().selectFirst();
//        //HBox.setHgrow(queryTypeField, Priority.ALWAYS);
//        queryTypeField.getStyleClass().add("query_type_f");
//        HBox h2 = new HBox(queryTypeLabel,queryTypeField);
//        h2.spacingProperty().add(10);
//
//        Label queryLabel = new Label("Query Text :");
//        queryLabel.setPrefSize(150, 22);
//        TextArea queryField = new TextArea();
//        queryField.setPromptText("Enter Query Text");
//        queryField.setPrefSize(200, 200);
//        HBox.setHgrow(queryField, Priority.ALWAYS);
//        queryField.getStyleClass().add("query_f");
//        HBox h3 = new HBox(queryLabel,queryField);
//        h3.spacingProperty().add(10);
//
//        Button saveQueryBtn = new Button("Save Query");
//        Button deleteQueryBtn = new Button("Delete Query");
//
//        deleteQueryBtn.setLayoutX(514.0);
//        deleteQueryBtn.setLayoutY(10.0);
//        HBox h4 = new HBox(idField, flagField, saveQueryBtn, deleteQueryBtn);
//        h4.setAlignment(Pos.TOP_RIGHT);
//        h4.setSpacing(10);
//
//        HBox h5 = new HBox(err_lbl);
//        h5.setAlignment(Pos.TOP_RIGHT);
//        h5.setPadding(new Insets(10, 0, 0, 0));
//
//        VBox v = new VBox(h1,h2, h3, h4, h5);
//        v.setSpacing(10);
//        v.setPadding(new Insets(10, 10, 10, 10));
//        v.setPrefSize(100, 200);
//
//        Tab t = new Tab("Untitled");
//        t.setContent(v);
//
//        titleField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (newPropertyValue) {
//                    System.out.println("Textfield on focus");
//                } else {
//                    if (!titleField.getText().equals("")) {
//                        t.setText(titleField.getText());
//                    } else {
//                        t.setText("Untitled");
//                    }
//                }
//            }
//        });
//
//        saveQueryBtn.setOnAction(ev -> {
//            saveQueryToFile(titleField, queryField, err_lbl);
//        });
//
//        deleteQueryBtn.setOnAction(ev -> {
//            deleteQueryFromFile(t);
//        });
//
//        queryTabPane.getTabs().add(t);
//
//        if (data != null) {
//            t.setText(data.get("title").getAsString());
//            idField.setText(data.get("id").getAsString());
//            titleField.setText(data.get("title").getAsString());
//            queryField.setText(data.get("text").getAsString());
//        }
//        
//
//    }
    private void saveQueryToFile(TextField queryTitle, TextArea queryText, Label err_lbl) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (isValidTextField(queryTitle) && isValidTextArea(queryText)) {
            writeJsonObjectToFile(err_lbl);
        } else {
            if (!isValidTextField(queryTitle) && isValidTextArea(queryText)) {
                err_lbl.setText("Query title required");
            } else if (isValidTextField(queryTitle) && !isValidTextArea(queryText)) {
                err_lbl.setText("Query text required");
            } else {
                err_lbl.setText("Query title and Query text required");
            }

            setTimeout(() -> {
                Platform.runLater(() -> {
                    err_lbl.setText("");
                });
            }, 3000);
        }
    }

    private void deleteQueryFromFile(Tab t) {
        queryTabPane.getTabs().remove(t);
//        if (queryTabPane.getTabs().size() == 0) {
//            addNewTab(null);
//        }
        writeJsonObjectToFile(null);
    }

    private void saveEmptyQueryJsonFile() {
        try {
            File f = new File("query.json");
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
        }
    }

    private JsonArray readJsonFile() {
        try {
            File f = new File("query.json");
            if (!f.exists()) {
                saveEmptyQueryJsonFile();
            }
            String rawJsonArr = new String(Files.readAllBytes(f.toPath()));
            JsonParser parser = new JsonParser();
            return parser.parse(rawJsonArr).getAsJsonArray();
        } catch (Exception e) {
            return new JsonArray();
        }
    }

    private void writeJsonObjectToFile(Label err_lbl) {
        try {
            File f = new File("query.json");
            if (!f.exists()) {
                saveEmptyQueryJsonFile();
            }
            JsonArray jarr = new JsonArray();
            int id_count = 1;
            for (Tab tab : queryTabPane.getTabs()) {
                TextField titleF = (TextField) tab.getContent().lookup(".title_f");
                TextArea queryF = (TextArea) tab.getContent().lookup(".query_f");
                ComboBox queryTypeF = (ComboBox) tab.getContent().lookup(".query_type_f");
                JsonObject jo = new JsonObject();
                jo.addProperty("id", id_count);
                jo.addProperty("flag", isValidTextField(titleF) && titleF.getText().equalsIgnoreCase("sell") ? "S" : isValidTextField(titleF) && titleF.getText().equalsIgnoreCase("purchase") ? "P" : "O");
                jo.addProperty("title", titleF.getText());
                jo.addProperty("text", queryF.getText());
                jo.addProperty("type", queryTypeF.getSelectionModel().getSelectedItem().toString());
                jarr.add(jo);
                id_count = id_count + 1;
            }

            try (Writer writer = new FileWriter(f)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jarr, writer);

                if (err_lbl != null) {
                    err_lbl.setStyle("-fx-text-fill: #14b1ab;");
                    err_lbl.setText("Query Saved Successfully.");
                    setTimeout(() -> {
                        Platform.runLater(() -> {
                            err_lbl.setText("");
                            err_lbl.setStyle("-fx-text-fill: #ff3535;");
                        });
                    }, 3000);
                }
                System.out.println("write to file sucessfully::");

                setTimeout(() -> {
                    updateQuerySelection();
                }, 500);
            }

        } catch (Exception e) {
            System.out.println("writeJsonObjectToFile exception e::" + e);
            if (err_lbl != null) {
                err_lbl.setStyle("-fx-text-fill: #ff3535;");
                err_lbl.setText("Error occured while saving query");

                setTimeout(() -> {
                    Platform.runLater(() -> {
                        err_lbl.setText("");
                    });
                }, 3000);
            }

        }
    }

    private void updateQuerySelection() {
        JsonArray ja = readJsonFile();
        System.out.println("updateQuerySelection::" + ja);
        System.out.println("updateQuerySelection ja size::" + ja.size());
        queryObservableList.removeAll(querySelector.getItems());
        queryObservableList.clear();
        querySelector.getItems().clear();
        if (!ja.isJsonNull()) {
            for (JsonElement je : ja) {
                if (!je.getAsJsonObject().get("text").getAsString().equals("")) {
                    queryObservableList.add(je.getAsJsonObject());
                }
            }
            querySelector.getItems().addAll(queryObservableList);
        }

        //querySelector.getSelectionModel().selectFirst();
        if (queryObservableList.size() > 0) {
            querySelector.getSelectionModel().selectFirst();
        }
    }

    private void createConnection() {
        System.err.println("createConnection() 1");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
            }
            connection = null;
        }
        if (isValidDatabaseType()
                && isValidTextField(dbhost)
                && isValidTextField(dbport)
                && isValidDatabaseName()
                && isValidUsername()
                && isValidPassword()
                && isValidTextField(gstin_no)) {
            System.err.println("createConnection() 2");
            connectingErr.setText("");
            String dbtype = databaseTypeSelect.getSelectionModel().getSelectedItem().getType();
            String connectionUrl = null;
            Connection con = null;
            saveToPropertyFile();
            if (dbtype.equals("MSSQL")) {
                connectionUrl = "jdbc:sqlserver://" + dbhost.getText() + ":" + dbport.getText() + ";databaseName=" + dbname.getText() + ";user=" + userName.getText() + ";password=" + password.getText() + ";";
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                }
            } else if (dbtype.equals("MYSQL")) {
                connectionUrl = "jdbc:mysql://" + dbhost.getText() + ":" + dbport.getText() + "/" + dbname.getText();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                }
            } else if (dbtype.equals("ORACLE")) {
                connectionUrl = "jdbc:oracle:thin:@" + dbhost.getText() + ":" + dbport.getText() + ":" + dbname.getText();
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                }
            } else if (dbtype.equals("POSTGRES")) {
                connectionUrl = "jdbc:postgresql://" + dbhost.getText() + ":" + dbport.getText() + "/" + dbname.getText();
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
                }
            }

            System.out.println("connectionUrl::" + connectionUrl);

            try {
                con = DriverManager.getConnection(connectionUrl, userName.getText().trim(), password.getText().trim());
            } catch (SQLException ex) {
                Logger.getLogger(DataTransferUtilityMainController.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
            }

            if (con != null) {
                connection = con;
                connectingErr.setText("Connected Successfully.");
                confirmDialog.close();
                dataPostMsg.setStyle("-fx-text-fill: #14b1ab;"); //#ff3535  #14b1ab
                dataPostMsg.setText("Connected Successfully.");
                //loadNowBtn.setVisible(true);
            } else {
                connectingErr.setText("Unable to connect to database. please check credential.");
                //loadNowBtn.setVisible(false);
                dataPostMsg.setStyle("-fx-text-fill: #ff3535;"); //#ff3535  #14b1ab
                dataPostMsg.setText("Unable to connect to database. please check credential.");
            }
        } else {
            System.err.println("createConnection() 3");
            connectingErr.setText("");
            //loadNowBtn.setVisible(false);
            if (!isValidDatabaseType()) {
                err_db_type.setVisible(true);
            } else {
                err_db_type.setVisible(false);
            }
            if (!isValidTextField(dbhost)) {
                err_host.setVisible(true);
            } else {
                err_host.setVisible(false);
            }
            if (!isValidTextField(dbport)) {
                err_port.setVisible(true);
            } else {
                err_port.setVisible(false);
            }
            if (!isValidDatabaseName()) {
                err_db_name.setVisible(true);
            } else {
                err_db_name.setVisible(false);
            }
            if (!isValidUsername()) {
                err_username.setVisible(true);
            } else {
                err_username.setVisible(false);
            }
            if (!isValidPassword()) {
                err_password.setVisible(true);
            } else {
                err_password.setVisible(false);
            }

            if (!isValidTextField(gstin_no)) {
                err_gstn.setVisible(true);
            } else {
                err_gstn.setVisible(false);
            }
        }
    }

    class DBType {

        String type;
        String name;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DBType(String type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.getName();
        }
    }

    class PARAM {

        String name;
        TextField tf;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TextField getTf() {
            return tf;
        }

        public void setTf(TextField tf) {
            this.tf = tf;
        }

        public PARAM(String name, TextField tf) {
            this.name = name;
            this.tf = tf;
        }

        @Override
        public String toString() {
            return this.tf.getText();
        }
    }

    class XCell extends ListCell<PARAM> {

        HBox hbox = new HBox();
        String lastItem;

        public XCell() {
            super();
        }

        @Override
        protected void updateItem(PARAM p, boolean empty) {
            super.updateItem(p, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = p.getName();
                Label label = new Label();
                label.setText(p.getName() != null ? p.getName() : "");
                Pane pane = new Pane();
                hbox.getChildren().addAll(label, pane, p.getTf());
                HBox.setHgrow(pane, Priority.ALWAYS);
                setGraphic(hbox);
            }
        }
    }

}
