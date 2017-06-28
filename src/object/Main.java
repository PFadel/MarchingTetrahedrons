package object;

/**
 *
 * @author marcoslage
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.projection.Projection;

public class Main {

    // Creates a new cube
    //private final CubeGL graphicObject = new CubeGL();
    //Creates a new Piramide
    private final PiramideGL graphicObject = new PiramideGL();

    // Animation:
    private float currentAngleX = 0.0f;
    private float currentAngleY = 0.0f;

    // Projection Matrix
    private final Projection proj = new Projection(45, 1.3333f, 0.0f, 100f);

    // View Matrix
    private final Vector3f eye = new Vector3f(0.0f, 0.0f, 2.0f);
    private final Vector3f at = new Vector3f(0.0f, 0.0f, 0.0f);
    private final Vector3f up = new Vector3f(0.0f, 1.0f, 2.0f);

    // Camera
    private final Camera cam = new Camera(eye, at, up);

    // Light
    private final Vector3f lightPos = new Vector3f(0.0f, 2.0f, -2.0f);
    private final Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f speclarColor = new Vector3f(1.0f, 1.0f, 1.0f);

    private final float kA = 0.4f;
    private final float kD = 0.5f;
    private final float kS = 0.1f;
    private final float sN = 60.0f;

    // Model Matrix:
    private final Matrix4f scaleMatrix = new Matrix4f();

    // Final Matrix
    private Matrix4f modelMatrix = new Matrix4f();
    private final Matrix4f viewMatrix = new Matrix4f();
    private final Matrix4f projMatrix = new Matrix4f();

    private void drawTetrahedron(Surface surface, Vector3f[] v, float isolevel) {
        char index = 0;
        for (int i = 0; i < 4; ++i) {
            if (v[i].value < isolevel) {
                index |= (1 << i);
            }
        }

        switch (index) {

            // we don't do anything if everyone is inside or outside
            case 0x00:
            case 0x0F:
                break;

            // only vert 0 is inside
            case 0x01:
                drawVert(surface, v[0], v[1], isolevel);
                drawVert(surface, v[0], v[3], isolevel);
                drawVert(surface, v[0], v[2], isolevel);
                break;

            // only vert 1 is inside
            case 0x02:
                drawVert(surface, v[1], v[0], isolevel);
                drawVert(surface, v[1], v[2], isolevel);
                drawVert(surface, v[1], v[3], isolevel);
                break;

            // only vert 2 is inside
            case 0x04:
                drawVert(surface, v[2], v[0], isolevel);
                drawVert(surface, v[2], v[3], isolevel);
                drawVert(surface, v[2], v[1], isolevel);
                break;

            // only vert 3 is inside
            case 0x08:
                drawVert(surface, v[3], v[1], isolevel);
                drawVert(surface, v[3], v[2], isolevel);
                drawVert(surface, v[3], v[0], isolevel);
                break;

            // verts 0, 1 are inside
            case 0x03:
                drawVert(surface, v[3], v[0], isolevel);
                drawVert(surface, v[2], v[0], isolevel);
                drawVert(surface, v[1], v[3], isolevel);

                drawVert(surface, v[2], v[0], isolevel);
                drawVert(surface, v[2], v[1], isolevel);
                drawVert(surface, v[1], v[3], isolevel);
                break;

            // verts 0, 2 are inside
            case 0x05:
                drawVert(surface, v[3], v[0], isolevel);
                drawVert(surface, v[1], v[2], isolevel);
                drawVert(surface, v[1], v[0], isolevel);

                drawVert(surface, v[1], v[2], isolevel);
                drawVert(surface, v[3], v[0], isolevel);
                drawVert(surface, v[2], v[3], isolevel);
                break;

            // verts 0, 3 are inside
            case 0x09:
                drawVert(surface, v[0], v[1], isolevel);
                drawVert(surface, v[1], v[3], isolevel);
                drawVert(surface, v[0], v[2], isolevel);

                drawVert(surface, v[1], v[3], isolevel);
                drawVert(surface, v[3], v[2], isolevel);
                drawVert(surface, v[0], v[2], isolevel);
                break;

            // verts 1, 2 are inside
            case 0x06:
                drawVert(surface, v[0], v[1], isolevel);
                drawVert(surface, v[0], v[2], isolevel);
                drawVert(surface, v[1], v[3], isolevel);

                drawVert(surface, v[1], v[3], isolevel);
                drawVert(surface, v[0], v[2], isolevel);
                drawVert(surface, v[3], v[2], isolevel);
                break;

            // verts 2, 3 are inside
            case 0x0C:
                drawVert(surface, v[1], v[3], isolevel);
                drawVert(surface, v[2], v[0], isolevel);
                drawVert(surface, v[3], v[0], isolevel);

                drawVert(surface, v[2], v[0], isolevel);
                drawVert(surface, v[1], v[3], isolevel);
                drawVert(surface, v[2], v[1], isolevel);
                break;

            // verts 1, 3 are inside
            case 0x0A:
                drawVert(surface, v[3], v[0], isolevel);
                drawVert(surface, v[1], v[0], isolevel);
                drawVert(surface, v[1], v[2], isolevel);

                drawVert(surface, v[1], v[2], isolevel);
                drawVert(surface, v[2], v[3], isolevel);
                drawVert(surface, v[3], v[0], isolevel);
                break;

            // verts 0, 1, 2 are inside
            case 0x07:
                drawVert(surface, v[3], v[0], isolevel);
                drawVert(surface, v[3], v[2], isolevel);
                drawVert(surface, v[3], v[1], isolevel);
                break;

            // verts 0, 1, 3 are inside
            case 0x0B:
                drawVert(surface, v[2], v[1], isolevel);
                drawVert(surface, v[2], v[3], isolevel);
                drawVert(surface, v[2], v[0], isolevel);
                break;

            // verts 0, 2, 3 are inside
            case 0x0D:
                drawVert(surface, v[1], v[0], isolevel);
                drawVert(surface, v[1], v[3], isolevel);
                drawVert(surface, v[1], v[2], isolevel);
                break;

            // verts 1, 2, 3 are inside
            case 0x0E:
                drawVert(surface, v[0], v[1], isolevel);
                drawVert(surface, v[0], v[2], isolevel);
                drawVert(surface, v[0], v[3], isolevel);
                break;

            // what is this I don't even
            default:
                assert (false);
        }

    }

    private void drawVert(Surface surface, Vector3f p1, Vector3f p2, float isolevel) {
        float v1 = p1.value;
        float v2 = p2.value;

        float x, y, z;

        if (v2 == v1) {
            x = (p1.x + p2.x) / 2.0f;
            y = (p1.y + p2.y) / 2.0f;
            z = (p1.z + p2.z) / 2.0f;
        } else {

            float interp = (isolevel - v1) / (v2 - v1);
            float oneMinusInterp = 1 - interp;

            x = p1.x * oneMinusInterp + p2.x * interp;
            y = p1.y * oneMinusInterp + p2.y * interp;
            z = p1.z * oneMinusInterp + p2.z * interp;
        }
    }

    public enum RotationType {
        X, Y, Z
    }

    public enum ProjectionType {
        O, P
    }

    private RotationType currentRotation = RotationType.X;
    private ProjectionType currentProjection = ProjectionType.O;

    public void createCubes(Surface surface, float isolevel, long resolution, float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {

        float xrange = xMax - xMin;
        float yrange = yMax - yMin;
        float zrange = zMax - zMin;

        long pointRes = resolution + 1;

        Vector3f grid = new Vector3f(pointRes, pointRes, pointRes);

        for (long i = 0; i <= resolution; ++i) {
            float x = (float) i / resolution * xrange + xMin;
            for (long j = 0; j <= resolution; ++j) {
                float y = (float) j / resolution * yrange + yMin;
                for (long k = 0; k <= resolution; ++k) {
                    float z = (float) k / resolution * zrange + zMin;
                    float value = surface.calculatePoints(x, y, z);
                    grid.setTo(i, j, k, value);
                }
            }
        }

        for (long i = 0; i < resolution; ++i) {
            float x1 = (float) i / resolution * xrange + xMin;
            float x2 = (float) (i + 1) / resolution * xrange + xMin;
            for (long j = 0; j < resolution; ++j) {
                float y1 = (float) j / resolution * yrange + yMin;
                float y2 = (float) (j + 1) / resolution * yrange + yMin;
                for (long k = 0; k < resolution; ++k) {
                    float z1 = (float) k / resolution * zrange + zMin;
                    float z2 = (float) (k + 1) / resolution * zrange + zMin;

                    Vector3f[] v = new Vector3f[8];
                    v[0] = new Vector3f(x1, y1, z1);
                    v[1] = new Vector3f(x2, y1, z1);
                    v[2] = new Vector3f(x2, y2, z1);
                    v[3] = new Vector3f(x1, y2, z1);
                    v[4] = new Vector3f(x1, y1, z2);
                    v[5] = new Vector3f(x2, y1, z2);
                    v[6] = new Vector3f(x2, y2, z2);
                    v[7] = new Vector3f(x1, y2, z2);

                    Vector3f[][] tetrahedra = new Vector3f[6][4];
                    tetrahedra[0][1] = v[0];
                    tetrahedra[0][2] = v[7];
                    tetrahedra[0][3] = v[3];
                    tetrahedra[0][4] = v[2];
                    tetrahedra[1][1] = v[0];
                    tetrahedra[1][2] = v[7];
                    tetrahedra[1][3] = v[2];
                    tetrahedra[1][4] = v[6];
                    tetrahedra[2][1] = v[0];
                    tetrahedra[2][2] = v[4];
                    tetrahedra[2][3] = v[7];
                    tetrahedra[2][4] = v[6];
                    tetrahedra[3][1] = v[0];
                    tetrahedra[3][2] = v[1];
                    tetrahedra[3][3] = v[6];
                    tetrahedra[3][4] = v[2];
                    tetrahedra[4][1] = v[0];
                    tetrahedra[4][2] = v[4];
                    tetrahedra[4][3] = v[6];
                    tetrahedra[4][4] = v[1];
                    tetrahedra[5][1] = v[5];
                    tetrahedra[5][2] = v[1];
                    tetrahedra[5][3] = v[6];
                    tetrahedra[5][4] = v[4];

                    for (int t = 0; t < 6; ++t) {
                        drawTetrahedron(surface, tetrahedra[t], isolevel);
                    }
                }
            }
        }
    }

    /**
     * General initialization stuff for OpenGL
     *
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {

        // width and height of window and view port
        int width = 640;
        int height = 480;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);

        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));

        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        graphicObject.fillVAOs();
        graphicObject.loadShaders();

        // Model Matrix setup
        scaleMatrix.m11 = 0.5f;
        scaleMatrix.m22 = 0.5f;
        scaleMatrix.m33 = 0.5f;

        // light setup
        graphicObject.setVector("lightPos", lightPos);
        graphicObject.setVector("ambientColor", ambientColor);
        graphicObject.setVector("diffuseColor", diffuseColor);
        graphicObject.setVector("speclarColor", speclarColor);

        graphicObject.setFloat("kA", kA);
        graphicObject.setFloat("kD", kD);
        graphicObject.setFloat("kS", kS);
        graphicObject.setFloat("sN", sN);

        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);

            setCurrentRotationAndProjectionFromInput();

            // Projection and View Matrix Setup
            projMatrix.setTo(getProjectionMatrix());
            viewMatrix.setTo(cam.viewMatrix());

            modelMatrix = getModelMatrix();

            modelMatrix.multiply(scaleMatrix);
            graphicObject.setMatrix("modelmatrix", modelMatrix);
            graphicObject.setMatrix("viewmatrix", viewMatrix);
            graphicObject.setMatrix("projection", projMatrix);
            graphicObject.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    private void setCurrentRotationAndProjectionFromInput() {
        float delta = 0.0f;
        if (Keyboard.isKeyDown(Keyboard.KEY_R) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            delta = 0.01f;
            currentRotation = RotationType.Y;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_L) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            delta = -0.01f;
            currentRotation = RotationType.Y;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_C) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            delta = 0.01f;
            currentRotation = RotationType.X;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_B) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            delta = -0.01f;
            currentRotation = RotationType.X;
        }
        if (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                int input = Keyboard.getEventKey();
                switch (input) {
                    case Keyboard.KEY_O:
                        currentProjection = ProjectionType.O;
                        break;
                    case Keyboard.KEY_P:
                        currentProjection = ProjectionType.P;
                        break;
                }
            }
        }

        switch (currentRotation) {
            case X:
                currentAngleX += delta;
                break;
            case Y:
                currentAngleY += delta;
                break;
        }
    }

    private Matrix4f getRotationMatrixX(float angle) {

        float c = FastMath.cos(angle);
        float s = FastMath.sin(angle);

        Matrix4f rotationMatrixX = new Matrix4f();

        //rotaçao em torno de x
        rotationMatrixX.m22 = c;
        rotationMatrixX.m32 = -s;
        rotationMatrixX.m23 = s;
        rotationMatrixX.m33 = c;

        return rotationMatrixX;
    }

    private Matrix4f getRotationMatrixY(float angle) {

        float c = FastMath.cos(angle);
        float s = FastMath.sin(angle);

        Matrix4f rotationMatrixY = new Matrix4f();

        //rotaçao em torno de y
        rotationMatrixY.m11 = c;
        rotationMatrixY.m31 = s;
        rotationMatrixY.m13 = -s;
        rotationMatrixY.m33 = c;

        return rotationMatrixY;
    }

    private Matrix4f getModelMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.setToIdentity();
        matrix.multiply(getRotationMatrixY(currentAngleY));
        matrix.multiply(getRotationMatrixX(currentAngleX));
        return matrix;
    }

    private Matrix4f getProjectionMatrix() {

        switch (currentProjection) {
            case O:
                return proj.orthogonal();
            case P:
                return proj.perspective();
        }
        return proj.orthogonal();
    }

    /**
     * main method to run the example
     *
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        Main example = new Main();
        example.initGl();
        example.run();
    }
}
