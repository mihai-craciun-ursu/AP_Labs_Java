import java.util.*;

public class Graph {

    private Map<Integer, ArrayList<Integer>> conexComponents = new HashMap<>();
    private int conexNumber;


    public Graph(boolean matrix[][], int n){
        int index = 0;
        boolean[] vizitat = new boolean[n];
        Arrays.fill(vizitat, false);
        Queue<Integer> queue = new LinkedList<>();



        do {
            index++;

            int tempNode = firstNodeToVizit(vizitat);
            queue.add(tempNode);
            conexComponents.put(index, new ArrayList<>());
            conexComponents.get(index).add(tempNode);
            vizitat[tempNode] = true;

            while (!queue.isEmpty()) {
                int node = queue.remove();
                for(int i = 0; i<n; i++){
                    if(matrix[node][i] && !vizitat[i]){
                        queue.add(i);
                        conexComponents.get(index).add(i);
                        vizitat[i] = true;
                    }
                }
            }
        }while(moreNodesAreToVisit(vizitat));

        this.conexNumber = index;

    }

    public boolean moreNodesAreToVisit(boolean vizitat[]){
        for(int i=0; i<vizitat.length; i++){
            if(!vizitat[i]){return true;};
        }
        return false;
    }

    public int firstNodeToVizit(boolean vizitat[]){
        for(int i=0; i<vizitat.length; i++){
            if(!vizitat[i]){return i;};
        }
        return 0;
    }


    public Map<Integer, ArrayList<Integer>> getConexComponents() {
        return conexComponents;
    }

    public int getConexNumber() {
        return conexNumber;
    }
}
