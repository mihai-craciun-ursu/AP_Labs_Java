import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main2 {

    public static void main(String args[]){
        if(args.length < 3){
            return;
        }

        int max = 0;
        int min = Integer.MAX_VALUE;

        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        String[] words = new String[n];
        boolean[][] adjacency = new boolean[n][n];
        int[] nrOfNeighbors = new int[n];
        Arrays.fill(nrOfNeighbors, 0);

        for(int i = 0; i < n; i++){
            words[i] = generateWord(args, k);
            System.out.println(words[i]);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++) {
                if (i != j) {
                    if (isAdiacent(words[i], words[j], k)) {
                        adjacency[i][j] = true;
                        nrOfNeighbors[i]++;
                    } else {
                        adjacency[i][j] = false;
                    }
                }
                System.out.print(adjacency[i][j] ? "1 " : "0 ");
            }

            if(nrOfNeighbors[i] > max){
                max = nrOfNeighbors[i];
            }

            if(nrOfNeighbors[i] < min){
                min = nrOfNeighbors[i];
            }

            System.out.println();
        }

        System.out.println();
        System.out.print("MAX: ");
        for(int i = 0; i < n; i++){
            if(checkLineSumOfArray(adjacency[i], n) == max){
                System.out.print(words[i] + "(" + max + ") ");
            }
        }

        System.out.println();

        System.out.print("MIN: ");
        for(int i = 0; i < n; i++){
            if(checkLineSumOfArray(adjacency[i], n) == min){
                System.out.print(words[i] + "(" + min + ") ");
            }
        }

        System.out.println();
        System.out.println();


        Graph graph = new Graph(adjacency, n);

        System.out.println("Componente conexe: " + graph.getConexNumber());
        System.out.println();
        System.out.println();

        for(Integer key : graph.getConexComponents().keySet()){
            System.out.println("Componenta nr. " + key);
            for(Integer wordIndex : graph.getConexComponents().get(key)){
                System.out.print(words[wordIndex] + " ");
            }
            System.out.println();
            System.out.println();
        }

    }



    public static boolean isAdiacent(String w1, String w2, int k){
        for(int i = 0; i < k; i++){
            if(w1.indexOf(w2.charAt(i)) > -1){
                return true;
            }
        }
        return false;
    }

    public static String generateWord(String args[], int k){
        String word = "";
        Random random = new Random();
        for(int i = 0; i < k; i++){
            word = word.concat(args[random.nextInt(args.length - 2) + 2]);
        }
        return word;
    }

    public static int checkLineSumOfArray(boolean adjacency[], int n){
        int sum = 0;

        for(int i = 0; i < n; i++){
            if(adjacency[i]){
                sum++;
            }
        }

        return sum;
    }

}
