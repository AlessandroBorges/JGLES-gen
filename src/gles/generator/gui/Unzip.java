package gles.generator.gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.SwingUtilities;

/**
 * From example at 
 *  http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
 * @author mkyong
 * @author Alessandro Borges
 *
 */
public class Unzip
{
    
    private static final String INPUT_ZIP_FILE = "syntax.zip";
    public static final String FOLDER_NAME = "GLESgen";
    
    private final String[] folder_to_copy = {"styles","scripts"};
    private final File[] folders = new File[folder_to_copy.length];    
    private final Class<? extends Unzip> clazz = this.getClass();
    
    private File folder;
    private boolean debug = false;
        
    public static void main(String[] args) {

       SwingUtilities.invokeLater(new Runnable() {        
        @Override
        public void run() {
           runMe();            
        }
    });        
    }
    
    public static void runMe() {
        Unzip unZip = new Unzip();
        try {
            unZip.unzipSyntaxScripts();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Ctor
     */
    public Unzip(){        
    }
    
    /**
     * Unzip the scripts to temp folder
     * @throws IOException - if some issue raise
     * @return an File where the files are placed
     */
    public File unzipSyntaxScripts() throws IOException{
        File folder = getFolder();
        InputStream zipStream = clazz.getResourceAsStream(INPUT_ZIP_FILE);
        unZipIt(zipStream, folder);
        return folder;
    }
    /**
     * Create temp dir and subfolders
     * @return the dir
     * @throws IOException
     */
    protected  File createTempDir() 
            throws IOException {
        Path path = Files.createTempDirectory(FOLDER_NAME);  
        
        File tempDir = path.toFile();
        tempDir.deleteOnExit();
       // if(false)      
        for(int i=0; i < folder_to_copy.length; i++){
            String name = folder_to_copy[i];
            File newDir = new File(tempDir,name);
            boolean ok = newDir.mkdir();
           
            if(!ok && !newDir.exists()){
                System.err.println("Failed to create temp folder: " + name +
                        "\nFile: " + newDir);
                Thread.dumpStack();
            }
            folders[i] = newDir;
        }    
        
        return tempDir;
    } 
    
    /**
     * Created a non temporary dir in java.io.tmpdir
     * @return file
     */
    public   File createNoTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        String baseName = FOLDER_NAME;

        File tempDir = new File(baseDir, baseName);
        if (tempDir.mkdir() || tempDir.isDirectory()) {
            for(int i=0; i < folder_to_copy.length; i++){
                String name = folder_to_copy[i];
                File newDir = new File(tempDir,name);
                boolean ok = newDir.mkdir();
               
                if(!ok && !newDir.exists()){
                    System.err.println("Failed to create temp folder: " + name +
                            "\nFile: " + newDir);
                    Thread.dumpStack();
                }
                folders[i] = newDir;
            }    

        }
        return tempDir;
    }
    
    /**
     * get the folder
     * @return the File folder
     * @throws IOException
     */
    public File getFolder() throws IOException{
        if(folder==null){
            folder = createNoTempDir();
            System.out.println("Temp folder: " + folder);
        }
        return folder;
    }
    
    
    public void unZipIt(InputStream zipStream, File outputFolder)
    throws IOException{
        byte[] buffer = new byte[512];
                              
           //create output directory is not exists
           File folder = outputFolder;
           if(!folder.exists()){
               folder.mkdir();
           }
               
           //get the zip file content
           ZipInputStream zis =  new ZipInputStream(zipStream);
           //get the zipped file list entry
           ZipEntry ze = zis.getNextEntry();
               
           while(ze!=null){
                   
              String fileName = ze.getName();
              File newFile = new File(outputFolder + File.separator + fileName);
              if(debug)
              System.out.println("# file unzip : "+ newFile.getAbsoluteFile());
               
              
               //create all non exists folders
               //else you will hit FileNotFoundException for compressed folder
               String fileParent = newFile.getParent();
               {
                   File parent = new File(fileParent);                  
                   boolean ok = parent.mkdir();
                   boolean isFolder = parent.isDirectory();
                   if(debug)
                   System.out.println(" - Parent folder created ? " + ok+" - Exists ? " + isFolder +" /"+ parent.getName());
               }
              // new File(newFile.getParent()).mkdirs();
               
               if(newFile.isDirectory()){
                   newFile.mkdir();
                   if(debug)
                   System.out.println("newFile is dir !" + newFile);
                   ze = zis.getNextEntry();
                   continue;
               }
                          
               FileOutputStream fos = new FileOutputStream(newFile);             

               int len;
               while ((len = zis.read(buffer)) > 0) {
               fos.write(buffer, 0, len);
               }
                   
               fos.close();   
               ze = zis.getNextEntry();
           }
           
           zis.closeEntry();
           zis.close();
               
           System.out.println("Done");
               
    }
    
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder){

     byte[] buffer = new byte[1024];
        
     try{
            
        //create output directory is not exists
        File folder = new File(outputFolder);
        if(!folder.exists()){
            folder.mkdir();
        }
            
        //get the zip file content
        ZipInputStream zis = 
            new ZipInputStream(new FileInputStream(zipFile));
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();
            
        while(ze!=null){
                
           String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
                
           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
              
            FileOutputStream fos = new FileOutputStream(newFile);             

            int len;
            while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
            }
                
            fos.close();   
            ze = zis.getNextEntry();
        }
        
        zis.closeEntry();
        zis.close();
            
        System.out.println("Done");
            
    }catch(IOException ex){
       ex.printStackTrace(); 
    }
   }    
}