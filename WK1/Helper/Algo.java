package Helper;

import java.util.*;

public class Algo {
    public static boolean CheckStableSet(List<List<Integer>> matrix, int k)
    {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (i != j)
                    matrix.get(i).set(j, matrix.get(i).get(j) == 0 ? 1 : 0);
            }
        }
        return CheckClique(matrix, k);
    }
    public static boolean CheckClique(List<List<Integer>> matrix, int k)
    {
        HashMap<Integer, List<Integer>> neighbours = new HashMap<>();

        for (int i = 0; i < matrix.size(); i++) {
            List<Integer> ngh = new ArrayList<>();

            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(j) == 1) {
                    ngh.add(j);
                }
            }

            neighbours.put(i, ngh);
        }

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            if (neighbours.containsKey(i) && neighbours.get(i).size() >= k - 1) {
                candidates.add(i);
            }
        }

        return findClique(neighbours, new ArrayList<>(), candidates, k);
    }
    
    private static boolean findClique(HashMap<Integer, List<Integer>> neighbours, List<Integer> currentClique, List<Integer> candidates, int k) {
        if (currentClique.size() == k) {
            System.err.println(k + " " + currentClique);
            return true;
        }

        for (int node : new ArrayList<>(candidates)) {
            if (isFullyConnected(neighbours, currentClique, node)) {
                currentClique.add(node);
                candidates.remove(Integer.valueOf(node));

                if (findClique(neighbours, currentClique, candidates, k)) {
                    return true;
                }

                currentClique.remove(currentClique.size() - 1);
            }
        }

        return false;
    }

    private static boolean isFullyConnected(HashMap<Integer, List<Integer>> neighbours, List<Integer> clique, int node) {
        for (int existingNode : clique) {
            if (!neighbours.get(existingNode).contains(node)) {
                return false;
            }
        }
        return true;
    }
    
}
