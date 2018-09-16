package sample.generator;

import dao.author.AuthorDao;
import dao.author.AuthorDaoImpl;
import dao.publisher.PublisherDao;
import dao.publisher.PublisherDaoImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * DataGenerator
 * Created on: May 06, 2018
 * Author: marc
 */
public class DataGenerator {
    private static List<String> names;
    private static List<String> nouns;
    private static List<String> verbs;
    private static List<String> adverbs;
    private static List<String> categories = Arrays.asList("Science"
            , "Art", "Religion", "History", "Geography");
    private static List<Long> publishers;
    private static List<Long> authors;
    private static Random random;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DataGenerator dataGenerator = new DataGenerator();

        names = dataGenerator.getValues("names");
        nouns = dataGenerator.getValues("nouns");
        adverbs = dataGenerator.getValues("adverbs");
        verbs = dataGenerator.getValues("verbs");
        random = new Random();

//        AuthorGenerator authorGenerator = new AuthorGenerator();
//        PublisherGenerator publisherGenerator = new PublisherGenerator();
//        authorGenerator.start();
//        publisherGenerator.start();

        AuthorDao authorDao = new AuthorDaoImpl();
        PublisherDao publisherDao = new PublisherDaoImpl();
        authors = authorDao.getAuthorsId();
        publishers = publisherDao.getPublishersId();
        BookGenerator bookGenerator = new BookGenerator();
        bookGenerator.start();
    }

    static String getRandomName() {
        int n = random.nextInt(names.size());
        return names.get(n);
    }

    static String getRandomVerb() {
        int n = random.nextInt(verbs.size());
        return verbs.get(n);
    }

    static String getRandomAdverb() {
        int n = random.nextInt(adverbs.size());
        return adverbs.get(n);
    }

    static String getRandomNoun() {
        int n = random.nextInt(nouns.size());
        return nouns.get(n);
    }

    static String getRandomCategory() {
        int n = random.nextInt(categories.size());
        return categories.get(n);
    }

    static int getRandomYear() {
        return random.nextInt(2018);
    }

    static int getRandomNum(int x) {
        return random.nextInt(x);
    }

    static long getRandomPublisherId() {
        return publishers.get(random.nextInt(publishers.size()));
    }

    static long getRandomAuthorId() {
        return authors.get(random.nextInt(authors.size()));
    }

    private List<String> getValues(String fileName) throws IOException {
        List<String> values = new ArrayList<String>();
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            values.add(st);

        return values;
    }
}
