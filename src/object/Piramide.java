/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import util.math.Vector4f;

/**
 *
 * @author fadel
 */
public class Piramide {

    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;

    protected int nverts = 5;
    protected int ntriangles = 6;

    public Piramide() {
        positions = new ArrayList<>(ntriangles * 3);
        colors = new ArrayList<>(ntriangles * 3);

        Vector4f p1 = new Vector4f(0.0f, 0.6f, 0.0f, 1.0f);
        Vector4f p2 = new Vector4f(-0.3f, 0.0f, 0.3f, 1.0f);
        Vector4f p3 = new Vector4f(0.3f, 0.0f, 0.3f, 1.0f);
        Vector4f p4 = new Vector4f(-0.3f, 0.0f, -0.3f, 1.0f);
        Vector4f p5 = new Vector4f(0.3f, 0.0f, -0.3f, 1.0f);

        positions.add(p1);
        positions.add(p4);
        positions.add(p2);
        positions.add(p1);
        positions.add(p2);
        positions.add(p3);
        positions.add(p1);
        positions.add(p3);
        positions.add(p5);
        positions.add(p1);
        positions.add(p5);
        positions.add(p4);
        positions.add(p2);
        positions.add(p4);
        positions.add(p3);
        positions.add(p3);
        positions.add(p4);
        positions.add(p5);

        // Fill the colors
        Vector4f white = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        Vector4f red = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        Vector4f yellow = new Vector4f(1.0f, 1.0f, 0.0f, 1.0f);
        Vector4f green = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
        Vector4f blue = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);

        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);
        colors.add(red);

    }
}
