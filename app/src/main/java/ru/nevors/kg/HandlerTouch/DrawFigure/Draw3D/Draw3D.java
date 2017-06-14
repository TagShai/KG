package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.DrawMethodObj3D;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.ManagerZBuffer;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D.IProjection;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D.OrthogonalProjection3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.OBJ.Point;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.View.PD2;

public class Draw3D extends DrawFigure {
    Transform3D transform;
    IProjection projection;
    float fPoints[];

    public void setProjection(IProjection projection) {
        state = 4;
        this.projection = projection;
        draw(this);
    }

    public Transform3D getTransform() {
        return transform;
    }

    public void setTransform(Transform3D transform) {
        state = 2;
        this.transform = transform;
        if (transform != null) {
            vec.set(0, 0);
            transform.transform(fPoints, vec, center);
            draw(this);
            computeBorder();
        }
    }

    public Draw3D(PD2 pd) {
        super(pd, new DrawMethodObj3D(new ManagerZBuffer()));
        projection = new OrthogonalProjection3D();
        fPoints = new float[0];
        //fPoints = new float[]{0, 0, 50, 0, 5, 50, 5, 0, 50, 0, 0, 1, 0, 10, 1, 10, 0, 1};
    }

    protected Draw3D(PD2 pd, DrawMethod dm, float[] points) {
        super(pd, dm);
        projection = new OrthogonalProjection3D();
        fPoints = points;
    }

    public void addPoints(float[] points) {
        int j = fPoints.length;
        addSpaceForMas(points.length);
        for (int i = 0; i < points.length; i++) {
            fPoints[j++] = points[i];
        }
    }

    public void addTriangleOBJ(Triangle tr) {
        int width = (int) (_pd.getBitMap().getWidth() / _pd.getScale());
        int height = (int) (_pd.getBitMap().getHeight() / _pd.getScale());

        int i = fPoints.length;
        addSpaceForMas(9);
        fPoints[i++] = (tr.p1.x + 1.F) * width / 2.F - width / 2.F;
        fPoints[i++] = (tr.p1.y + 1.F) * height / 2.F - height / 2.F;
        fPoints[i++] = (tr.p1.z + 1.F) * width / 2.F - width / 2.F;

        fPoints[i++] = (tr.p2.x + 1.F) * width / 2.F - width / 2.F;
        fPoints[i++] = (tr.p2.y + 1.F) * height / 2.F - height / 2.F;
        fPoints[i++] = (tr.p2.z + 1.F) * width / 2.F - width / 2.F;

        fPoints[i++] = (tr.p3.x + 1.F) * width / 2.F - width / 2.F;
        fPoints[i++] = (tr.p3.y + 1.F) * height / 2.F - height / 2.F;
        fPoints[i++] = (tr.p3.z + 1.F) * width / 2.F - width / 2.F;
    }

    int state = 1;//1-paint, 2-work,3-move center, 0-none 4-control projection

    Point start = new Point();
    Point center = new Point();
    Point vec = new Point();

    int bufState;

    @Override
    protected void actionDown() {
        _pd.clearPointBuf();
        start.set(_x, _y);
        if (state == 1) {
            for (int i = 0; i < fPoints.length; i += 3) {
                fPoints[i] = fPoints[i] + _x;
                fPoints[i + 1] = fPoints[i + 1] + _y;
            }

            computeBorder();

            //center.x = minX + (maxX - minX) / 2;
            //center.y = minY + (maxY - minY) / 2;
            center.x = _x;
            center.y = _y;
            center.z = minZ + (maxZ - minZ) / 2;

            _pd.activateModify();
            draw(this);
            state = 2;
        }
        if (state != 0) {
            if (Math.abs(center.x - _x) < _accuracy && Math.abs(center.y - _y) < _accuracy) {
                bufState = state;
                state = 3;
                return;
            }
        }
    }


    @Override
    protected void actionMove() {
        vec.x = _x - start.x;
        vec.y = _y - start.y;
        start.x = _x;
        start.y = _y;
        if (state == 3) {
            _pd.clearPointBuf();
            center.x += vec.x;
            center.y += vec.y;
            drawManagement();
        } else {
            if (state == 2) {
                if (transform != null) {
                    transform.transform(fPoints, vec, center);
                }
                draw(this);
            } else {
                if (state == 4) {
                    projection.action(vec);
                    draw(this);
                }
            }
        }
    }

    private void transPoints() {
        if (_points.length != fPoints.length) {
            _points = new int[fPoints.length];
        }
        projection.projection(_points, fPoints, center);
    }

    @Override
    protected void draw(DrawFigure df) {
        transPoints();
        super.draw(df);
    }

    @Override
    protected void actionUp() {
        if (state == 3) {
            state = bufState;
        }
        drawManagement();
    }

    @Override
    public DrawFigure clone() {
        return new Draw3D(_pd, _dm, fPoints.clone());
    }

    @Override
    public void restore() {
        state = 2;
        _pd.activateModify();
        draw(this);
        drawManagement();
    }

    @Override
    public void update() {
        state = 0;
        if (_pd.deActivateModify()) {
            draw(this);
        }
    }

    int minX, minY, maxX, maxY, minZ, maxZ;

    protected void computeBorder() {
        float minX, minY, maxX, maxY, minZ, maxZ;
        if (fPoints.length > 2) {
            minX = maxX = fPoints[0];
            minY = maxY = fPoints[1];
            minZ = maxZ = fPoints[2];
            for (int i = 3; i < fPoints.length; i += 3) {
                if (fPoints[i] < minX) {
                    minX = fPoints[i];
                } else {
                    if (fPoints[i] > maxX) {
                        maxX = fPoints[i];
                    }
                }

                if (fPoints[i + 1] < minY) {
                    minY = fPoints[i + 1];
                } else {
                    if (fPoints[i + 1] > maxY) {
                        maxY = fPoints[i + 1];
                    }
                }

                if (fPoints[i + 2] < minZ) {
                    minZ = fPoints[i + 2];
                } else {
                    if (fPoints[i + 1] > maxZ) {
                        maxZ = fPoints[i + 2];
                    }
                }
                this.maxX = (int) maxX;
                this.minX = (int) minX;
                this.maxY = (int) maxY;
                this.minY = (int) minY;
                this.maxZ = (int) maxZ;
                this.minZ = (int) minZ;
            }
        }
    }


    protected void drawManagement() {
        computeBorder();
        _pd.addPointBuf((int) center.x, (int) center.y, getAccuracy());
        _pd.addLineBuf(minX, minY, maxX, minY);
        _pd.addLineBuf(minX, maxY, maxX, maxY);
        _pd.addLineBuf(maxX, minY, maxX, maxY);
        _pd.addLineBuf(minX, minY, minX, maxY);
    }

    @Override
    protected void addSpaceForMas(int size) {
        float[] points = new float[fPoints.length + size];
        for (int i = 0; i < fPoints.length; i++) {
            points[i] = fPoints[i];
        }
        fPoints = points;
    }

    public void setDrawMethod(DrawMethod dm) {
        _dm = dm;
        draw(this);
    }
}
