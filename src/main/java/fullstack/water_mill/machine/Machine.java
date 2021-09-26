package fullstack.water_mill.machine;

public class Machine  extends Thread{
    private boolean on; //default false

    public boolean isOn(){
        return on;
    }
    public void on(){
        on= true;
    }
    public void off(){
        on= false;
    }


}
