package org.example.game;

import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.traversal.DFSIterator;
import org.graph4j.traversal.SearchNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dictionary {
    private String dictPath;
    private List<String> allWords = new ArrayList<String>();
    private Graph gr;

    public Dictionary(String dictPath) {
        this.dictPath = dictPath;
        gr = GraphBuilder.empty().buildDigraph();
        gr.addLabeledVertex("ROOT");

        for(char c = 'a'; c <= 'z'; c++) {
            String word = String.valueOf(c);
            gr.addLabeledVertex(word);
            gr.addLabeledEdge("ROOT", word, word);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dictPath))) {
            String line;

            while ((line = br.readLine()) != null) {
                addWordToGraph(line);
                allWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addWordToGraph(String word) {
        StringBuilder label = new StringBuilder();
        for(char c : word.toCharArray()) {
            label.append(c);
            if(gr.findVertex(label.toString()) == -1)
            {
                int n = gr.addLabeledVertex(label.toString());
                gr.addLabeledEdge(label.substring(0,label.toString().length() - 1), label.toString(), c);
                gr.setVertexWeight(n, label.toString().equals(word) ? 1 : 0);
            }
        }
    }

    public void printTree()
    {
        DFSIterator iter = new DFSIterator(gr);
        while (iter.hasNext()) {
            var nxt = iter.next();
            for (int i = 0; i < nxt.level(); i++) System.out.print("  ");
            System.out.println(gr.getVertexLabel(nxt.vertex()));
        }
    }


    public boolean containsWord(String word) {
        var iter = gr.neighborIterator(0);
        while(iter.hasNext())
        {
            var nxt = iter.next();
            if(word.equals(gr.getVertexLabel(nxt).toString()))
            {
                return gr.getVertexWeight(nxt) == 1;
            }
            if(word.startsWith(gr.getVertexLabel(nxt).toString()))
            {
                iter = gr.neighborIterator(nxt);
            }
        }

        return false;
    }
    public int containsWordPart(String word) {
        var iter = gr.neighborIterator(0);
        while(iter.hasNext())
        {
            var nxt = iter.next();
            if(word.equals(gr.getVertexLabel(nxt).toString()))
            {
                return (int)gr.getVertexWeight(nxt);
            }
            if(word.startsWith(gr.getVertexLabel(nxt).toString()))
            {
                iter = gr.neighborIterator(nxt);
            }
        }

        return -1;
    }

    public boolean naiveContainsWord(String word) {
        return allWords.contains(word);
    }
}
