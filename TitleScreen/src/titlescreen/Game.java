/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlescreen;

/**
 *
 * @author micha
 */
//something server related
class Game {
    Game(){
        this.score=0;
    }

    public int getScore(){
        return this.score;
    }
    private int score;//total score storage
    private double startTime=0;
    private double endTime=0;
    private double term1=0;//stores first term of operation
    private double term2=0;//stores second term of operation
    private int operation;//stores value representing what operation will be made
    private double[] valList = {0,0,0,0};//stores original values
    private boolean buttonTurn=false;//false means term 1 will be set, true means term 2 will be set
    private int moveCount = 0;
    private int solveCount=0;
    private double totalTime;
    public int getSolve(){
        return this.solveCount;
    }
    public void setSolve(int val){
        this.solveCount=val;
    }

    public void setVal(double val,int loc){
        this.valList[loc]=val;
    }
    public double[] getVals(){
        return this.valList;
    }

    public int getMove(){
        return this.moveCount;
    }
    public void nextMove(){
        this.moveCount++;
    }
    public void resetMove(){
        this.moveCount=0;
    }

    public void setTurn(boolean val){
        this.buttonTurn = val;
    }
    public boolean getTurn(){
        return this.buttonTurn;
    }
    public void set00(double val){
        this.valList[0]=val;
    }
    public double get00(){
        return this.valList[0];
    }
    public void set01(double val){
        this.valList[1]=val;
    }
    public double get01(){
        return this.valList[1];
    }
    public void set10(double val){
        this.valList[2]=val;
    }
    public double get10(){
        return this.valList[2];
    }
    public void set11(double val){
        this.valList[3]=val;
    }
    public double get11(){
        return this.valList[3];
    }


    public void setOp(int val){
        this.operation=val;
    }
    public int getOp(){
        return this.operation;
    }
    public void setTerm1(double val){
        this.term1=val;
    }
    public void setTerm2(double val){
         this.term2=val;
    }
    public double getTerm1(){
        return this.term1;
    }
    public double getTerm2(){
        return this.term2;
    }
    public void startGame(){
        this.totalTime=System.nanoTime();
    }
    public void startTime(){
        this.startTime=System.nanoTime();
    }
    public void stopTime(){
        this.endTime=System.nanoTime();
    }
    public double getTotalTime(){
        return this.totalTime;
    }
    public void raise(){
        double time = (this.endTime-this.startTime)/1000000000;
        this.totalTime = this.totalTime+time;
        int amount=0;
        if (time<=5.0){
            amount = 1000;//max score of 1000
        }
        else{
            amount = 950 - (int)time*10;
        }
        if (amount<100){
            amount=100; //minimum score of 100
        }
        this.score = this.score + amount;
    }

}
