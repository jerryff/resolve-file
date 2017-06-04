import java.io.File;

//import java.io.*;

public class Main {

	static int extraterror=0;

    public static void main(String[] args) {
        String dir = "F:/search_engine/5";

        //debug
//        String dir = "F:/search_engine/5/www.tsinghua.edu.cn/publish/env/6420/20140111194453647986157/14-1-10.doc";

        File dirFile =  new File(dir);

        //debug
//        File testFile = new File(filePath);
//        resloveFile(testFile);
        Extractor.init();

         resloveFile(dirFile);


         Extractor.end();

    }

    private static void resloveFile(File file){
        if(file.isFile() ){
            String path[] = file.getPath().split("/");
            if(path[path.length-1].matches(".*.html$")){
                try{
                	Extractor.extractHtml(file);
                }catch (Exception e){
                    extraterror++;
                	e.printStackTrace();
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
            if(path[path.length-1].matches(".*.docx?$")){
                try{
                	Extractor.extractDoc(file);
                }catch (Exception e){
                    extraterror++;
                    System.out.println("extract error  "+ file.getPath());
                }
            }
            if(path[path.length-1].matches(".*.xlsx?$")){
                try{
                	Extractor.extractXLs(file);
                }catch (Exception e){
                    extraterror++;
                    System.out.println("extract error  "+ file.getPath());
                }
            }
            if(path[path.length-1].matches(".*.pptx?$")){
                try{
                	Extractor.extractPpt(file);
                }catch (Exception e){
                    extraterror++;
                    System.out.println("extract error  "+ file.getPath());
                }
            }
        }else {
            //System.out.println("鍒嗘瀽鏂囦欢澶�  "+file.getPath());
            for (File subFile :
                    file.listFiles() ) {
                resloveFile(subFile);
            }
        }
    }

//    private static void writeLog(String log){
//        try {
//            ops.write((log+"/n").getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
