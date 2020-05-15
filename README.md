# Developer Challenge 1


Brief

This project goes to the target website and searches for all the products on the site. The product details are then captured and then output.

Outputs

An example output is below:

{"Result":[{"Title":"Bunch of freshly picked fair-trade Bananas","Code":"KS-001-YELLOW-FRUIT","Kcal_per_100g":"154.00","Unit_price":"1.35","Description":"Sustainably produced"},{"Title":"Kibble’s Blueberries (250G)","Code":"KS-002-BLUE-FRUIT","Kcal_per_100g":"68.00","Unit_price":"3.00","Description":"Delicately hand-picked and washed"},{"Title":"Bunch of freshly picked fair-trade Strawberries","Code":"KS-003-RED-FRUIT","Kcal_per_100g":"30.00","Unit_price":"2.00","Description":"Who can resist these delicious English hand-picked delights?"},{"Title":"Large punnet of Green Seedless Grapes","Code":"KS-004-GREEN-FRUIT","Kcal_per_100g":"66.00","Unit_price":"3.50","Description":"For maximum freshness, best kept refrigerated. Wash before use – and your hands!!"},{"Title":"Fresh spaghetti pasta","Code":"KS-005-SPAG-FR1","Kcal_per_100g":"359.00","Unit_price":"1.99","Description":"Durum wheat semolina pasta"},{"Title":"Terry’s Chocolate Orange – Dark Chocolate","Code":"KS-006-TCOD","Kcal_per_100g":"514.00","Unit_price":"1.50","Description":"A magical orb of delight."},{"Title":"Fresh skimmed milk, enriched with protein","Code":"KS-007-MILK-BOB","Kcal_per_100g":"","Unit_price":"1.75","Description":"Every ‘ised’ has been done to this milk – standardised, homogenised, pasteurised"},{"Title":"Warburtons Crumpets (6 pack)","Code":"KS-008-CRUMP-01","Kcal_per_100g":"176.00","Unit_price":"0.90","Description":"Warburtons Crumpets are always thick & deliciously fluffy."},{"Title":"Le Rustique Pastuerised Camembert 250G","Code":"KS-009-CAM-CHEESE-01","Kcal_per_100g":"268.00","Unit_price":"2.05","Description":"Made exlusively from milk collected from the Le Rustique Cheese factory in Normandy."},{"Title":"Toblerone Milk 360G","Code":"KS-010-TOB-360","Kcal_per_100g":"535.00","Unit_price":"4.50","Description":"Swiss milk chocolate with honey and almond nougat – now with more free Swiss fresh air between the triangles!"},{"Title":"Kibble’s Maris Piper Potatoes 2.5Kg","Code":"KS-011-MARIS-SPUD","Kcal_per_100g":"102.00","Unit_price":"1.55","Description":"Perfect all-rounder potatoes – for baking, roasting or mashing."},{"Title":"Organic re-usable cotton fruit and veg bag","Code":"ORG_FRUIT_VEG_BAG","Kcal_per_100g":"","Unit_price":"1.99","Description":""},{"Title":"2Ltr storage container, with klip-lock seal to lock-in freshness.","Code":"KLIP-LOCK-2LTR","Kcal_per_100g":"","Unit_price":"1.99","Description":""},{"Title":"Glass store jar to keep your pasta fresh, like your momma does.","Code":"GLASS-STORE-JAR","Kcal_per_100g":"","Unit_price":"1.99","Description":""}],"Net":30.06,"VAT":6.01,"Gross":36.07}


How to run

Pull the project into your IDE
Run the Main.jar class


Run options

1) "Please enter the Url you wish to generate from:"
This will select the target url the system will try and get information from- if an invalid url is chosen then the system will default to "http://devtools.truecommerce.net:8080/challenge001/"

2) "Would you like a full product breakdown: (y/n)"
This will determine if the system will show just the main products or the main products and the linked products. - if an invalid response is given the system will default to showing all posible items.

3) "Would you like the data output as a PDF: (y/n)"
This will determine if the system will generate an additional PDF of the product selection or not. If chosen the the PDF will be saved on the root file of the project with the name "ScreenScrape.pdf". If the PDF fails to generate please make sure that your current copy is closed and try again.


Libraries used

Lombok - Used to auto generate getters and setters for some classses, sped up the production of the code in those areas

FasterJackson - Used to handle JSONs in the project, allows for the convertion from objects to JSON and vica versa

JSOUP - Used to access webpages and get data from the said pages
