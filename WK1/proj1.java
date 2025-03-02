import Helper.Graph;

public class proj1 {
    public static void main(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Wrong parameter count.");
            return;
        }
        int n, k;
        
        n = Integer.parseInt(args[0]);
        k = Integer.parseInt(args[1]);
        
        long startTime = System.nanoTime();
        System.out.println("Start");
        
        
        Graph g = new Graph(n, k);
        
        if (n > 50)
        {
            long endTime = System.nanoTime(); // End time
            long duration = endTime - startTime; // Compute duration

            System.out.println("Execution time: " + duration / 1_000_000.0 + " ms");
        }
        else
            g.DisplayMatrix();
    }
}