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


    /**
     *Connects to a the requested url and then gets a list of products along with they're total price. This is then output as a JSON
     * @param args
     * @param url
     * @param additionalFlag
     */
    public  void getProducts(String[] args, String url, boolean additionalFlag, boolean pdfFlag) {
        PDFGenerator pdfGenerator = new PDFGenerator();
        System.out.println("---Attempting connection---");

        //Error handling, and default correcting
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

            ArrayList<Element> urlList = doc.getElementsByClass("productLink"); // Get a list of the productLinks from the target site

            List<Element> fixedList = removeDupes(urlList); //Remove the duplicates from the urlList

            productList prodList = new productList();
            ArrayList<productItem> itemList = new ArrayList<productItem>();

            for(Element outList : fixedList) { //loop throught the productLinks and add the item details to the itemList
                itemList.add(getItemDetails(outList, prodList));
            }
            prodList.setItem(itemList);

            //Round the values to 2 decimal places
            prodList.net = BigDecimal.valueOf(prodList.net).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            prodList.vat = BigDecimal.valueOf(prodList.vat).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
            prodList.gross = BigDecimal.valueOf(prodList.gross).setScale(2,RoundingMode.HALF_EVEN).doubleValue();




            String jsonResponse = mapper.writeValueAsString(prodList); //convert the objects into a outputable JSON string
            System.out.println("");
            System.out.println(jsonResponse);
            if(pdfFlag == true){
                pdfGenerator.generatePDF(jsonResponse,url, doc.title(), prodList);
            }



            if(additionalFlag == true){ //Do we want to show the user the extra items or not
                productList addList = new productList();
                addList = additionalItems(url);

                for(productItem add : addList.getItem()){
                    prodList.getItem().add(add);
                }
                //Add the additional Items values to the main item values
                prodList.net = prodList.net + addList.net;
                prodList.vat = prodList.vat + addList.vat;
                prodList.gross = prodList.gross + addList.gross;

                //Round the values to 2 decimal places
                prodList.net = BigDecimal.valueOf(prodList.net).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                prodList.vat = BigDecimal.valueOf(prodList.vat).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
                prodList.gross = BigDecimal.valueOf(prodList.gross).setScale(2,RoundingMode.HALF_EVEN).doubleValue();


                jsonResponse = mapper.writeValueAsString(prodList); //convert the objects into a outputable JSON string
                System.out.println("");
                System.out.println(jsonResponse);
                if(pdfFlag == true){
                    pdfGenerator.generatePDF(jsonResponse,url, doc.title(), prodList);
                }


            }

        } catch (IOException e) { // Error handling, prints out the exception and calls the startup function to restart
            System.out.println(e);
            System.out.println("");
            System.out.println("Error: Cannot connect to requested website, please check connection. Restarting....");
            System.out.println("");
            System.out.println("");
            Main.main(new String[]{""});
        }
    }

    /**
     * Uses the parsed element to connect to a page, the item details are then gathered and then returned
     * @param item
     * @param prodList
     * @return
     */
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
            String productCode = doc.getElementsByClass("productItemCode").text();
            String productDesc = doc.getElementsByClass("productDescription2").text();
            String productPrice = doc.getElementsByClass("productUnitPrice").text();
            String productKal = doc.getElementsByClass("productKcalPer100Grams").text();

            prodList.net = prodList.net + Double.parseDouble(productPrice);
            prodList.vat = prodList.vat + (Double.parseDouble(productPrice) * vatConstant);
            prodList.gross = prodList.net + prodList.vat; //pretty sure this logic is right



            prodItem.setName(productTitle);
            prodItem.setDescription(productDesc);
            prodItem.setPrice(productPrice);
            prodItem.setCode(productCode);
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


    /**
     * Gets a list of additional Items and then returns the list
     * @param url
     * @return
     */
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


                itemList.add(additionalItemsDetail(outList, prodList));

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


            return prodList;
            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Uses the parsed element to connect to the addtional item page, the itme details are then gathered and then returned
     * @param item
     * @param prodList
     * @return
     */
    public productItem additionalItemsDetail(Element item, productList prodList){
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
            String productCode = doc.getElementsByClass("productItemCode").text();

            prodList.net = prodList.net + Double.parseDouble(productPrice);
            prodList.vat = prodList.vat + (Double.parseDouble(productPrice) * vatConstant);
            prodList.gross = prodList.net + prodList.vat; //pretty sure this logic is right



            prodItem.setName(productTitle);
            prodItem.setDescription(""); //Additionals dont have descriptions
            prodItem.setPrice(productPrice);
            prodItem.setCode(productCode);
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
     * Sorts the list of additional item links into a unique list
     * @param dupeList
     * @return
     */
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
