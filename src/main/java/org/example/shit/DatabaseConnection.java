package org.example.shit;

public interface DatabaseConnection {

    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/artstream";
    boolean checkCredentials(String name, String email);

}
