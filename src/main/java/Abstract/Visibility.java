//package Abstract;
//
//import Engine.GameController;
//import Engine.GameObject;
//import Level.Cell;
//import Level.Map;
//import sun.misc.Queue;
//
///**
// * Created by MajronMan on 18.12.2016.
// */
//public class Visibility {
//    private boolean visible;
//    private boolean opaque;
//    private Visibility[][] neighbours = new Visibility[3][3];
//    private Vector position;
//    private GameObject myGameObject;
//    private Map myMap;
//    public boolean visited;
//
//    public Visibility(boolean visible, boolean opaque, Map map, GameObject myGameObject){
//        this.visible = visible;
//        this.opaque = opaque;
//        this.myGameObject = myGameObject;
//        position = myGameObject.getPosition();
//        myMap = map;
//    }
//
//    public void addNeighbours(){
//        for(Cell neighbour : myMap.neighbours(position.getX(), position.getY())){
//            Vector delta = position.getDelta(neighbour.getPosition());
//            neighbours[delta.getX()+1][delta.getY()+1] = neighbour.getVisibility();
//        }
//    }
//
//    public void see() {
//        visible = true;
//        myGameObject.draw();
//    }
//
//
//    public void propagateWave(Vector direction){
//        if(opaque || visible) return;
//        Vector[] to = { direction, direction.rotate(45), direction.rotate(-45)};
//        for(Vector v: to){
//            Visibility next = neighbours[v.getX()+1][v.getY()+1];
//            if(next != null){
//                next.see();
//                next.propagateWave(v);
//            }
//        }
//    }
//
//    public boolean isVisible() {
//        return visible;
//    }
//
//    public void setVisible(boolean visible) {
//        this.visible = visible;
//    }
//
//    public boolean isOpaque() {
//        return opaque;
//    }
//
//    public void setOpaque(boolean opaque) {
//        this.opaque = opaque;
//    }
//
//    private void BFS() {
//        GameController.getCurrentMap().HideCells();
//        Queue<Cell> q = new Queue<>();
//        q.enqueue((Cell) myGameObject);
//        ((Cell) myGameObject).getVisibility().setVisible(true);
//        while (!q.isEmpty()) {
//            try {
//                Cell p = q.dequeue();
//                p.getVisibility().visited = true;
//                p.color = cur;
//                neighbours(p.x, p.y).stream().filter(f -> f.getWalkable() && !f.visited).forEach(q::enqueue);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
