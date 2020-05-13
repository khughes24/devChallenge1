package com.khughes.devchallenge1;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;


public class PDFGenerator {


    public boolean generatePDF(String dataStream, String siteAddress, String siteTitle, productList prodList){

        boolean generationStatus = true; //everything is ok

        Document document = new Document();

        System.out.println(prodList.getItem());

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("ScreenScrape.pdf"));

            document.open();

            Font fontHead = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD | Font.UNDERLINE);
            Font fontBody = new Font(Font.FontFamily.HELVETICA,10);

            Paragraph headerParagraph = new Paragraph(50);

            Chunk headChunk = new Chunk("ScreenScapper", fontHead);
            headerParagraph.add(headChunk);
            headerParagraph.setSpacingAfter(25);
            headerParagraph.setAlignment(Element.ALIGN_CENTER);
            headerParagraph.setIndentationLeft(50);
            headerParagraph.setIndentationRight(50);
            document.add(headerParagraph);

            document.add(new Paragraph("Data captured from: " + siteTitle + ".",fontBody)


            );
            Paragraph paragraphInfo1 = new Paragraph();
            paragraphInfo1.setSpacingAfter(10);
            paragraphInfo1.setSpacingBefore(25);
            Chunk chunkInfo1 = new Chunk("Data available at: " + siteAddress + ".", fontBody);
            paragraphInfo1.add(chunkInfo1);
            document.add(paragraphInfo1);

            Paragraph paragraphInfo2 = new Paragraph();
            paragraphInfo1.setSpacingAfter(25);
            Chunk chunkInfo2 = new Chunk("Please find the data extraction below: ", fontBody);
            paragraphInfo2.add(chunkInfo2);
            document.add(paragraphInfo2);


            for(productItem item : prodList.getItem()){
                Paragraph jsonName = new Paragraph();
                jsonName.setSpacingBefore(10);
                jsonName.setAlignment(Element.ALIGN_CENTER);
                jsonName.setIndentationLeft(50);
                jsonName.setIndentationRight(50);
                Chunk chunk = new Chunk("Name: " + item.getName(), fontBody);
                jsonName.add(chunk);
                document.add(jsonName);

                Paragraph jsonCode = new Paragraph();
                jsonCode.setAlignment(Element.ALIGN_CENTER);
                jsonCode.setIndentationLeft(50);
                jsonCode.setIndentationRight(50);
                chunk = new Chunk("Code: " + item.getCode(), fontBody);
                jsonCode.add(chunk);
                document.add(jsonCode);

                if(item.getKals() == null || item.getKals().equals("")){

                }else{
                    Paragraph jsonKals = new Paragraph();
                    jsonKals.setAlignment(Element.ALIGN_CENTER);
                    jsonKals.setIndentationLeft(50);
                    jsonKals.setIndentationRight(50);
                    chunk = new Chunk("Kals: " + item.getKals(), fontBody);
                    jsonKals.add(chunk);
                    document.add(jsonKals);
                }


                Paragraph jsonPrice = new Paragraph();
                jsonPrice.setAlignment(Element.ALIGN_CENTER);
                jsonPrice.setIndentationLeft(50);
                jsonPrice.setIndentationRight(50);
                chunk = new Chunk("Price: £" + item.getPrice(), fontBody);
                jsonPrice.add(chunk);
                document.add(jsonPrice);

                Paragraph jsonDescription = new Paragraph();
                jsonDescription.setAlignment(Element.ALIGN_CENTER);
                jsonDescription.setIndentationLeft(50);
                jsonDescription.setIndentationRight(50);
                chunk = new Chunk("Description: " + item.getDescription(), fontBody);
                jsonDescription.add(chunk);
                document.add(jsonDescription);
            }


            document.close();

        } catch (DocumentException e) {
            generationStatus = false; //something failed so set the status to failed
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            generationStatus = false; //something failed so set the status to failed
            e.printStackTrace();
            System.out.println("Unable to create Document. Please check if the document is open or in use");
        }

        return generationStatus;
    }


}
