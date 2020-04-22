package com.khughes.devchallenge1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {


    /**
     * Starting Program. Requests the target url and if they need additional details. These are then parsed as params the the main scraper method.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Product scraper: Please enter the Url you wish to generate from:");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        System.out.println("Product scraper: Would you like a full product breakdown: (y/n)");
        scanner = new Scanner(System.in);
        String breakResponse = scanner.nextLine();
        boolean additionalFlag = false;
        if(breakResponse.toLowerCase().equals("y")){
            additionalFlag = true;
        }

        screenScraper scraper = new screenScraper();
        scraper.getProducts(args,url, additionalFlag);
    }



}
