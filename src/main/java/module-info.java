module org.example.shit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.shit to javafx.fxml;
    exports org.example.shit;
}