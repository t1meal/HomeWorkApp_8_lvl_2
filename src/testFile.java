import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class testFile {

    public static void main(String[] args) {
        File file = new File("C:\\IdeaProjects\\HomeWorkApp_8_lvl_2");
        new testFile().dirWalker(file);

        byte [] arr = { 12, 32, 33, -5, -8, -9};

        try (ByteArrayInputStream bis = new ByteArrayInputStream(arr)){
            int a;
            while ( (a = bis.read()) != -1){
                System.out.println(a + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            baos.write(10);
            baos.write(11);
            System.out.println(Arrays.toString(baos.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    private void dirWalker(File file){

        for (File child: file.listFiles()) {
            if (child.isDirectory()){
                dirWalker(child);
            } else {
                System.out.println(child.getAbsolutePath());
            }
        }
    }
}
