package com.company;

public class Car implements Comparable<Car> {
    private int maxSpeed;
    public Car()
    {
        maxSpeed = 60;
    }
    public Car(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public void setMaxSpeed(int maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }
    public int getMaxSpeed(){
        return maxSpeed;
    }
    @Override
    public int compareTo(Car o) {
        if(this.maxSpeed > o.maxSpeed)
            return 1;
        if(this.maxSpeed < o.maxSpeed)
            return -1;
        return 0;
    }

}