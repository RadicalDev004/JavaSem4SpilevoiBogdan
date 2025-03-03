package Helper;

import java.util.*;

//Spilevoi Bogdan

public class Graph {
    private int n;
    private int k;
    private HashMap<Integer, Boolean> cliqueNodes = new HashMap<>();
    private HashMap<Integer, Boolean> setNodes = new HashMap<>();
    private HashMap<Integer, Boolean> otherNodes = new HashMap<>();
    private Random rand = new Random();

    List<List<Integer>> matrix;

    
    public Graph(int n, int k)
    {
        this.n = n;
        this.k = k;
        matrix = new ArrayList<>();
        for(int i = 0; i < n; i++)
        {
            matrix.add(new ArrayList<>(Collections.nCopies(n, 0)));
        }
        if(k <= n)
        {
            GenerateClique();
            GenerateSet();
        }
        GenerateRest();
    }
    
    public void GenerateClique()
    {
        for (int i = 0; i < k; i++) {
            int rnd = rand.nextInt(n);
            while (cliqueNodes.containsKey(rnd))
                rnd = rand.nextInt(n);

            cliqueNodes.put(rnd, true);
        }

        for (var entry1 : cliqueNodes.entrySet()) {
            for (var entry2 : cliqueNodes.entrySet()) {
                if (!entry1.getKey().equals(entry2.getKey())) {
                    matrix.get(entry1.getKey()).set(entry2.getKey(), 1);
                    matrix.get(entry2.getKey()).set(entry1.getKey(), 1);
                }
            }
        }
        //System.out.println("Got Clique");

    }
    
    public int GetEdges()
    {
        int edges = 0;
        for (var h : matrix) {
            for (var v : h) {
                if (v == 1)
                    edges++;
            }
        }
        if (edges % 2 == 1)
            return -1;
        return edges / 2;
    }
    
    public void ShowMaxDegree()
    {
        int max = 0, vert = 0, ind = 0;
        for (var h : matrix) {
            int sum = 0;
            for (var v : h) {
                if (v == 1)
                    sum++;
            }
            if (sum > max) {
                max = sum;
                vert = ind;
            }
            ind++;
        }
        System.out.println("Δ(G)=" + max + " for vertex" + vert);
    }
    public void ShowMinDegree()
    {
        int min = 999999999, vert = 0, ind = 0;
        for(var h : matrix)
        {
            int sum = 0;
            for (var v : h) {
                if (v == 1)
                    sum++;
            }
            if(sum < min)
            {
                min = sum;
                vert = ind;
            }
            ind ++;
        }
        System.out.println("δ(G)=" + min + " for vertex" + vert);
    }
    
    public void GenerateSet()
    {        
        for (int i = 0; i < k; i++) {

            int rnd = rand.nextInt(n);
            while (setNodes.containsKey(rnd) || IntersectIndependentSet(rnd))
                rnd = rand.nextInt(n);

            setNodes.put(rnd, true);
        }
        //System.out.println("Got Set");
    }
    public void GenerateRest()
    {
        for(int i = 0; i < n; i++)
        {
            if (cliqueNodes.containsKey(i))
                continue;
            
            List<Integer> available = new ArrayList<>();    
            
            for(int j = 0; j < n; j++)
            {
                if(i == j || (setNodes.containsKey(i) && setNodes.containsKey(j)) || matrix.get(i).get(j) == 1) 
                    continue;
                available.add(j);
                
            }
            
            int cnt = rand.nextInt(available.size()) + 1;
            //System.out.println("Adding " + cnt + " to " + i + " out of " + available.size());
            
            while(cnt > 0)
            {
                int rnd = rand.nextInt(available.size());
                otherNodes.put(available.get(rnd), true);

                matrix.get(i).set(available.get(rnd), 1);
                matrix.get(available.get(rnd)).set(i, 1);

                available.remove(rnd);
                cnt--;
            }
            //System.out.println("Got one in rest");
            
        }
    }
    
    public void DisplayMatrix()
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
        for (var a : cliqueNodes.entrySet()) {
            System.out.print(a.getKey() + " ");
        }
        System.out.println();
        for (var a : setNodes.entrySet()) {
            System.out.print(a.getKey() + " ");
        }
        System.out.println();
        for (var a : otherNodes.entrySet()) {
            System.out.print(a.getKey() + " ");
        }
        System.out.println();
    }
    
    public boolean FindClique(int k)
    {
        System.out.println(k + " ");
        return Algo.CheckClique(matrix, k);
    }
    
    private boolean IntersectIndependentSet(int node)
    {
        for(var a : setNodes.entrySet())
        {
            if (matrix.get(node).get(a.getKey()) == 1)
                return true;
        }
        return false;
    }
}
