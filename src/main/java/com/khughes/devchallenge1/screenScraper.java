package com.khughes.devchallenge1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class screenScraper {

    public  void getProducts(String[] args) {
        // write your code her
        System.out.println("Starting connection");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("http://devtools.truecommerce.net:8080/challenge001/").get();

            // With the document fetched, we use JSoup's title() method to fetch the title
            System.out.printf("Title: %s\n", doc.title());

            Elements productTags = doc.getElementsByClass("productDescription1");
            Elements productLinks = doc.getElementsByClass("productLink");

            for (Element product : productTags) {
                System.out.println(product.getElementsByClass("productDescription1").text());
            }

            System.out.println("------------------");
            System.out.println("------------------");

            ArrayList<Element> urlList = productLinks;

            for(Element target : urlList){
                System.out.println(target.getElementsByClass("productLink").attr("href"));
            }
            System.out.println("");
            System.out.println("------------------");
            System.out.println("------------------");
            System.out.println("");

            //arrayBuilder(urlList);
            List<Element> fixedList = removeDupes(urlList);

            productList prodList = new productList();
            ArrayList<productItem> itemList = new ArrayList<productItem>();
            for(Element outList : fixedList) {
                System.out.println(outList.getElementsByClass("productLink").attr("href"));


                itemList.add(getItemDetails(outList));

            }
            prodList.setItem(itemList);
            System.out.println(prodList.toString());

            System.out.println("---------------");
            System.out.println("");
            String jsonResponse = mapper.writeValueAsString(prodList);
            System.out.println(jsonResponse);

            /**
             *

             List <Element> fixedList = removeDupes(urlList);
             for(Element link : fixedList){
             System.out.println("a");
             System.out.println(link.getElementsByClass("productLink").attr("href"));
             }
             */

            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  productItem getItemDetails(Element item){
        String suffix = item.getElementsByClass("productLink").attr("href");
        productItem prodItem = new productItem();
        double net = 0.00;
        double vat = 0.00;
        double gross = 0.00;
        double vatConstant = 0.20;

        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("http://devtools.truecommerce.net:8080" + suffix).get();
            System.out.printf("Title: %s\n", doc.title());

            String productTitle= doc.getElementsByClass("productDescription1").text();
            String productDesc = doc.getElementsByClass("productDescription2").text();
            String productPrice = doc.getElementsByClass("productUnitPrice").text();
            String productKal = doc.getElementsByClass("productKcalPer100Grams").text();

            net = net + Double.parseDouble(productPrice);
            vat = vat + (Double.parseDouble(productPrice) * vatConstant);
            gross = net + vat; //pretty sure this logic is right

            prodItem.setName(productTitle);
            prodItem.setDescription(productDesc);
            prodItem.setPrice(productPrice);
            if(!productPrice.equals("")){
                prodItem.setKals(productKal);
            }

            System.out.println(prodItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prodItem;
    }

    /**
     * Sort the list of addresses into a unique list
     * @param dupeList
     * @return
     */
    public  List<Element> removeDupes(List<Element> dupeList){
        ArrayList<Element> fixedList = new ArrayList<Element>();
        String hrefA;
        String hrefB;
        boolean found = false;
        System.out.println("----------------------------------------");
        for (Element element : dupeList){ //loop through main list
            hrefA = element.getElementsByClass("productLink").attr("href").toString();
            found = false;
            for (Element iterCheck : fixedList){ //check if what we are trying to add already exists
                hrefB = iterCheck.getElementsByClass("productLink").attr("href").toString();
                if(hrefA.equals(hrefB)){
                    found = true;
                }
            }
            for (Element iterCheck : dupeList){
                hrefB = iterCheck.getElementsByClass("productLink").attr("href").toString();
                if(hrefA.equals(hrefB) && found == false){
                    found = true;
                    fixedList.add(element);
                }
            }
        }
        return  fixedList;
    }



}
