package controller;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Author;
import util.DBUtil;

public class AuthorController {

  @FXML
  private TableView authorTable;
  @FXML
  private TableColumn<Author, Integer> authIdColumn;
  @FXML
  private TableColumn<Author, String> authNameColumn;
  @FXML
  private TableColumn<Author, String> authLastNameColumn;

  //Initializing the controller class.
  //This method is automatically called after the fxml file has been loaded.
  @FXML
  private void initialize() {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Author objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe

        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
    //Declare a SELECT statement
    String selectStmt = "SELECT * FROM AUTHORS";
    try {
      //Get all Author information
//Get ResultSet from dbExecuteQuery method
      ResultSet rsAuth = DBUtil.dbExecuteQuery(selectStmt);

      ObservableList<Author> authList = FXCollections.observableArrayList();

      while (rsAuth.next()) {
        Author author = new Author();
        author.setAuthorId(rsAuth.getInt("AUTHORID"));
        author.setFirstName(rsAuth.getString("FIRSTNAME"));
        author.setLastName(rsAuth.getString("LASTNAME"));

        //Add employee to the ObservableList
        authList.add(author);
      }

      authIdColumn.setCellValueFactory(cellData -> cellData.getValue().author_idProperty().asObject());
      authNameColumn.setCellValueFactory(cellData -> cellData.getValue().first_nameProperty());
      authLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().last_nameProperty());
      authorTable.setItems(authList);
    } catch (Exception ex) {
      System.out.println("Error occurred while getting authors information from DB.\n" + ex);
    }

  }

}
