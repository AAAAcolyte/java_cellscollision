public class CollisionDetector extends Thread{
  volatile boolean collision;
  private PriorityQueueInterface<Object2D> sortedPoints;
  private AABB region;
  private QuadTree quadTree;

  public CollisionDetector(PriorityQueueInterface<Object2D> sortedPoints,AABB
      region,QuadTree quadTree){
    collision = false;
    this.sortedPoints = sortedPoints;
    this.region = region;
    this.quadTree = quadTree;
  }

  @Override
  public void run() {
    while (!collision && !sortedPoints.isEmpty()) {
      Object2D toAdd;
      synchronized (sortedPoints) {
        toAdd = sortedPoints.peek();
        sortedPoints.remove();
      }
      Point2D leftTop = new Point2D(toAdd.getCenter().x - toAdd.getSize(),
          toAdd.getCenter().y - toAdd.getSize());
      Point2D rightBottom = new Point2D(toAdd.getCenter().x + toAdd.getSize
          (), toAdd.getCenter().y + toAdd.getSize());
      AABB safetyRegion = new AABB(leftTop, rightBottom);
      ListInterface<Object2D> list = quadTree.queryRegion(safetyRegion);
      if(!list.isEmpty()){
        collision = true;
      }else{
        quadTree.add(toAdd);
      }
    }
  }

  public boolean result(){
    return collision;
  }
}
