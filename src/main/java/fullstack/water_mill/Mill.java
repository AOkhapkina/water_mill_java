package fullstack.water_mill;

import fullstack.water_mill.bean.Flour;
import fullstack.water_mill.bean.Millet;
import fullstack.water_mill.bean.Water;
import fullstack.water_mill.engine.Engine;
import fullstack.water_mill.engine.WaterWheel;
import fullstack.water_mill.machine.Machine;
import fullstack.water_mill.machine.MillStone;

import java.util.Queue;


public class Mill extends Thread {
    private final Engine engine;
    private final Machine machine;

    //создаем конструктор, но убираем из параметров значения по умолчанию и добавляем три внешних параметра - очереди: входящие (вода, зерно) и исходящую (мука)
    public Mill(Queue<Water> waterFlow, Queue<Millet> milletFlow, Queue<Flour> flourFlow) {
        this.engine = new WaterWheel(waterFlow);
        this.engine.start();

        this.machine = new MillStone(engine, milletFlow, flourFlow);
        this.machine.start();
    }

    public boolean isMachineOn() {
        return this.machine.isOn();
    }

    public boolean isEngineOn() {
        return this.engine.getPower() > 0;
    }

    public int getPower() {
        return this.engine.getPower();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (engine.getPower() > 0) {
                machine.on();

            }
        }
    }
}
