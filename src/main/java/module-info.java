module org.example.shit {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.desktop;
    requires java.xml.crypto;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    opens org.example.shit to javafx.fxml, org.hibernate.orm.core;
    exports org.example.shit;


}