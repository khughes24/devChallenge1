package com.khughes.devchallenge1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class screenScraper {

    public  void getProducts(String[] args, String url) {
        System.out.println("---Attempting connection---");


        if(url.isEmpty()){
            url = targetUrl.url; // Use the targetUrl constant
            System.out.println("Warning: URL not provided, defaulting to preset URL");
        }
        if(!url.equals(targetUrl.url)){
            url = targetUrl.url;
            System.out.println("Warning: URL does not match target, defaulting to preset URL");
        }


        ObjectMapper mapper = new ObjectMapper();
        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("http://devtools.truecommerce.net:8080/challenge001/").get();

            // With the document fetched, we use JSoup's title() method to fetch the title
            System.out.printf("Title: %s\n", doc.title());

            if(doc.title() != "Kibble Stores Ltd"){
                System.out.println("Warning: URL not provided does not match requested website");
            }


            Elements productTags = doc.getElementsByClass("productDescription1");
            Elements productLinks = doc.getElementsByClass("productLink");




            ArrayList<Element> urlList = productLinks;


            System.out.println("");
            System.out.println("------------------");
            System.out.println("------------------");
            System.out.println("");

            List<Element> fixedList = removeDupes(urlList);

            productList prodList = new productList();
            ArrayList<productItem> itemList = new ArrayList<productItem>();
            for(Element outList : fixedList) {
                itemList.add(getItemDetails(outList, prodList));
            }
            prodList.setItem(itemList);

            //TODO theres gotta be a better way of rounding
            prodList.net = BigDecimal.valueOf(prodList.net).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            prodList.vat = BigDecimal.valueOf(prodList.vat).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
            prodList.gross = BigDecimal.valueOf(prodList.gross).setScale(2,RoundingMode.HALF_EVEN).doubleValue();

            System.out.println(prodList.toString());

            System.out.println("---------------");
            System.out.println("");
            String jsonResponse = mapper.writeValueAsString(prodList);
            System.out.println(jsonResponse);

            productList addList = new productList();

            addList = additionalItems(url);

            for(productItem add : addList.getItem()){
                prodList.getItem().add(add);
            }
            prodList.net = prodList.net + addList.net;
            prodList.vat = prodList.vat + addList.vat;
            prodList.gross = prodList.gross + addList.gross;

            prodList.net = BigDecimal.valueOf(prodList.net).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            prodList.vat = BigDecimal.valueOf(prodList.vat).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
            prodList.gross = BigDecimal.valueOf(prodList.gross).setScale(2,RoundingMode.HALF_EVEN).doubleValue();

            System.out.println("---------------");
            System.out.println("");
            jsonResponse = mapper.writeValueAsString(prodList);
            System.out.println(jsonResponse);

            // Error
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("");
            System.out.println("Error: Cannot connect to requested website, please check connection. Restarting....");
            System.out.println("");
            System.out.println("");
            Main.main(new String[]{""});
        }
    }

    public  productItem getItemDetails(Element item, productList prodList){
        String suffix = item.getElementsByClass("productLink").attr("href");
        productItem prodItem = new productItem();
        double net = 0.00;
        double vat = 0.00;
        double gross = 0.00;
        double vatConstant = 0.20;

        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("http://devtools.truecommerce.net:8080" + suffix).get();

            String productTitle= doc.getElementsByClass("productDescription1").text();
            String productDesc = doc.getElementsByClass("productDescription2").text();
            String productPrice = doc.getElementsByClass("productUnitPrice").text();
            String productKal = doc.getElementsByClass("productKcalPer100Grams").text();

            prodList.net = prodList.net + Double.parseDouble(productPrice);
            prodList.vat = prodList.vat + (Double.parseDouble(productPrice) * vatConstant);
            prodList.gross = prodList.net + prodList.vat; //pretty sure this logic is right



            prodItem.setName(productTitle);
            prodItem.setDescription(productDesc);
            prodItem.setPrice(productPrice);
            if(!productPrice.equals("")){
                prodItem.setKals(productKal);
            }

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


    public productList additionalItems(String url){
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect(url).get();

            Elements productLinks = doc.getElementsByClass("productCrossSellLink");

            ArrayList<Element> urlList = productLinks;

            //arrayBuilder(urlList);
            List<Element> fixedList = removeDupesAdditional(urlList);

            productList prodList = new productList();
            ArrayList<productItem> itemList = new ArrayList<productItem>();
            for(Element outList : fixedList) {
                System.out.println(outList.getElementsByClass("productLink").attr("href"));


                itemList.add(additionalItems(outList, prodList));

            }
            prodList.setItem(itemList);

            //TODO theres gotta be a better way of rounding
            prodList.net = BigDecimal.valueOf(prodList.net).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            prodList.vat = BigDecimal.valueOf(prodList.vat).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
            prodList.gross = BigDecimal.valueOf(prodList.gross).setScale(2,RoundingMode.HALF_EVEN).doubleValue();

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
            return prodList;
            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public productItem additionalItems(Element item, productList prodList){
        String suffix = item.getElementsByClass("productCrossSellLink").attr("href");
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
            String productPrice = doc.getElementsByClass("productUnitPrice").text();
            String productKal = doc.getElementsByClass("productKcalPer100Grams").text();

            prodList.net = prodList.net + Double.parseDouble(productPrice);
            prodList.vat = prodList.vat + (Double.parseDouble(productPrice) * vatConstant);
            prodList.gross = prodList.net + prodList.vat; //pretty sure this logic is right



            prodItem.setName(productTitle);
            prodItem.setDescription(""); //Additionals dont have descriptions
            prodItem.setPrice(productPrice);
            if(!productPrice.equals("")){
                prodItem.setKals(productKal);
            }

            System.out.println(prodItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  List<Element> removeDupesAdditional(List<Element> dupeList){
        ArrayList<Element> fixedList = new ArrayList<Element>();

        ArrayList<Element> filteredList = new ArrayList<>();
        String hrefA;
        String hrefB;
        boolean found = false;
        System.out.println("----------------------------------------");



        for (Element element : dupeList){
            System.out.println(element.getElementsByClass("productCrossSellDescription").text());
            if(!element.getElementsByClass("productCrossSellDescription").text().equals("")){
                filteredList.add(element);
            }
        }



        for (Element element : filteredList){ //loop through main list
            System.out.println(element.getElementsByClass("productCrossSellLink"));
            hrefA = element.getElementsByClass("productCrossSellDescription").text();
            found = false;
            for (Element iterCheck : fixedList){ //check if what we are trying to add already exists
                hrefB = iterCheck.getElementsByClass("productCrossSellDescription").text();
                if(hrefA.equals(hrefB)){
                    found = true;
                }
            }
            for (Element iterCheck : filteredList){
                hrefB = iterCheck.getElementsByClass("productCrossSellDescription").text();
                if(hrefA.equals(hrefB) && found == false){
                    found = true;
                    fixedList.add(element);
                }
            }
        }
        return  fixedList;
    }

}
