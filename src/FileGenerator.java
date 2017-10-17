import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class FileGenerator {

    public static void main(String[] args) throws FileNotFoundException {
        Random random = new Random();
        int tmp1, tmp2;
        int target;
        PrintWriter zapis = new PrintWriter("data2.txt");
        zapis.println("10000");                                     //ile danych
        zapis.println("3");                                         //ile kolumn
        for (int i = 0; i < 10000; i++) {
            tmp1 = random.nextInt()%40;
            tmp2 = random.nextInt()%40;
            if(tmp1 + tmp2 >10)
                target = 1;
            else
                target = 0;

            zapis.println(tmp1+" "+tmp2+" "+target);
        }
    }
}
