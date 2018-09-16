package utils;

/**
 * MySqlErrorCodes
 * Created on: May 05, 2018
 * Author: marc
 */
public abstract class MySqlErrorCodes {
    public static final int MYSQL_DUPLICATE_PK = 1062;
    public static final int MYSQL_DUPLICATE_COMPOSITE_PK = 1761;
    public static final int MYSQL_COLUMN_DOESNOT_HAVE_DEFAULT_VALUE = 1364;
    public static final int MYSQL_INVALID_QUANTITY = 1643;
}
