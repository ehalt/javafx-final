package com.example.bms;

import java.io.File;


//import com.sun.javafx.charts.Legend;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.image.Image;


public class dashboardController implements Initializable {

    private File file;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button availableBooks_btn;

    @FXML
    private Button purchaseBtn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane availableBooks_form;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;

    @FXML
    private BarChart<?, ?> dashboard_customerChart; // Corrected spelling

    @FXML
    private AnchorPane dashboard_AB;

    @FXML
    private Label dashboard_avb;

    @FXML
    private AnchorPane dashboard_TC;

    @FXML
    private Label dashboard_totalinc;

    @FXML
    private Label dashboard_tc;

    @FXML
    private AnchorPane dashboard_TI;

    @FXML
    private Button availableBooks_importBtn;

    @FXML
    private ImageView availableBooks_imageView;

    @FXML
    private TextField availableBooks_BookID;

    @FXML
    private TextField availableBooks_BookTitle;

    @FXML
    private TextField availableBooks_author;

    @FXML
    private TextField availableBooks_genre;

    @FXML
    private DatePicker availableBooks_date;

    @FXML
    private TextField availableBooks_price;

    @FXML
    private Button availableBooks_addBtn;

    @FXML
    private Button availableBooks_updateBtn;

    @FXML
    private Button availableBooks_clearBtn;

    @FXML
    private Button availableBooks_deleteBtn;

    @FXML
    private TextField availableBooks_search;

    @FXML
    private TableView<bookData> availableBooks_tableView; // Specify your data type

    @FXML
    private TableColumn<bookData, String> availableBooks_col_bookID;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_bookTitle;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_author;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_genre;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_date;

    @FXML
    private TableColumn<bookData, Double> availableBooks_col_price; // Specify correct type

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private TableView<customerData> purchase_tableView;

    @FXML
    private TableColumn<customerData, String> purchase_col_bookID;

    @FXML
    private TableColumn<customerData, String> purchase_col_bookTitle;

    @FXML
    private TableColumn<customerData, String> purchase_col_author;

    @FXML
    private TableColumn<customerData, String> purchase_col_genre;

    @FXML
    private TableColumn<customerData, Integer> purchase_col_quantity; // Specify correct type

    @FXML
    private TableColumn<customerData, Double> purchase_col_price; // Specify correct type






    @FXML
    private ComboBox<String> purchase_bookID; // Make sure this is declared








    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;


    public void dashboardAB() {
        String sql = "SELECT COUNT(id) FROM book";
        connect = Database.getConnection();

        int countAB = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countAB = result.getInt("COUNT(id)");
            }

            dashboard_avb.setText(String.valueOf(countAB));

        }catch(Exception e) {e.printStackTrace();}
    }

    public void dashboardTI() {
        String sql = "SELECT SUM(total) FROM customer_info";
        connect = Database.getConnection();

        double sumTotal = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                sumTotal = result.getDouble("SUM(total)");
            }

            dashboard_totalinc.setText("$" + String.valueOf(sumTotal));

        }catch(Exception e) {e.printStackTrace();}
    }


    public void dashboardTC() {
        String sql = "SELECT COUNT(id) FROM customer_info";
        connect = Database.getConnection();

        int countTC = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countTC = result.getInt("COUNT(id)");
            }
            dashboard_tc.setText(String.valueOf(countTC));


        }catch(Exception e) {e.printStackTrace();}
    }



//    public void dashboardIncomeChart() {
//        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 6";
//        connect = Database.getConnection();
//
//        try {
//
//            XYChart.Series chart = new XYChart.Series();
//
//
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//            if (result.next()) {
//                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
//
//            }
//
//            dashboard_incomeChart.getData().add(chart);
//
//        }catch(Exception e) {e.printStackTrace();}
//    }


    public void dashboardIncomeChart(){

        dashboard_incomeChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 6";

        connect = Database.getConnection();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_incomeChart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }



//    public void dashboardCustomerChart() {
//        dashboard_customerChart.getData().clear();
//
//        String sql = "SELECT date, COUNT(id) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 4";
//        connect = Database.getConnection();
//
//        try {
//            XYChart.Series chart = new XYChart.Series();
//
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//            if (result.next()) {
//                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
//            }
//
//            dashboard_customerChart.getData().add(chart);
//
//        }catch(Exception e) {e.printStackTrace();}
//    }

    public void dashboardCustomerChart(){

        dashboard_customerChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 4";

        connect = Database.getConnection();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_customerChart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }

    public void availableBooksAdd() {
        String sql = "INSERT INTO book (book_id, title, author, genre, pub_date, price, image) VALUES(?, ?, ?, ?, ?, ?, ?)";
        connect = Database.getConnection();

        try {
            Alert alert;

            // Check for empty fields
            if (availableBooks_BookID.getText().isEmpty()
                    || availableBooks_BookTitle.getText().isEmpty()
                    || availableBooks_author.getText().isEmpty()
                    || availableBooks_genre.getText().isEmpty()
                    || availableBooks_date.getValue() == null
                    || availableBooks_price.getText().isEmpty()
                    || getData.path == null || getData.path.isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields correctly");
                alert.showAndWait();
                return;
            }

            // Validate price
            double price;
            try {
                price = Double.parseDouble(availableBooks_price.getText());
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Price must be a valid number");
                alert.showAndWait();
                return;
            }

            // Check whether book id exists or not
            String checkData = "SELECT book_id FROM book WHERE book_id = ?";
            prepare = connect.prepareStatement(checkData);
            prepare.setString(1, availableBooks_BookID.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Book ID: " + availableBooks_BookID.getText() + " already exists");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableBooks_BookID.getText());
                prepare.setString(2, availableBooks_BookTitle.getText());
                prepare.setString(3, availableBooks_author.getText());
                prepare.setString(4, availableBooks_genre.getText());
                prepare.setString(5, String.valueOf(availableBooks_date.getValue()));
                prepare.setDouble(6, price);

                String uri = getData.path.replace("\\", "\\\\");
                prepare.setString(7, uri);

                prepare.executeUpdate();
                availableBooksShowListData();
                availableBooksClear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void availableBooksUpdate() {
        String uri = getData.path; // Get the image path
        uri = uri.replace("\\", "\\\\"); // Replace backslashes for file path

        String sql = "UPDATE book SET title = ?, author = ?, genre = ?, price = ?, pub_date = ?, image = ? WHERE book_id = ?";
        connect = Database.getConnection();

        try {
            Alert alert;

            // Check for empty fields
            if (availableBooks_BookID.getText().isEmpty()
                    || availableBooks_BookTitle.getText().isEmpty()
                    || availableBooks_author.getText().isEmpty()
                    || availableBooks_genre.getText().isEmpty()
                    || availableBooks_date.getValue() == null
                    || availableBooks_price.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields correctly");
                alert.showAndWait();
                return;
            }

            // Validate price
            double price;
            try {
                price = Double.parseDouble(availableBooks_price.getText());
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Price must be a valid number");
                alert.showAndWait();
                return;
            }

            // Prepare the SQL statement
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableBooks_BookTitle.getText());
            prepare.setString(2, availableBooks_author.getText());
            prepare.setString(3, availableBooks_genre.getText());
            prepare.setDouble(4, price);
            prepare.setString(5, String.valueOf(availableBooks_date.getValue()));
            prepare.setString(6, uri);
            prepare.setString(7, availableBooks_BookID.getText()); // Ensure the book_id is set for the WHERE clause

            // Execute the update
            int rowsUpdated = prepare.executeUpdate();
            if (rowsUpdated > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Book updated successfully");
                alert.showAndWait();
                availableBooksShowListData(); // Refresh the table data
                availableBooksClear(); // Clear input fields
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Book ID not found");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the book");
            alert.showAndWait();
        } finally {
            // Clean up resources
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // available books delete
    public void availableBooksDelete() {
        String sql = "DELETE FROM book WHERE book_id = ?";
        connect = Database.getConnection();

        try {
            Alert alert;

            // Check if a book is selected
            if (availableBooks_tableView.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a book to delete");
                alert.showAndWait();
                return;
            }

            // Get the selected book ID
            bookData selectedBook = availableBooks_tableView.getSelectionModel().getSelectedItem();
            String bookId = String.valueOf(selectedBook.getBookId());

            // Prepare the SQL statement
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, bookId);

            // Execute the delete
            int rowsDeleted = prepare.executeUpdate();
            if (rowsDeleted > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Book deleted successfully");
                alert.showAndWait();
                availableBooksShowListData(); // Refresh the table data
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Book ID not found");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while deleting the book");
            alert.showAndWait();
        } finally {
            // Clean up resources
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void availableBooksClear() {
        availableBooks_BookID.setText("");
        availableBooks_BookTitle.setText("");
        availableBooks_author.setText("");
        availableBooks_genre.setText("");
        availableBooks_date.setValue(null);
        availableBooks_price.setText("");

        getData.path = "";

        availableBooks_imageView.setImage(null);
    }




    public void availableBooksInsertImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Book Image");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        file = open.showOpenDialog(main_form.getScene().getWindow()); // Set the file here

        if (file != null) {
            getData.path = file.getAbsolutePath();  // Set the path here
            image = new Image(file.toURI().toString(), 134, 79, false, true);
            availableBooks_imageView.setImage(image);
        } else {
            getData.path = null;  // Ensure path is null if no file is selected
        }
    }




    public ObservableList<bookData> availableBookListData() {
        ObservableList<bookData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM book"; // Check this query

        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            bookData bookD;

            while (result.next()) {
                bookD = new bookData(result.getInt("book_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getDate("pub_date"),
                        result.getDouble("price"),
                        result.getString("image"));
                listData.add(bookD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }


    private ObservableList<bookData> availableBookList;


    public void availableBooksShowListData() {
        availableBookList = availableBookListData();

        availableBooks_col_bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        availableBooks_col_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableBooks_col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        availableBooks_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableBooks_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        availableBooks_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        availableBooks_tableView.setItems(availableBookList);
    }


    public void availableBooksSelect() {
        bookData bookD = availableBooks_tableView.getSelectionModel().getSelectedItem();
        int num = availableBooks_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) { return; }

        availableBooks_BookID.setText(String.valueOf(bookD.getBookId()));
        availableBooks_BookTitle.setText(bookD.getTitle());
        availableBooks_author.setText(bookD.getAuthor());
        availableBooks_genre.setText(bookD.getGenre());
        availableBooks_date.setValue(LocalDate.parse(String.valueOf(bookD.getDate())));
        availableBooks_price.setText(String.valueOf(bookD.getPrice()));

        String uri = "file:" + bookD.getImage(); // Use bookD.getImage() directly

        getData.path = bookD.getImage(); // Set the path correctly

        image = new Image(uri, 134, 79, false, true);
        availableBooks_imageView.setImage(image);
    }


    // searching implemention
    public void availableBookSearch() {
        String keyword = availableBooks_search.getText().trim(); // Get the search input
        ObservableList<bookData> filteredList = FXCollections.observableArrayList(); // List to hold filtered results

        String sql = "SELECT * FROM book WHERE book_id LIKE ? OR title LIKE ? OR author LIKE ? OR genre LIKE ?";
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%"; // Use wildcard for partial matches
            prepare.setString(1, searchPattern);
            prepare.setString(2, searchPattern);
            prepare.setString(3, searchPattern);
            prepare.setString(4, searchPattern);

            result = prepare.executeQuery();

            while (result.next()) {
                // Create a new bookData object for each result
                bookData bookD = new bookData(
                        result.getInt("book_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getDate("pub_date"),
                        result.getDouble("price"),
                        result.getString("image")
                );
                filteredList.add(bookD); // Add to the filtered list
            }

            // Update the TableView with filtered results
            availableBooks_tableView.setItems(filteredList);

            if (filteredList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No books found matching the search criteria.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while searching for books.");
            alert.showAndWait();
        } finally {
            // Clean up resources
            try {
                if (prepare != null) prepare.close();
                if (result != null) result.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int totalP;

    public void purchaseAdd() {
        purchasecustomerId();
        String sql = "INSERT INTO customer (customer_id, book_id, title, author, genre, date, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connect = Database.getConnection();

        try {
            Alert alert;

            // Check for null selections
            if (purchase_bookTitle.getSelectionModel().getSelectedItem() == null || purchase_bookID.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a book title");
                alert.showAndWait();
                return; // Exit method
            }

            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, customerId); // Use setInt for integer values
            prepare.setInt(2, Integer.parseInt(purchase_info_bookID.getText()));
            prepare.setString(3, purchase_info_bookTitle.getText());
            prepare.setString(4, purchase_info_author.getText());
            prepare.setString(5, purchase_info_genre.getText());
            prepare.setInt(6, qty); // Use setInt for integer values

            // Retrieve the price
            String checkData = "SELECT price FROM book WHERE title = ?";
            try (PreparedStatement checkPrepare = connect.prepareStatement(checkData)) {
                checkPrepare.setString(1, purchase_info_bookTitle.getText());
                try (ResultSet checkResult = checkPrepare.executeQuery()) {
                    if (checkResult.next()) {
                        double priceD = checkResult.getDouble("price");
                        totalP = (int) (qty * priceD); // Store the total price
                        prepare.setDouble(7, totalP); // Set price
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Book not found.");
                        alert.showAndWait();
                        return; // Exit method
                    }
                }
            }

            // Set the date
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            prepare.setDate(6, sqlDate); // Correct index for date

            // Execute the update
            int rowsAffected = prepare.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Check if any rows were inserted

            // Refresh the data displayed
            purchaseShowCustomerListData();
            purchaseDisplayTotal();

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void purchasePay() {
        String sql = "INSERT INTO customer_info (customer_id, total, date) " + "VALUES(?, ?, ?)";
        connect = Database.getConnection();

        try {
            Alert alert;

            if (displayTotal == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
            }
            else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(displayTotal));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully purchased");
                    alert.showAndWait();
                }
            }

        }catch(Exception e) {e.printStackTrace();}
    }



    private double displayTotal;
    public void purchaseDisplayTotal() {
        purchasecustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '" + customerId + "'";
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                displayTotal = result.getDouble("SUM(price)");
            }

            purchaseTotal.setText("$" + String.valueOf(displayTotal));

        }catch(Exception e) {e.printStackTrace();}
    }



    public void purchaseBookId() {
        String sql = "SELECT book_id FROM book";
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList<String> listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("book_id"));
            }

            purchase_bookID.setItems(listData);

            // Set up a listener for the ComboBox selection
            purchase_bookID.setOnAction(event -> purchaseBookTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void purchaseBookTitle() {
        String selectedBookId = purchase_bookID.getSelectionModel().getSelectedItem();
        if (selectedBookId == null) {
            return; // Exit if no ID is selected
        }

        String sql = "SELECT title FROM book WHERE book_id = ?";
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, selectedBookId);
            result = prepare.executeQuery();

            ObservableList<String> listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("title"));
            }

            purchase_bookTitle.setItems(listData);

            // Call purchaseBookInfo when a title is selected
            purchase_bookTitle.setOnAction(event -> purchaseBookInfo());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    public void purchaseBookInfo() {
        String selectedTitle = purchase_bookTitle.getSelectionModel().getSelectedItem();
        if (selectedTitle == null) {
            return; // Exit if no title is selected
        }

        String sql = "SELECT * FROM book WHERE title = ?";
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, selectedTitle);
            result = prepare.executeQuery();

            if (result.next()) {
                String bookId = result.getString("book_id");
                String title = result.getString("title");
                String author = result.getString("author");
                String genre = result.getString("genre");
                String date = result.getString("pub_date");

                // Log to console
                System.out.println("Setting values: " + bookId + ", " + title + ", " + author + ", " + genre + ", " + date);

                // Update UI components on the JavaFX Application Thread
                Platform.runLater(() -> {
                    purchase_info_bookID.setText(bookId);
                    purchase_info_bookTitle.setText(title);
                    purchase_info_author.setText(author);
                    purchase_info_genre.setText(genre);
                    purchase_info_date.setText(date);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @FXML
    private ComboBox<String> purchase_bookTitle; // Specify type

    @FXML
    private Label purchaseTotal;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private Label purchase_info_bookID;

    @FXML
    private Label purchase_info_bookTitle;

    @FXML
    private Label purchase_info_author;

    @FXML
    private Label purchase_info_genre; // Corrected spelling

    @FXML
    private Label purchase_info_date;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private Spinner<Integer> purchase_quantity;

    public ObservableList<customerData> purchaseListData(){
        purchasecustomerId();
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";

        ObservableList<customerData> listData = FXCollections.observableArrayList();

        connect = Database.getConnection();

        try{
            prepare  = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData customerD;

            while(result.next()){
                customerD = new customerData(result.getInt("customer_id")
                        , result.getInt("book_id")
                        , result.getString("title")
                        , result.getString("author")
                        , result.getString("genre")
                        , result.getInt("quantity")
                        , result.getDouble("price")
                        , result.getDate("date"));

                listData.add(customerD);
            }

        }catch(Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<customerData> purchaseCustomerList;
    public void purchaseShowCustomerListData(){
        purchaseCustomerList = purchaseListData();

        purchase_col_bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        purchase_col_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        purchase_col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        purchase_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseCustomerList);

    }




    private SpinnerValueFactory<Integer> spinner;

    public void purchaseDisplayQTY() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;
    public void purchaseQty() {
        qty = purchase_quantity.getValue();

    }

    private int customerId;

    public void purchasecustomerId() {
        String sql = "SELECT MAX(customer_id) FROM customer";
        int checkCID = 0;
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("MAX(customer_id)");
            }

            String checkData = "SELECT MAX(customer_id) FROM customer_info";
            prepare = connect.prepareStatement(checkData);
            result = prepare.executeQuery();

            while (result.next()) {
                checkCID = result.getInt("MAX(customer_id)");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (checkCID == customerId) {
                customerId = checkCID + 1;
            }

        }catch(Exception e) {e.printStackTrace();}
    }



    public void displayUsername() {
        String user = getData.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }


    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                // Close the current stage
                Stage currentStage = (Stage) main_form.getScene().getWindow();
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Close method
    public void close() {
        System.exit(0);
    }

    // Minimize method
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            availableBooks_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #FAF7F0");
            availableBooks_btn.setStyle("-fx-background-color: transparent;");
            purchaseBtn.setStyle("-fx-background-color: transparent");

            dashboardAB();
            dashboardTI();
            dashboardTC();

//            dashboardIncomeChart();
//            dashboardCustomerChart();

        }
        else if (event.getSource() == availableBooks_btn) {
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(true);
            purchase_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color:  #FAF7F0");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            purchaseBtn.setStyle("-fx-background-color: transparent");

            availableBookListData();

        }
        else if (event.getSource() == purchaseBtn) {
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(false);
            purchase_form.setVisible(true);

            purchaseBtn.setStyle("-fx-background-color:  #FAF7F0");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            availableBooks_btn.setStyle("-fx-background-color: transparent");

            purchaseBookTitle();
            purchaseBookId();
            purchaseShowCustomerListData();
            purchaseDisplayQTY();
            purchaseDisplayTotal();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Implement any necessary initialization here
        displayUsername();

        dashboardAB();
        dashboardTI();
        dashboardTC();

//        dashboardIncomeChart();
//        dashboardCustomerChart();

        // to show the data on the table
        availableBookListData();

        purchaseBookId();
        purchaseBookTitle();
        purchaseShowCustomerListData();
        purchaseDisplayQTY();
        purchaseDisplayTotal();
    }

}
