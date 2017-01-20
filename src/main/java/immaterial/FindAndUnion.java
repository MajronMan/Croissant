package immaterial;

import java.util.*;

/**
 * Created by MajronMan on 15.11.2016.
 */


class FindAndUnion<T> {
    private class FUNode<M>{
        M value;
        FUNode<M> parent;
        int rank;

        FUNode(M value){
            this(value, null, 0);
        }

        FUNode(M value, FUNode<M> parent, int rank) {
            this.value = value;
            this.parent = parent;
            this.rank = rank;
        }
    }

    private HashMap<T, FUNode<T>> nodes;
    private int setsCount = 0;

    FindAndUnion(Iterable<T> values){
        nodes = new HashMap<>();
        for (T val : values) {
            makeSet(val);
        }
    }

    boolean isForest(){
        return setsCount > 1;
    }

    private FUNode<T> makeSet(T val){
        FUNode<T> node = new FUNode<>(val);
        nodes.put(val, node);
        setsCount++;
        return node;
    }

    T Find(T x){
        FUNode<T> node = nodes.get(x);
        return Find(node).value;
    }

    private FUNode<T> Find(FUNode<T> x){
        if(x.parent == null)
            return x;
        x.parent = Find(x.parent);
        return x.parent;
    }

    void Union(T x, T y){
        FUNode<T> nx = nodes.get(x), ny = nodes.get(y);
        Union(nx, ny);
    }

    private void Union(FUNode<T> x, FUNode<T> y){
        FUNode<T> xRoot = Find(x), yRoot = Find(y);
        if(xRoot.rank > yRoot.rank){
            yRoot.parent = xRoot;
            setsCount--;
        }
        else if(xRoot.rank < yRoot.rank){
            xRoot.parent = yRoot;
            setsCount--;
        }
        else if(!xRoot.equals(yRoot)){
            yRoot.parent = xRoot;
            xRoot.rank++;
            setsCount--;
        }
    }
}
