import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Payne on 3/18/16.
 */
public class Extractor {
	
	private static int index=0;
	private static FileWriter indexw = null;
	private static FileWriter outlink = null;
	
	public static void init(){
		index=0;
		try{
			indexw= new FileWriter("F:\\search_engine\\result\\index_src.txt");
			outlink= new FileWriter("F:\\search_engine\\result\\outlink.txt");
		}catch (Exception e) {
        	System.out.println("extract error init ");
        }
	}
	public static void end(){
		try{
			indexw.close();
			outlink.close();
		}catch (Exception e) {
        	System.out.println("extract error end ");
        }
	}
    public static void extractHtml(String filePath){
        File htmlFile = new File(filePath);
        extractHtml(htmlFile);
    }
    public static void extractPdf(String filePath){
        File pdfFile = new File(filePath);
        extractHtml(pdfFile);
    }
    public static void extractDoc(String filePath){
        File docFile = new File(filePath);
        extractHtml(docFile);
    }
    public static void extractXls(String xlsPath){
        File xlsFile = new File(xlsPath);
        extractHtml(xlsFile);
    }
    public static void extractPpt(String pptPath){
        File pptFile = new File(pptPath);
        extractHtml(pptFile);
    }
    /**
     * 浠庢櫙鐐归〉闈㈢殑html鏂囦欢涓В鏋愬嚭鏉ユ垜浠墍闇�瑕佺殑淇℃伅
     * @param htmlFile html鏂囦欢
     * @return 鏅偣瀵硅薄 @Sight
     */
    public static void extractHtml(File htmlFile){
    	FileWriter fw = null;
    	String filename="F:\\search_engine\\result\\"+index+".txt";
    	String title=null;
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
            htmlDoc = Jsoup.parse(htmlFile,"utf-8",htmlFile.getPath().split("\\\\")[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //瑙ｆ瀽椤甸潰閾炬帴
        Elements links = htmlDoc.getElementsByTag("a");
        Elements content = htmlDoc.getElementsByTag("p");
        ListIterator<Element> listit = content.listIterator();  
        ListIterator<Element> listit1 = links.listIterator();  
        try {
    		String lk="";
        	while(listit1.hasNext()){  
            	String p=listit1.next().attr("href");
            	if(p.equals("javascript:;")) continue;
            	p=p.replace(";", "");
            	p=p.replace("https://", "");
            	p=p.replace("http://", "");
            	if(p!="" && p.subSequence(0, 1).equals("/"))
            		p=htmlFile.getPath().split("\\\\")[3]+p;
            	lk+=" "+p;
            }  
            outlink.write(index+" [] "+lk+"\n");
            String cont="";
            cont+=htmlDoc.getElementsByTag("h1").text();
            cont+=htmlDoc.getElementsByTag("h2").text();
            cont+=htmlDoc.getElementsByTag("h3").text();
            cont+=htmlDoc.getElementsByTag("h4").text();
            cont+=htmlDoc.getElementsByTag("h5").text();
            while(listit.hasNext()){  
            	String p=listit.next().toString();
                String pattern = "cutSummary\\(\"(.*)\" , ";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(p);
                if (m.find( )) {
                   cont+=" "+m.group(1)+" ";
                }
            }  
            cont+=links.text();
            Element e=htmlDoc.getElementsByTag("title").first();
            if(e != null){
                title=htmlDoc.getElementsByTag("title").first().text();
            }else{
            	title=cont.split(" ")[0];
            }
            if(title.equals("")) title=htmlFile.getName();
            fw.write(cont);
            fw.close();
            indexw.write(index+" , "+htmlFile.getPath().replace("F:\\search_engine\\5\\", "")+" , "+title+"\n");
        } catch (IOException e) {
        	System.out.println("extract error  "+ htmlFile.getPath());
        }
//        
//        Elements f_left = htmlDoc.getElementsByClass("f_left");
//        String sight_name = f_left.first().child(0).child(0).text();
//        sight.setName(sight_name);
        index=index+1;

    }
    public static void extractPdf(File pdfFile){
    	FileWriter fw = null;
    	String filename="F:\\search_engine\\result\\"+index+".txt";
    	String title=pdfFile.getName();
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
            indexw.write(index+" , "+pdfFile.getPath().replace("F:\\search_engine\\5\\", "")+" , "+title+"\n");
        } catch (Exception e) {
        	System.out.println("extract error  "+ pdfFile.getPath());
        }
        index=index+1;
    }
    public static void extractDoc(File docFile){
    	FileWriter fw = null;
    	String filename="F:\\search_engine\\result\\"+index+".txt";
    	String title=docFile.getName();
    	File file=new File(filename);
    	try {
    		if(!file.exists()) file.createNewFile();
            fw = new FileWriter(filename);
            String content=""; 
            try{
            	OPCPackage opcPackage = POIXMLDocument.openPackage(docFile.getPath());
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                content = extractor.getText();
            } catch(Exception e) {
            	FileInputStream fis = new FileInputStream(docFile.getPath());
            	HWPFDocument doc = new HWPFDocument(fis);
            	content = doc.getDocumentText();
            }
            fw.write(content); 
            fw.close(); 
            indexw.write(index+" , "+docFile.getPath().replace("F:\\search_engine\\5\\", "")+" , "+title+"\n");
        } catch (Exception e) {
        	System.out.println("extract error  "+ docFile.getPath());
        }
        index=index+1;
    } 
    public static void extractXLs(File xlsFile){
    	FileWriter fw = null;
    	String filename="F:\\search_engine\\result\\"+index+".txt";
    	String title=xlsFile.getName();
    	File file=new File(filename);
    	try {
    		if(!file.exists()) file.createNewFile();
            fw = new FileWriter(filename);
             FileInputStream readFile = new FileInputStream(xlsFile.getPath());
             //创建一个WorkBook，从指定的文件流中创建，即上面指定了的文件流
             if(xlsFile.getName().matches(".*.xlsx$"))
	         {
	        	 XSSFWorkbook wb = new XSSFWorkbook(readFile);
	        	 int sheetCount = wb.getNumberOfSheets();
	             for(int i=0;i<sheetCount;i++)
	             {
		              XSSFSheet sheet = wb.getSheetAt(i);
		              int rowNum = sheet.getPhysicalNumberOfRows();
		              int cellNum;
		              for(int j=0;j<rowNum;j++)
		              {
			               XSSFRow row = sheet.getRow(j);
			               if(row==null) continue;
			               cellNum =row.getPhysicalNumberOfCells();
			               for(short k=0;k<cellNum;k++)
			               {
				                XSSFCell cell = row.getCell(k);
				                if(cell==null) continue;
				                if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				                {
				                	fw.write(" "+cell.getStringCellValue());
				                }
			               }
		              }
	             }
	         }
	         else
	         {
	        	 POIFSFileSystem fs = new POIFSFileSystem(readFile);
	        	 HSSFWorkbook wb= new HSSFWorkbook(fs);
	        	 int sheetCount = wb.getNumberOfSheets();
	             for(int i=0;i<sheetCount;i++)
	             {
		              HSSFSheet sheet = wb.getSheetAt(i);
		              int rowNum = sheet.getPhysicalNumberOfRows();
		              int cellNum;
		              for(int j=0;j<rowNum;j++)
		              {
			               HSSFRow row = sheet.getRow(j);
			               if(row==null) continue;
			               cellNum =row.getPhysicalNumberOfCells();
			               for(short k=0;k<cellNum;k++)
			               {
				                HSSFCell cell = row.getCell(k);
				                if(cell==null) continue;
				                if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				                {
				                	fw.write(" "+cell.getStringCellValue());
				                }
			               }
		              }
	             }
	         }         	
            fw.close(); 
            indexw.write(index+" , "+xlsFile.getPath().replace("F:\\search_engine\\5\\", "")+" , "+title+"\n");
        } catch (Exception e) {
        	System.out.println("extract error  "+ xlsFile.getPath());
        }
        index=index+1;
    }
    public static void extractPpt(File pptFile){
    	FileWriter fw = null;
    	String filename="F:\\search_engine\\result\\"+index+".txt";
    	String title=pptFile.getName();
    	File file=new File(filename);
    	try {
    		if(!file.exists()) file.createNewFile();
            fw = new FileWriter(filename);
    		String content="";
    		FileInputStream istream = new FileInputStream(pptFile.getPath());  
    		if(pptFile.getName().matches(".*.pptx$"))
            {
    			XMLSlideShow ppt=new XMLSlideShow(istream);  
                for(XSLFSlide slide:ppt.getSlides()){ //遍历每一页ppt  
                    //content+=slide.getTitle()+"\t";  
                    for(XSLFShape shape:slide.getShapes()){  
                        if(shape instanceof XSLFTextShape){ //获取到ppt的文本信息  
                            for(Iterator iterator=((XSLFTextShape) shape).iterator();iterator.hasNext();){  
                            //获取到每一段的文本信息  
                                XSLFTextParagraph paragraph=(XSLFTextParagraph) iterator.next();   
                                for (XSLFTextRun xslfTextRun : paragraph) {  
                                    content+=xslfTextRun.getRawText()+"\t";  
                                }  
                            }  
                        }  
                    }  
                    //获取一张ppt的内容后 换行  
                    content+="\n";  
                }  
            }
            else
            {
            	HSLFSlideShow ppt=new HSLFSlideShow(istream);  
                for(HSLFSlide slide:ppt.getSlides()){ //遍历每一页ppt  
                    //content+=slide.getTitle()+"\t";  
                    for(HSLFShape shape:slide.getShapes()){  
                        if(shape instanceof HSLFTextShape){ //获取到ppt的文本信息  
                            for(Iterator iterator=((HSLFTextShape) shape).iterator();iterator.hasNext();){  
                            //获取到每一段的文本信息  
                            	HSLFTextParagraph paragraph=(HSLFTextParagraph) iterator.next();   
                                for (HSLFTextRun xslfTextRun : paragraph) {  
                                    content+=xslfTextRun.getRawText()+"\t";  
                                }  
                            }  
                        }  
                    }  
                    //获取一张ppt的内容后 换行  
                    content+="\n";  
                }  
            }
            
            fw.write(content); 
            fw.close(); 
            indexw.write(index+" , "+pptFile.getPath().replace("F:\\search_engine\\5\\", "")+" , "+title+"\n");
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("extract error  "+ pptFile.getPath());
        }
        index=index+1;
    }
}
