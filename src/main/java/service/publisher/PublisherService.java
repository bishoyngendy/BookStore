package service.publisher;

import exceptions.DuplicateKeyException;
import models.Publisher;

import java.util.List;

/**
 * PublisherService
 * Created on: May 05, 2018
 * Author: marc
 */
public interface PublisherService {
    long addPublisher(Publisher publisher) throws DuplicateKeyException;
    List<Publisher> getPublishersByNamePrefix(String namePrefix);
}
