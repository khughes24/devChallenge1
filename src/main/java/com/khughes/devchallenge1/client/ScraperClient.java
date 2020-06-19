package com.khughes.devchallenge1.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.khughes.devchallenge1.screenScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class ScraperClient {

    private Client client;
    public ScraperClient() { client = ClientBuilder.newClient(); }

    /**
     * Send a valid request to the connection method
     * @return
     */
    public Document validConnection(){
        screenScraper scraper = new screenScraper();
        Document response = scraper.getConnection("http://devtools.truecommerce.net:8080/challenge001/");
        return response;
    }

    /**
     * Test a passed connection
     * @return
     */
    public Document passedConnection(String url){
        screenScraper scraper = new screenScraper();
        Document response = scraper.getConnection(url);
        return response;
    }

    /**
     * send an invalid request to the connection method
     * @return
     */
    public Document invalidConnection(){
        screenScraper scraper = new screenScraper();
        Document response = scraper.getConnection("brokenurl");
        return response;
    }



}
