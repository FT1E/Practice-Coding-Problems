import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MinSuccessors {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens;
        tokens = br.readLine().split(" ");

        int base = Integer.parseInt(tokens[0]);
        String a = tokens[1];
        String b = tokens[2];

        if(a.length() < b.length()) {
            System.out.println(0);
            return;
        }



        int len = a.length();


        int i=0;
        while (i<len && !(a.charAt(i) < b.charAt(i))){
            i++;
        }
        if(i==len) {
            System.out.println(a);
            return;
        }
        System.out.print(a.substring(0, i+1));
        System.out.println("0".repeat(len-i-1));

    }
}
