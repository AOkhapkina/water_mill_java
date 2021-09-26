package fullstack.water_mill.bean;

import fullstack.water_mill.Mill;

import java.util.Queue;

public class MillState {// возвращает состояние мельницы в любой момент
    private final int water;
    private final int millet;
    private final int flour;
    private final int power;

    private final boolean engine;
    private final boolean machine;


    public MillState(Mill mill, Queue<Water> waterFlow, Queue<Millet> milletFlow, Queue<Flour> flourFlow) {
        this.water = waterFlow.size();
        this.millet = milletFlow.size();
        this.flour = flourFlow.size();

        power = mill.getPower();
        machine = mill.isMachineOn();
        engine = mill.isEngineOn();
    }
}
