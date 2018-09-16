package dao.publisher;

import models.Publisher;

import java.sql.SQLException;
import java.util.List;

/**
 * PublisherDao
 * Created on: May 05, 2018
 * Author: marc
 */
public interface PublisherDao {
    long addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException;
    long addPublishers(List<Publisher> publishers) throws SQLException, ClassNotFoundException;
    List<Publisher> getPublishersByNamePrefix(String namePrefix) throws SQLException, ClassNotFoundException;
    List<Long> getPublishersId() throws SQLException, ClassNotFoundException;
}
