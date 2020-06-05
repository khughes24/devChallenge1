package com.khughes.devchallenge1;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfTable;
import com.sun.scenario.effect.ImageData;
import com.truecommerce.onetime.messagingService.common.utils.DateTime;
import com.truecommerce.docs.common.components.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.GregorianCalendar;


public class PDFGenerator {


    public boolean generatePDF(String dataStream, String siteAddress, String siteTitle, productList prodList, String imgUrl, boolean additionalItems){

        boolean generationStatus = true; //everything is ok

        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        com.truecommerce.docs.common.components.DateTime now = new com.truecommerce.docs.common.components.DateTime();
        try{
            GregorianCalendar cal = new GregorianCalendar();
            XMLGregorianCalendar nowInXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            now.setTime(nowInXML);
            now.setDate(nowInXML);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println(prodList.getItem());

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("ScreenScrape.pdf"));

            document.open();
            //add a photo of the website we are scraping
            Image image = Image.getInstance(imgUrl);
            image.scaleToFit(750,250);
            document.add(image);
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

            //try and add a table
            // Creating a table object

            document.add(new Paragraph("Site Details",fontBody));
            PdfPTable table = new PdfPTable(2);
            table.setSplitLate(false);
            table.setWidths(new int[]{1,15});

            table.addCell(String.valueOf("Site Name"));
            table.addCell(String.valueOf(siteTitle));

            table.addCell(String.valueOf("Site Address"));
            table.addCell(String.valueOf(siteAddress));

            table.addCell(String.valueOf("Date Gathered"));
            table.addCell(String.valueOf(now.getDate() + "" + now.getTime()));

            document.add(table);



            document.add(new Paragraph("Item Details",fontBody));
            PdfPTable itemTable = new PdfPTable(2);
            itemTable.setSplitLate(false);
            itemTable.setWidths(new int[]{1,15});

            itemTable.addCell(String.valueOf("Total Item Count"));
            itemTable.addCell(String.valueOf(prodList.getItem().size()));

            itemTable.addCell(String.valueOf("Standard Item Count"));
            itemTable.addCell(String.valueOf(prodList.getItem().size()));

            itemTable.addCell(String.valueOf("Additional Item Count"));
            if(additionalItems == true){
                itemTable.addCell(String.valueOf(prodList.getItem().size()));
            }else{
                itemTable.addCell(String.valueOf("N/A"));
            }


            document.add(itemTable);

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

            document.add(new Paragraph("Price Break Down",fontBody));
            PdfPTable priceTable = new PdfPTable(2);
            priceTable.setSplitLate(false);
            priceTable.setWidths(new int[]{1,15});

            priceTable.addCell(String.valueOf("Total Price"));
            priceTable.addCell(String.valueOf("£" + ""));

            priceTable.addCell(String.valueOf("Gross"));
            priceTable.addCell(String.valueOf("£" + ""));

            priceTable.addCell(String.valueOf("Net"));
            priceTable.addCell(String.valueOf("£" + ""));

            document.add(priceTable);

            document.close();

        } catch (DocumentException e) {
            generationStatus = false; //something failed so set the status to failed
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            generationStatus = false; //something failed so set the status to failed
            e.printStackTrace();
            System.out.println("Unable to create Document. Please check if the document is open or in use");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return generationStatus;
    }


}
