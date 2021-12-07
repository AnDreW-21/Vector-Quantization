import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

public class vector_compression {
    private File file = new File("C:\\Users\\andre\\Desktop\\test1.jpg");
    private int width,height;
    private int [][] mainVecAvr;
    private int newWidth,newHeight;
    private int[][] imgVec;
    private Raster raster;
    public void make_photo_vector() throws IOException {
        BufferedImage img = ImageIO.read(file);
        width = img.getWidth();
        height = img.getHeight();
        imgVec = new int[width][height];
        raster = img.getData();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imgVec[i][j] = raster.getSample(i, j, 0);
            }
        }
    }
    public int calcVecSize(){
        int vecSize;
        vecSize=width*height;
        return vecSize;
    }
    public int CalSubVecNum(){
        newWidth=((width%4)+width);
        newHeight+=(height%4)+height;
        System.out.println(newWidth+"aa"+width);
        System.out.println(newHeight);
        return (newHeight*newWidth)/4;
    }
    public void vecAvg(){

        int firElmAvr,secElmAvr,thirdElmAvr,fourthElmAvr;

    }
    public void print_imgVector(){
        System.out.println(height);
        System.out.println(width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(imgVec[i][j]+" ");
            }
        }
        System.out.println("\n");
    }


    public static void main(String []args) throws IOException {
        System.out.println("we are here");
        System.out.println(285%4);
        System.out.println(298%4);
        System.out.println((300*300)/4);
        vector_compression test =new vector_compression();
        test.make_photo_vector();
        test.print_imgVector();
        int x=test.CalSubVecNum();
        System.out.println(x);

    }
}
