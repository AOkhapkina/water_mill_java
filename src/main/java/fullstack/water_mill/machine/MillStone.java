package fullstack.water_mill.machine;

import fullstack.water_mill.bean.Flour;
import fullstack.water_mill.bean.Millet;
import fullstack.water_mill.engine.Engine;
import fullstack.water_mill.engine.WaterWheel;
import org.apache.catalina.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MillStone extends Machine {
    private final Logger logger = LoggerFactory.getLogger(MillStone.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(1);//одна пара жерновов, трущихся друг о друга

    private final Engine engine;// передаем не просто значения, а объект (какой-то валб который подает импульс непоследственно в цех (машину)
    private final Queue<Millet> milletQueue;
    private final Queue<Flour> flourQueue;

    public MillStone(Engine engine, Queue<Millet> milletQueue, Queue<Flour> flourQueue) {
        this.engine = engine;
        this.milletQueue = milletQueue;
        this.flourQueue = flourQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (engine.getPower() > 0) {//если есть энергия
                engine.decPower(1);
                try {
                    this.executor.submit(() -> {//то пытается достать зерно и перемолоть в муку
                        Millet millet = milletQueue.poll();
                        if (millet != null) {
                            flourQueue.offer(new Flour());
                            logger.info("Flour: " + flourQueue.size());
                        }
                        logger.info("Power left: " + engine.getPower());
                    });

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
