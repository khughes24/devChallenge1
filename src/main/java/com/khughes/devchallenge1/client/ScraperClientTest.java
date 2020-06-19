package com.khughes.devchallenge1.client;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class ScraperClientTest {


    /**
     * Check to see if the connection works with a known connection path
     */
    @Test
    public  void testValidConnection(){
        String Url = "http://devtools.truecommerce.net:8080/challenge001/";
        ScraperClient client = new ScraperClient();
        Document doc = client.validConnection();
        assertNotNull(doc);
    }

    /**
     * Check to see if the connection doesnt work with an invalid connection path
     */
    @Test
    public  void testInvalidConnection(){
        String Url = "http://devtools.truecommerce.net:8080/challenge001/";
        ScraperClient client = new ScraperClient();
        Document doc = client.invalidConnection();
        assertNull(doc);
    }

    /**
     * Check to see if the connection doesnt work with an invalid connection path
     */
    @Test
    public  void testPassedConnection(){
        String Url = "http://devtools.truecommerce.net:8080/challenge001/";
        ScraperClient client = new ScraperClient();
        Document doc = client.passedConnection(Url);
        assertNotNull(doc);
    }




}
