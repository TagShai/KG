package ru.nevors.kg.DrawMethod.FillMethod.FillMethodPolygon;

public class Edge {
    float x, dx;
    int y, dy;

    public Edge(int x1, int y1, int x2, int y2) {
        y = y1;
        x = x1;
        dy = y1 - y2;
        dx = (x2 - x1) / (float) dy;

    }

    public void nextY() {
        dy--;
        x += dx;
    }

    public boolean isNextY() {
        return dy != 0;
    }

    public int getX() {
        return (int) x;
    }

    public int getStartY() {
        return y;
    }
}
