package com.khughes.devchallenge1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {

    public static void main(String[] args) {
        screenScraper scraper = new screenScraper();
        scraper.getProducts(args);
    }



}
