package com.khughes.devchallenge1;

import com.aspose.pdf.*;

import javax.xml.parsers.DocumentBuilder;


/**
 * Test class to try and get aspose to work in java
 */
public class AsposeExample {

    //Create the document
    Document document = new Document("input.pdf");

    //DocumentBuilder builder = new Docu




    //Add page
    Page page = document.getPages().add();

    //TextFragment txtFragment = new TextFragment("main text");

    //txtFragment.setPosition(new Position(100,600));

    //TextBuilder txtBuilder = new TextBuilder(page);
    //txtBuilder.appendText(txtFragment);



    // not sure why this isnt working yet -- should add hello world to the page
    //page.getParagraphs().add(new TextFragment("Hello World!"));


}
