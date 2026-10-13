public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) + (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        return G * mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        return calcForceExertedBy(p) * ((p.xxPos - xxPos) / calcDistance(p));
    }

    public double calcForceExertedByY(Planet p) {
        return calcForceExertedBy(p) * ((p.yyPos - yyPos) / calcDistance(p));
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double xxNF = 0;
        for (Planet planet : planets) {
            if (!this.equals(planet)) {
                xxNF += calcForceExertedByX(planet);
            }
        }
        return xxNF;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double yyNF = 0;
        for (Planet planet : planets) {
            if (!this.equals(planet)) {
                yyNF += calcForceExertedByY(planet);
            }
        }
        return yyNF;
    }

    public void update(double dt ,double xxF,double yyF){
        xxVel+=dt*(xxF/mass);
        yyVel+=dt*(yyF/mass);
        xxPos+=dt*xxVel;
        yyPos+=dt*yyVel;
    }

    public void draw(){
        StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }

}
