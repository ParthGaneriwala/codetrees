package org.fit.hiai.constants;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/28/19
 * Institution: Florida Institute of Technology
 * Purpose:
 */
public class ProjectConstants {

    public static String HOST="localhost";
    public static boolean OK = true;
    public static boolean FAIL = false;
    public static boolean APPEND_MODE = true;
    public static boolean OVER_WRITE_MODE = false;

    public static String TREE_DATA_TABLE = "PROGRAM_TREES";

    public static String CREATE_TASK_TABLE_SQL = "CREATE TABLE TASKS " +
            "(TASK_ID               INTEGER PRIMARY KEY NOT NULL," +
            " TASK_NAME             TEXT                NOT NULL," +
            " START_TIME            TEXT                NOT NULL," +
            " END_TIME              TEXT                NOT NULL," +
            " PROJECT_NAME          TEXT                NOT NULL," +
            " OWNER_NAME            TEXT,"+
            " OWNER_IP              TEXT,"+
            " OWNER_PORT            INT,"+
            " COMPLETED_FLAG        TEXT)";




    ////////////// MISC /////////
    public static String WEKA_AST_RELATION_NAME = "WEKA_AST_TREES";
    public static String FEATURE_SEPARATOR = "|";
    public static String CSV_SEPARATOR = ",";
    public static String DOUBLE_QUOTE = "\"";
    public static String APOSTROPHE = "'";
    public static String CONCATENATOR = "\\+";
    public static String QUESTION_MARK = "?";
    public static String ARFF_ATTRIBUTE_PREFIX = "@attribute ";
    public static String ARFF_RELATION_PREFIX = "@relation ";
    public static String ARFF_DATA_PREFIX = "@data ";
    public static String MISSING_ATTRIBUTE_WILDCARD = "?";
    public static String BOOLEAN_ATTRIBUTE_VALUES = "{true, false}";
    public static String INTEGER_ATTRIBUTE_VALUE = "integer";
    public static String STRING_ATTRIBUTE_VALUE = "string";

    public static final String GAP_STRING = "________"; 	//Only For printing the final alignment (8 bits)
    public static final double MIN_ALIGNMENT_PENALTY = Double.MAX_VALUE;
    public static final int MIN_METHOD_LENGTH = 100; //in character count
    public static final int MIN_STATEMENT_COUNT = 20;
    public static final int MAX_STATEMENT_COUNT = 100;
    public static final int MAX_NUM_FILES = 700000;
    public static final int MAX_ALIGNMENT_FILES = 25000;
    public static int MIN_CODE_LENGTH = 200;
}
