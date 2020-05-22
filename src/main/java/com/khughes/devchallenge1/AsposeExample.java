package com.khughes.devchallenge1;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;


/**
 * Test class to try and get aspose to work in java
 */
public class AsposeExample {

    //Create the document
    Document document = new Document();

    //Add page
    Page page = document.getPages().add();

    // not sure why this isnt working yet -- should add hello world to the page
    page.getParagraphs().add(new TextFragment("Hello World!"));


}
