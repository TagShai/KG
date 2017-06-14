package ru.nevors.kg.DrawMethod.TrimMethod.TrimMethodVA;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodPolygon.FillMethodPolygon;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class TrimMethodVA extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        Node window = null;
        Node startNode = null;
        int wMinX,wMinY,wMaxX,wMaxY;
        boolean start = true;
        //окно
        DrawMethodLineBRH brh = new DrawMethodLineBRH();
        int temp[] = new int[4];
        int k = 0;
        int sizeW = points[k++] * 2;
        int cond = sizeW + k;

        wMaxX = wMinX = points[1];
        wMaxY = wMinY =  points[2];

        while (k < cond) {
            int pre = (k + sizeW - 2) % sizeW;
            if (pre > points.length) return;//дикий баг
            temp[0] = points[pre];
            temp[1] = points[pre + 1];
            temp[2] = points[k++];
            temp[3] = points[k++];
            window = new Node(new PointF(temp[2], temp[3]), window);
            if (start) {
                startNode = window;
                start = false;
            }
            brh.draw(bmp, temp, paint);

            if(temp[0]<wMinX){
                wMinX = temp[0];
            }else{
                if(temp[0]>wMaxX){
                    wMaxX = temp[0];
                }
            }

            if(temp[1]<wMinY){
                wMinY = temp[1];
            }else{
                if(temp[1]>wMaxY){
                    wMaxY = temp[1];
                }
            }

        }
        startNode.setNext(window);

        //отсекание
        while (k < points.length) {
            float pMinX,pMinY,pMaxX,pMaxY;

            Node tempWindow = window.reverseClone();
            Node polygon = null;
            int num = points[k] * 2;
            k++;
            if (num < 6 || num > points.length) continue;//дикий баг
            cond = num + k;

            polygon = new Node(new PointF(points[cond - 2], points[cond - 1]), null);

            pMaxX = pMinX = polygon.getPoint().x;
            pMaxY = pMinY = polygon.getPoint().y;

            startNode = polygon;
            for (int i = cond - 3; i >= k; i -= 2) {
                polygon = new Node(new PointF(points[i - 1], points[i]), polygon);

                if(points[i - 1]<pMinX){
                    pMinX = points[i - 1];
                }else{
                    if(points[i - 1]>pMaxX){
                        pMaxX = points[i - 1];
                    }
                }

                if(points[i]<pMinY){
                    pMinY = points[i];
                }else{
                    if(points[i]>pMaxY){
                        pMaxY = points[i];
                    }
                }
            }
            k+=num;

            startNode.setNext(polygon);

            cross(tempWindow, polygon);


            /*Node t = polygon;
            StringBuilder sb = new StringBuilder();
            while (t!=null){
                sb.append("(").append(t.getPoint().x).append(",").append(t.getPoint().y).append(")");
                t = t.getNext();
            }
            Log.d("pva",sb.toString());*/
            FillMethodPolygon p = new FillMethodPolygon();
            Node g = polygon;
            int []t = null;
            do {
                if (g.getType()==1 && !g.isCheck()) {
                    t = nextPolygon(g);
                    p.draw(bmp,t,paint);
                }
                g = g.getNext();
            } while (g != polygon);

            if(t == null){
                if(pMaxX >= wMaxX && pMinX <= wMinX && pMaxY >= wMaxY && pMinY <= wMinY){
                    t = new int[sizeW];
                    int index = 1;
                    for(int i = 0;i<t.length;i++){
                        t[i] = points[index++];
                    }
                    p.draw(bmp,t,paint);
                }else{
                    if(pMaxX <= wMaxX && pMinX >= wMinX && pMaxY <= wMaxY && pMinY >= wMinY){
                        t = new int[num];
                        g = polygon;
                        for(int i=0;i<num;){
                            t[i++] = (int)g.getPoint().x;
                            t[i++] = (int)g.getPoint().y;
                            g = g.getNext();
                        }
                        p.draw(bmp,t,paint);
                    }
                }

            }
        }





        /*Point t = LinesCross(new Point(0,0),new Point(2,2),new Point(2,0),new Point(0,2));
        Log.d("123",t.toString());*/
    }

    private int[] nextPolygon(Node start) {
        ArrayList<PointF> points = new ArrayList<>();

        points.add(start.getPoint());
        start.setCheck(true);

        Node tempS = start.getNext();
        while (tempS != start && tempS.getSame() != start) {
            points.add(tempS.getPoint());
            if (tempS.getSame() == null) {
                tempS = tempS.getNext();
            } else {
                tempS.setCheck(true);
                tempS = tempS.getSame().getNext();
            }
        }

        int result[] = new int[points.size() * 2];

        int k = 0;
        for (PointF point : points) {
            result[k++] = (int)point.x;
            result[k++] = (int)point.y;
        }
        return result;
    }

    private void cross(Node window, Node polygon) {
        Node windowP1 = window;
        Node windowP2 = windowP1.getNext();
        do {
            Node polygonP1 = polygon;
            Node polygonP2 = polygon.getNext();
            do {
                Node pc = LinesCross(windowP1.getPoint(), windowP2.getPoint(), polygonP1.getPoint(), polygonP2.getPoint());
                if (pc != null) {
                    pc.setNext(polygonP2);
                    polygonP1.setNext(pc);

                    Node temp = windowP1;
                    float h = hash(windowP1.getPoint(), pc.getPoint());
                    while (temp.getNext() != windowP2) {
                        if (h < hash(windowP1.getPoint(), temp.getNext().getPoint())) {
                            break;
                        }
                        temp = temp.getNext();
                    }
                    Node node = new Node(pc.getPoint(), temp.getNext());
                    temp.setNext(node);

                    node.setSame(pc);
                    pc.setSame(node);
                }
                polygonP1 = polygonP2;
                polygonP2 = polygonP2.getNext();
            }while (polygonP1 != polygon);
            windowP1 = windowP2;
            windowP2 = windowP2.getNext();
        } while (windowP1 != window);
    }

    private float hash(PointF start, PointF end) {
        float var1 = end.x - start.x;
        var1*=var1;
        float var2 = end.y - start.y;
        var2*=var2;
        return (float)Math.sqrt(var1+var2);
    }

    private float vektorMulti(float ax, float ay, float bx, float by) {
        return ax * by - bx * ay;
    }

    private Node LinesCross(PointF a1, PointF a2, PointF b1, PointF b2) {
        Node result = null;
        float v1 = vektorMulti(b2.x - b1.x, b2.y - b1.y, a1.x - b1.x, a1.y - b1.y);
        float v2 = vektorMulti(b2.x - b1.x, b2.y - b1.y, a2.x - b1.x, a2.y - b1.y);

        if (v1 * v2 < 0) {
            float v3 = vektorMulti(a2.x - a1.x, a2.y - a1.y, b1.x - a1.x, b1.y - a1.y);
            float v4 = vektorMulti(a2.x - a1.x, a2.y - a1.y, b2.x - a1.x, b2.y - a1.y);
            if (v3 * v4 < 0) {
                float t = Math.abs(v1 / (v2 - v1));
                float x = (a1.x + (a2.x - a1.x) * t);
                float y = (a1.y + (a2.y - a1.y) * t);

                result = new Node(new PointF(x, y),null);
                if(v1>0){
                    result.setType(1);
                }else{
                    result.setType(2);
                }
            }
        }
        return result;
    }
}
