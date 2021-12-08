import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class vector_compression {
    private File file = new File("C:\\Users\\andre\\Desktop\\test1.jpg");
    private int width, height;
    private float[][] mainVecAvr;
    private int newWidth, newHeight;
    private int[][] imgVec;
    private Raster raster;
    private Vector<int[][]> vectors = new Vector<>();
    private Vector<int[][]> firstHalf = new Vector<>();
    private Vector<int[][]> secHalf = new Vector<>();
    private Vector<int[][]> mainSplit = new Vector<>(2);
    private ArrayList<Vector<int[][]>> nearest = new ArrayList<>();

    private int numOfVec;
    private Vector<int[][]> getFirstHalf(){
        return firstHalf;
    }
    private Vector<int[][]> getSecHalf(){
        return secHalf;
    }
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

    void makeFirstSeb() {
        int[][] arr1, arr2;
        arr1 = mainSplit.get(0);
        arr2 = mainSplit.get(1);
        for (int i = 0; i < vectors.size(); i++) {
            int avr1 = 0, avr2 = 0;
            int[][] dummy = vectors.get(i);
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    avr1 += ((arr1[j][k] - dummy[j][k]));
                    avr1 = (int) Math.pow(avr1, 2);
                    avr2 += ((arr2[j][k] - dummy[j][k]));
                    avr2 = (int) Math.pow(avr2, 2);
                }
            }
            if (avr1 < avr2) {
                firstHalf.add(dummy);
            } else
                secHalf.add(dummy);

        }
    }

    public void CalSubVecNum() {
        newWidth = ((width % 4) + width);
        newHeight += (height % 4) + height;
        numOfVec = (newHeight * newWidth) / 4;
    }

    public int[][] avrage(Vector<int[][]> seb) {
        int firElmAvr = 0, secElmAvr = 0, thirdElmAvr = 0, fourthElmAvr = 0;
        for (int i = 0; i < seb.size(); i++) {
            int[][] arr2D = seb.get(i);
            firElmAvr += arr2D[0][0];
            secElmAvr += arr2D[0][1];
            thirdElmAvr += arr2D[1][0];
            fourthElmAvr += arr2D[1][1];
        }
        int[][] avr = new int[2][2];
        firElmAvr /= seb.size();
        secElmAvr /= seb.size();
        thirdElmAvr /= seb.size();
        fourthElmAvr /= seb.size();
        avr[0][0] = firElmAvr;
        avr[0][1] = secElmAvr;
        avr[1][0] = thirdElmAvr;
        avr[1][1] = fourthElmAvr;
        return avr;
    }

    public Vector<int[][]> matrixForAvr(int[][] x) {
        Vector<int[][]> matrix = new Vector<>();
        int[][] dummy1, dummy2;
        dummy1 = new int[2][2];
        dummy2 = new int[2][2];
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++){
                dummy1[i][j]=(x[i][j]-1);
                dummy2[i][j]=(x[i][j]+1);
            }
        }
        matrix.add(dummy1);
        matrix.add(dummy2);

        return matrix;
    }

    public void mainVecAvg() {
        mainVecAvr = new float[2][2];
        float firElmAvr = 0, secElmAvr = 0, thirdElmAvr = 0, fourthElmAvr = 0;
        for (int i = 0; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                firElmAvr += imgVec[i][j];
            }
        }
        mainVecAvr[0][0] = firElmAvr / numOfVec;
        for (int i = 0; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                secElmAvr += imgVec[i][j];
            }
        }
        mainVecAvr[0][1] = secElmAvr / numOfVec;
        for (int i = 1; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                thirdElmAvr += imgVec[i][j];
            }
        }
        mainVecAvr[1][0] = thirdElmAvr / numOfVec;
        for (int i = 1; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                fourthElmAvr += imgVec[i][j];
            }
        }
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

    public void spiltOnVec() {
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
                vectors.add(book);
            }
        }
    }

    public void print_imgVector() {
        System.out.println(imgVec[0].length);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(imgVec[i][j] + " ");
            }
        }
        System.out.println("\n");
    }
//    void arrange(){
//        int avr1=0,avr2=0,avr3=0,avr4=0;
//        for (int i=0; i<vectors.size();i++){
//            for (int k=0;k<2;k++){
//                for (int n=0;n<2;n++);
//                avr1
//            }
//        }
//    }


    public static void main(String[] args) throws IOException {
        vector_compression test = new vector_compression();
        test.make_photo_vector();
        test.CalSubVecNum();
        test.mainVecAvg();
        test.makeMainSplit();
        test.spiltOnVec();
        test.makeFirstSeb();
        Vector<int[][]> vectors ;
        Vector<int[][]> dum;
        vectors=test.getFirstHalf();
        int [][]dummy;
        dummy=test.avrage(vectors);
        vectors=test.matrixForAvr(dummy);
        dum=test.getSecHalf();
        dummy=test.avrage(dum);
        dum=test.matrixForAvr(dummy);
        vectors.add(dum.get(0));
        vectors.add(dum.get(1));
        System.out.println(vectors.size());

       // test.printMainAvrVec();




    }
}
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
