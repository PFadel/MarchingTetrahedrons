/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import util.math.Vector3f;

/**
 *
 * @author fadel
 */
public class Surface {

    public float a, b, c, d, e, f;
    public final float G = 0.5f;

    public Surface(float a, float b, float c, float d, float e, float f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    public float calculatePoints(float x, float y, float z) {
        return (float) (((Math.pow((double) x, (double) b)) * a)
                + (Math.pow((double) y, (double) d)) * c
                + (Math.pow((double) z, (double) f)) * e);
    }

    public Vector3f normalize(Vector3f a) {
        float scale = 0;
        scale += a.x * a.x;
        scale += a.y * a.y;
        scale += a.z * a.z;

        scale = (float) (1 / Math.sqrt(scale));
        a.x *= scale;
        a.y *= scale;
        a.z *= scale;
        return a;
    }

    public Vector3f gradientAt(float x, float y, float z) {
        float epsilon = 0.0001f;

        float dx = calculatePoints(x + epsilon, y, z) - calculatePoints(x - epsilon, y, z);
        float dy = calculatePoints(x, y + epsilon, z) - calculatePoints(x, y - epsilon, z);
        float dz = calculatePoints(x, y, z + epsilon) - calculatePoints(x, y, z - epsilon);

        Vector3f result = new Vector3f(dx, dy, dz);
        return normalize(result);
    }
}
