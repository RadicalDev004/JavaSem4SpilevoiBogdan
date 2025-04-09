package org.example.helper;

import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.spanning.BoruvkaMinimumSpanningTreeBase;
import org.graph4j.spanning.BoruvkaMinimumSpanningTreeDefault;
import org.graph4j.spanning.WeightedSpanningTreeIterator;

import java.util.HashSet;
import java.util.Set;

public class MSTCompute {
    private Graph current;
    public Graph all;

    public MSTCompute()
    {
        current =  GraphBuilder.labeledVertices(GameManager.allCircleButtons).buildGraph();
        all = GraphBuilder.labeledVertices(GameManager.allCircleButtons).buildGraph();

        for(var b1 : GameManager.allCircleButtons)
        {
            for(var b2 : GameManager.allCircleButtons)
            {
                if(GameManager.hasLine(b1, b2) && b1 != b2)
                {
                    current.addEdge(GameManager.allCircleButtons.indexOf(b1), GameManager.allCircleButtons.indexOf(b2), GameManager.getScore(b1, b2));
                }
            }
        }
        for(var b1 : GameManager.allCircleButtons)
        {
            for(var b2 : GameManager.allCircleButtons)
            {
                if(b1 != b2)
                {
                    all.addEdge(GameManager.allCircleButtons.indexOf(b1), GameManager.allCircleButtons.indexOf(b2), GameManager.getScore(b1, b2));
                }
            }
        }

    }

    public void computeNextEdge()
    {
        WeightedSpanningTreeIterator bt = new WeightedSpanningTreeIterator(all);

        if(bt.hasNext())
        {
            for (var e : bt.next())
            {
                //System.out.println(e.source() + " " + e.target() + " " + e.weight());
                ConnectingLine cl = new ConnectingLine(GameManager.allCircleButtons.get(e.source()).getX(), GameManager.allCircleButtons.get(e.source()).getY(), GameManager.allCircleButtons.get(e.target()).getX(), GameManager.allCircleButtons.get(e.target()).getY(), 5, false);

                /*boolean isNotAtOneEnd = false;
                for(var line : GameManager.allLines)
                {
                    if(!line.hasCircleButtonAtOneEnd(GameManager.allCircleButtons.get(e.source())) && !line.hasCircleButtonAtOneEnd(GameManager.allCircleButtons.get(e.target())))
                    {
                        isNotAtOneEnd = true;
                        break;
                    }
                }
                if(isNotAtOneEnd) continue;*/

                if(GameManager.hasLine(cl))
                {
                    //System.out.println("Already has line");
                    continue;
                }
                GameManager.addLine(cl, false);
                //System.out.println(GameManager.allCircleButtons.get(e.source()).getX() + " " + GameManager.allCircleButtons.get(e.source()).getY() + " | " + GameManager.allCircleButtons.get(e.target()).getX() + " " + GameManager.allCircleButtons.get(e.target()).getY());
                break;
            }
        }
    }

}
