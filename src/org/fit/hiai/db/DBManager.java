package org.fit.hiai.db;

import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.javaparser.ProgramTree;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: April 28, 2019
 * Institution: Florida Institute of Technology
 * Purpose: This class provides functions for SQLite Database Management.
 */

public class DBManager {
    private String dbPath;
    private Connection connection;

    public DBManager() {
    }

    public DBManager(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * This method attempts to connect to the SQLite database selected.
     *
     * @return a connection to the database
     */
    public Connection connectToDB() {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    /**
     * This method checks if a database exists
     *
     * @param dbName the path to the database
     * @return true if database exists, false otherwise
     */
    public boolean databaseExists(String dbName) {
        File file = new File(dbName);
        return file.exists();
    }

    public String getDbPath() {
        return dbPath;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean saveNewProgramTree(ProgramTree programTree) {
        try {

            //use a query to check if this project already defined (if count = 0)
            int count = 0;
            String queryStr = "SELECT count(PROJECT_NAME) AS COUNT FROM " + ProjectConstants.TREE_DATA_TABLE + " WHERE PROJECT_NAME=?";

            //use preparedStatement to sanitize input strings
            PreparedStatement countQuery = connection.prepareStatement(queryStr);
//            countQuery.setString(1, programTree);
            ResultSet countResultSet = countQuery.executeQuery();

            while (countResultSet.next()) {
                count = countResultSet.getInt("COUNT");

                if (count == 0) {

                    //Now, insert this project-name into the projects table
//                    queryStr = "INSERT INTO " + ProjectConstants.PROJECTS_TABLE + " (PROJECT_NAME) VALUES(?)";
                    PreparedStatement insertProjQuery = connection.prepareStatement(queryStr);
//                    insertProjQuery.setString(1, projectName);

                    int rowsAffected = insertProjQuery.executeUpdate();

                    //if project inserted successfully, then attempt to insert tasks
                    if (rowsAffected > 0) {

                        //loop and insert the tasks in the tasks table
                        connection.close();
                        return ProjectConstants.OK;
                    }


                }

            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ProjectConstants.FAIL;
    }
}



