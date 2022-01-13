import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    TableView<Mahasiswa> tableView = new TableView<Mahasiswa>();
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UAS OOP");

        TableColumn<Mahasiswa, Integer> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Mahasiswa, String> columnNIM = new TableColumn<>("NIM");
        columnNIM.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<Mahasiswa, String> columnNAMA = new TableColumn<>("NAMA");
        columnNAMA.setCellValueFactory(new PropertyValueFactory<>("nama"));
        
        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNIM);
        tableView.getColumns().add(columnNAMA);
        
        // Button
        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Add");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> Add());

        Button button2 = new Button("Edit");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> Edit());

        Button button3 = new Button("Delete");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> Delete());

        load();
        VBox vbox = new VBox(tableView, toolBar);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();

    Statement stmt;
        
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet record = stmt.executeQuery("select * from tb_mahasiswa");
            tableView.getItems().clear();

            while (record.next()){
                tableView.getItems()
                .add(new Mahasiswa(record.getInt("id"), record.getString("nim"), record.getString("nama") ));
            }
        } 
        catch (SQLException e) {
            System.out.println("koneksi gagal");
        }


        
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            // Connection conn = DriverManager.getConnection(url, user, pass);
            stmt = db.conn.createStatement();
            ResultSet record = stmt.executeQuery("select * from tb_mahasiswa");

            while (record.next()) {
                tableView.getItems().add(new Mahasiswa(record.getInt("id"), record.getString("nim"),
                        record.getString("nama") )) ;
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            System.out.println("");
        }
    }

    // Bagian add
    public void Add() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Add Data");

        TextField nimField = new TextField();
        TextField namaField = new TextField();
        Label labelnim = new Label("NIM");
        Label labelnama = new Label("Nama");
        
        VBox hbox1 = new VBox(5, labelnim, nimField);
        VBox hbox2 = new VBox(5, labelnama, namaField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 500, 500);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into tb_mahasiswa set NIM='%s', nama='%s'";
                sql = String.format(sql, nimField.getText(), namaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
            addStage.show();
        }

    // Bagian edit
    public void Edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Data");

        TextField idField = new TextField();
        TextField namaField = new TextField();
        Label labelid = new Label("id");
        Label labelnama = new Label("Nama");
        

        VBox hbox1 = new VBox(5, labelid, idField);
        VBox hbox2 = new VBox(5, labelnama, namaField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 500, 500);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "update tb_mahasiswa set nama='%s' where id='%s'";
                sql = String.format(sql, namaField.getText(), idField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
            addStage.show();
        }

    // Bagian delete
    public void Delete() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Delete");

        TextField nimField = new TextField();
        Label labelnim = new Label("NIM");
        

        VBox hbox1 = new VBox(5, labelnim, nimField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 500, 500);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from tb_mahasiswa where NIM='%s'";
                sql = String.format(sql, nimField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

            addStage.setScene(scene);
            addStage.show();
        }

}

    