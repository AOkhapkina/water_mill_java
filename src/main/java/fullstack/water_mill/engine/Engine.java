package fullstack.water_mill.engine;

import lombok.Data;

@Data
public class Engine extends Thread {

    protected int power = 0;

    public void incPower(int val) {//increase
        power += val;
    }

    public void decPower(int val) {//decrease
        power -= val;
        if (power < 0) {
            power = 0;//чтобы не уходил в минус
        }
    }
}
