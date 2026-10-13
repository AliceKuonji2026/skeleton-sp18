public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int n = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[n];
        for (int i = 0; i < n; i++) {
            Planet p = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
            planets[i] = p;
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(.0, .0, "images/starfield.jpg");
        for (Planet p : planets) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.pause(2000);

        double t = 0;
        int n = planets.length;
        double[] xForces = new double[n];
        double[] yForces = new double[n];
        while (t < T) {
            int i = 0;
            for (Planet p : planets) {
                xForces[i] = p.calcNetForceExertedByX(planets);
                yForces[i] = p.calcNetForceExertedByY(planets);
                i++;
            }
            int j = 0;
            for (Planet p : planets) {
                p.update(dt, xForces[j], yForces[j]);
                j++;
            }
            StdDraw.picture(.0, .0, "images/starfield.jpg");
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }
}
