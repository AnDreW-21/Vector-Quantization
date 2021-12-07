import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class vector_compression {
    private File file = new File("C:\\Users\\andre\\Desktop\\test1.jpg");
    private int width, height;
    private float[][] mainVecAvr;
    private int newWidth, newHeight;
    private int[][] imgVec;
    private Raster raster;
    private int numOfVec;

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
    public int calcVecSize() {
        int vecSize;
        vecSize = width * height;
        return vecSize;
    }

    public void CalSubVecNum() {
        newWidth = ((width % 4) + width);
        newHeight += (height % 4) + height;
        System.out.println(newWidth + "aa" + width);
        System.out.println(newHeight);
        numOfVec=(newHeight * newWidth) / 4;
    }

    public void mainVecAvg() {
        mainVecAvr = new float[2][2];
        float firElmAvr = 0, secElmAvr = 0, thirdElmAvr = 0, fourthElmAvr = 0;
        for (int i = 0; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                    firElmAvr += imgVec[i][j];
            }

        }
        System.out.println(firElmAvr);
        mainVecAvr[0][0]=firElmAvr/numOfVec;
        for (int i = 0; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                    secElmAvr += imgVec[i][j];
            }
        }
        System.out.println(secElmAvr);
        mainVecAvr[0][1]=secElmAvr/numOfVec;
        for (int i = 1; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                    thirdElmAvr += imgVec[i][j];
            }
        }
        System.out.println(thirdElmAvr);
        mainVecAvr[1][0]=thirdElmAvr/numOfVec;
        for (int i = 1; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                    fourthElmAvr += imgVec[i][j];
            }
        }
        System.out.println(fourthElmAvr);
        mainVecAvr[1][1]=fourthElmAvr/numOfVec;
    }
    public void printMainAvrVec(){
        for (int i = 0; i < 2; i ++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(mainVecAvr[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void print_imgVector() {
        System.out.println(height);
        System.out.println(width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(imgVec[i][j] + " ");
            }
        }
        System.out.println("\n");
    }


    public static void main(String[] args) throws IOException {
        System.out.println("we are here");
        vector_compression test =new vector_compression();
        test.make_photo_vector();
        test.print_imgVector();
        test.CalSubVecNum();
        test.mainVecAvg();
        test.printMainAvrVec();
//        int [][] x=new int[2][2];
//        Vector<int[][]> v = new Vector<>(200);
//        v.add(x);
//        int [][]y=new int[2][2];
//        y=x;
//        v.removeElementAt(0);
//        System.out.println(y);
    }
}
