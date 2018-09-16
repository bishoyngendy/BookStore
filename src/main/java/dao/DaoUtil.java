package dao;

import models.HasKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DaoUtil
 * Created on: May 05, 2018
 * Author: marc
 */
public abstract class DaoUtil {
    public static long getId(PreparedStatement preparedStatement, HasKey model) throws SQLException {
        long id = -1;
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            model.setKey(generatedKeys.getLong(1));
            id = model.getKey();
        }

        return id;
    }
}
