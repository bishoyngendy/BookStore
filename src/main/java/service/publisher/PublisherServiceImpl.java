package service.publisher;

import dao.publisher.PublisherDao;
import dao.publisher.PublisherDaoImpl;
import exceptions.DuplicateKeyException;
import models.Publisher;

import java.sql.SQLException;
import java.util.List;

import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_PK;

/**
 * PublisherServiceImpl
 * Created on: May 05, 2018
 * Author: marc
 */
public class PublisherServiceImpl implements PublisherService{
    private PublisherDao publisherDao;

    public PublisherServiceImpl() {
        publisherDao = new PublisherDaoImpl();
    }

    public long addPublisher(Publisher publisher) throws DuplicateKeyException {
        try {
            return this.publisherDao.addPublisher(publisher);
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new DuplicateKeyException("Publisher name already exist.");
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Publisher> getPublishersByNamePrefix(String namePrefix) {
        List<Publisher> publishers = null;
        try {
            publishers = this.publisherDao.getPublishersByNamePrefix(namePrefix);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return publishers;
    }
}
