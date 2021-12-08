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
    private Vector<int[][]> splited = new Vector<>();
    public Vector<int[][]> mainSplit = new Vector<>(2);
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
        numOfVec = (newHeight * newWidth) / 4;
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
        mainVecAvr[0][0] = firElmAvr / numOfVec;
        for (int i = 0; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                secElmAvr += imgVec[i][j];
            }
        }
        System.out.println(secElmAvr);
        mainVecAvr[0][1] = secElmAvr / numOfVec;
        for (int i = 1; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                thirdElmAvr += imgVec[i][j];
            }
        }
        System.out.println(thirdElmAvr);
        mainVecAvr[1][0] = thirdElmAvr / numOfVec;
        for (int i = 1; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                fourthElmAvr += imgVec[i][j];
            }
        }
        System.out.println(fourthElmAvr);
        mainVecAvr[1][1] = fourthElmAvr / numOfVec;
    }

    public void printMainAvrVec() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(mainVecAvr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void makeMainSplit() {
        int[][] firstVec = new int[2][2];
        int[][] secVec = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                firstVec[i][j] = (int) mainVecAvr[i][j] - 1;
                secVec[i][j] = (int) mainVecAvr[i][j] + 1;
            }
        }
        mainSplit.add(firstVec);
        mainSplit.add(secVec);
    }

    public void spilt() {
        ;
        for (int i = 0; i < newWidth; i += 2) {
            for (int j = 0; j < newHeight; j += 2) {
                int[][] book = new int[2][2];
                for (int r = i; r < (i + 2); ++r) {
                    for (int c = j; c < (j + 2); ++c) {
                        if (r < width && c < height)
                            book[r - i][c - j] = imgVec[r][c];
                        else
                            book[r - i][c - j] = 0;
                    }
                }
                splited.add(book);
            }
        }
    }

    public void print_imgVector() {
        System.out.println(height);
        System.out.println(width);
        System.out.println(imgVec.length + " al length ya abo 3amo");
        System.out.println(imgVec[0].length);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(imgVec[i][j] + " ");
            }
        }
        System.out.println("\n");
    }


    public static void main(String[] args) throws IOException {
        vector_compression test = new vector_compression();
        test.make_photo_vector();
        test.print_imgVector();
        test.CalSubVecNum();
        test.mainVecAvg();
        test.makeMainSplit();
        test.printMainAvrVec();
        test.spilt();
//        int[][] x=new int[5][6];
//        x = test.mainSplit.get(1);
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                System.out.print(x[i][j] + " ");
//            }
//            System.out.println();
//        }
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 6; j++) {
//                x[i][j]=2;
//            }
//        }
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 6; j++) {
//                x[i][j]=2;
//            }
//        }


//        Vector<int[][]> v = new Vector<>(200);
//        v.add(x);
//        int [][]y=new int[2][2];
//        y=x;
//        v.removeElementAt(0);
//        System.out.println(y);
    }
}
