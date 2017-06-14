package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.nevors.kg.View.PD2;

public class Transform2DRotate extends Transform2DCenterPoint {
    Point v1, v2;

    public Transform2DRotate(PD2 pd) {
        super(pd);
        v1 = new Point();
        v2 = new Point();
    }
    List<Node> list = new ArrayList<>();
    Node last;
    @Override
    protected int[] actionPoint(int[] points, Point start, Point cur, Point center) {
        if(last==null){
            last = new Node();
            list.add(last);
        }
        if(last.center.x!=center.x || last.center.y!=center.y){
            last = new Node();
            list.add(last);
        }

        last.center.set(center.x,center.y);

        v1.x = start.x - center.x;
        v1.y = start.y - center.y;
        v2.x = cur.x - center.x;
        v2.y = cur.y - center.y;

        float angle = 0.02F;

        int p = v1.x * v2.y - v1.y * v2.x;
        if (p < 0) {
            angle *= -1;
        }

        last.angle+=angle;

        float cos, sin;
        int[] result = points.clone();
        for (Node node : list) {
            cos = (float) Math.cos(node.angle);
            sin = (float) Math.sin(node.angle);

            for (int i = 0; i < result.length; i += 2) {
                result[i] -= node.center.x;
                result[i + 1] -= node.center.y;

                int x = result[i], y = result[i + 1];
                result[i] = Math.round(x * cos - y * sin);
                result[i + 1] = Math.round(x * sin + y * cos);

                result[i] += node.center.x;
                result[i + 1] += node.center.y;
            }
        }
        return result;
    }
    class Node{
        Point center;
        float angle;
        public Node(){
            center = new Point();
            angle = 0;
        }
    }
}
