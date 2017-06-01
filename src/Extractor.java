import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Payne on 3/18/16.
 */
public class Extractor {


    public static void extractHtml(String filePath){
        File htmlFile = new File(filePath);
        extractHtml(htmlFile);
    }
    public static void extractPdf(String filePath){
        File pdfFile = new File(filePath);
        extractHtml(pdfFile);
    }
    /**
     * 浠庢櫙鐐归〉闈㈢殑html鏂囦欢涓В鏋愬嚭鏉ユ垜浠墍闇�瑕佺殑淇℃伅
     * @param htmlFile html鏂囦欢
     * @return 鏅偣瀵硅薄 @Sight
     */
    public static void extractHtml(File htmlFile){
    	FileWriter fw = null;
    	String filename=htmlFile.getPath().replace("C:\\Users\\fanjy14\\Documents\\1", "C:\\Users\\fanjy14\\Documents\\1\\result")+".txt";
    	File file=new File(filename);
    	try {
    		if(!file.exists()) file.createNewFile();
            fw = new FileWriter(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document htmlDoc = null;
        String fileName[] = htmlFile.getName().split("\\.");
        try {
            htmlDoc = Jsoup.parse(htmlFile,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //瑙ｆ瀽椤甸潰閾炬帴
        Elements links = htmlDoc.getElementsByTag("a");
        Elements content = htmlDoc.getElementsByTag("p");
        ListIterator<Element> listit = content.listIterator();  
        try {
            fw.write(links.text());
            fw.write(htmlDoc.getElementsByTag("h1").text());
            fw.write(htmlDoc.getElementsByTag("h2").text());
            fw.write(htmlDoc.getElementsByTag("h3").text());
            fw.write(htmlDoc.getElementsByTag("h4").text());
            fw.write(htmlDoc.getElementsByTag("h5").text());
            while(listit.hasNext()){  
            	String p=listit.next().toString();
                String pattern = "cutSummary\\(\"(.*)\",";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(p);
                if (m.find( )) {
                   fw.write(" "+m.group(1)+" ");
                }
            }  
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        
//        Elements f_left = htmlDoc.getElementsByClass("f_left");
//        String sight_name = f_left.first().child(0).child(0).text();
//        sight.setName(sight_name);

    }
    public static void extractPdf(File pdfFile){
    	FileWriter fw = null;
    	String filename=pdfFile.getPath().replace("C:\\Users\\fanjy14\\Documents\\1", "C:\\Users\\fanjy14\\Documents\\1\\result")+".txt";
    	File file=new File(filename);
    	int pages;
    	PDDocument document=null;
    	PDFTextStripper stripper;
    	try {
    		if(!file.exists()) file.createNewFile();
            fw = new FileWriter(filename);
        	document=PDDocument.load(pdfFile);
            pages = document.getNumberOfPages();
            stripper=new PDFTextStripper();
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            String content = stripper.getText(document);
            fw.write(content); 
            fw.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
