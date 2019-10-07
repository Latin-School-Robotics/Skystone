package org.firstinspires.ftc.teamcode;


import java.lang.Math;



public class Vec
{
    
    public float x;
    public float y;

    public Vec(float ax, float ay){
        x = ax;
        y = ay;
    }
    
    public Vec sum(Vec other){
        float sx = x + other.x;
        float sy = y + other.y;

        Vec s = new Vec(sx,sy);
        return s;
    }

    public float distance(Vec other){

        float dx = x - other.x;
        float dy = y - other.y;

        return (float)Math.sqrt(dx * dx + dy * dy);
    }

    
    
}



