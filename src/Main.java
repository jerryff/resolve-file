import java.io.*;

public class Main {

	static int extraterror=0;

    public static void main(String[] args) {
        String dir = "C:/Users/fanjy14/Documents/1/";

        //debug
        String filePath = "C:/Users/fanjy14/Documents/1/news.tsinghua.edu.cn/publish/thunews/9658/20170417114734267469394/1492410830577.pdf";

        File dirFile =  new File(dir);

        //debug
        File testFile = new File(filePath);
        resloveFile(testFile);

        // resloveFile(dirFile);


    }

    private static void resloveFile(File file){
        if(file.isFile() ){
            String path[] = file.getPath().split("/");
            if(path[path.length-1].matches(".*.html$")){
                try{
                	Extractor.extractHtml(file);
                }catch (Exception e){
                    extraterror++;
                    System.out.println("extract error  "+ file.getPath());
                }
            }
            if(path[path.length-1].matches(".*.pdf$")){
                try{
                	Extractor.extractPdf(file);
                }catch (Exception e){
                    extraterror++;
                    System.out.println("extract error  "+ file.getPath());
                }
            }
        }else {
            //System.out.println("鍒嗘瀽鏂囦欢澶�  "+file.getPath());
        	String folderName=file.getPath().replace("C:\\Users\\fanjy14\\Documents\\1", "C:\\Users\\fanjy14\\Documents\\1\\result")+".txt";
        	File folder = new File(folderName);
            if(!folder.exists() && folder.isDirectory()) folder.mkdirs();
            for (File subFile :
                    file.listFiles() ) {
                resloveFile(subFile);
            }
        }
    }

//    private static void writeLog(String log){
//        try {
//            ops.write((log+"\n").getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}