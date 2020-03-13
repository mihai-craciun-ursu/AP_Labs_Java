import java.util.Random;

public class Main {

    public static int getDigitsSum(int n){
        int sum = 0;
        while (n != 0)
        {
            sum = sum + n % 10;
            n = n/10;
        }
        return sum;
    }

    public static void main(String[] args) {
        Random random = new Random();
        String languages[] = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n1, sum=0;

        do{
            n1 = (random.nextInt(1_000_000)*3 + Integer.parseInt("10101", 2) + Integer.parseInt("FF", 16)) * 6;
            sum = getDigitsSum(n1);
        }while(sum > 10);

        System.out.printf("Willy-nilly, this semester I will learn %s\n", languages[sum]);


    }

}
