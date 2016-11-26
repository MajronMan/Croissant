package Croissant.Abstract;

import Croissant.Level.Room;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by MajronMan on 09.11.2016.
 */

public class Graph <T>{
    public class Edge{
        private Node from;
        private Node to;
        private int weight;

        Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }

    public class Node{
        private int number;
        private T value;
        Collection<Node> neighbours;

        Node (T val, int i){
            value = val;
            number = i;
            neighbours = new ArrayList<>();
        }

        public int getNumber(){
            return number;
        }

        public T getValue(){
            return value;
        }
    }

    private boolean digraph = false;
    private ArrayList<Node> vertices;
    private ArrayList<Edge> edges;

    private int sign(int a){
        if(a < 0) return -1;
        if(a == 0) return 0;
        return 1;
    }

    public Graph(){
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public Graph(T[] values, int[][] weights){
        this(values, weights, false);
    }

    public Graph(T[] values, int[][] weights, boolean digraph){
        this();
        this.digraph = digraph;
        for (int i = 0; i < values.length; i++) {
            Node node = new Node(values[i], i);
            vertices.add(node);
        }

        createEdges(weights);
    }

    private void createEdges(int[][] weights){
        for(int i=0; i<vertices.size(); i++){
            for(int j = digraph? 0: i+1; j<vertices.size(); j++){
                if(weights[i][j] != 0){
                    Node from = vertices.get(i), to = vertices.get(j);
                    from.neighbours.add(to);
                    if(!digraph)
                        to.neighbours.add(from);
                    edges.add(new Edge(from, to, weights[i][j]));
                }
            }
        }
    }

    public Collection<Edge> Kruskal(){
        ArrayList<Edge> ret = new ArrayList<>();
        List<T> values = vertices.stream().map(u -> u.value).collect(Collectors.toList());
        FindAndUnion<T> forest = new FindAndUnion<>(values);
        PriorityQueue<Edge> left = new PriorityQueue<>(
                (Comparator<Edge>) (o1, o2) -> sign(o1.weight - o2.weight)
        );
        left.addAll(edges);

        while(!left.isEmpty() && forest.isForest()){
            Edge current = left.poll();
            if(!forest.Find(current.from.value).equals(forest.Find(current.to.value))){
                ret.add(current);
                forest.Union(current.from.value, current.to.value);
            }
        }
        return ret;
    }
}
