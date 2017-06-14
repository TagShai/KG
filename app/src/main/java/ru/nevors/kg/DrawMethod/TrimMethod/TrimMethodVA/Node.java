package ru.nevors.kg.DrawMethod.TrimMethod.TrimMethodVA;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Iterator;

public class Node {
    private PointF point;
    private Node same;
    private Node next;
    private int type;//1-in 2-out

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check;

    public Node(PointF point, Node next){
        this.point = point;
        this.next = next;
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }

    public Node getNext() {
        return next;
    }

    public Node getSame() {
        return same;
    }

    public void setSame(Node same) {
        this.same = same;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node reverseClone(){
        Node temp = next;

        Node result = new Node(new PointF(point.x,point.y),null);
        Node start = result;
        while(temp!=this){
            result =  new Node(new PointF(temp.getPoint().x,temp.getPoint().y),result);
            temp = temp.getNext();
        }
        start.setNext(result);
        return result;
    }
}
