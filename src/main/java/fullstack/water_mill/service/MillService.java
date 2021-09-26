package fullstack.water_mill.service;

import fullstack.water_mill.Mill;
import fullstack.water_mill.bean.Flour;
import fullstack.water_mill.bean.MillState;
import fullstack.water_mill.bean.Millet;
import fullstack.water_mill.bean.Water;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MillService {//создаем потоки, называемые внешней средой, ограничиваем очередь значением 10

    private final Logger logger = LoggerFactory.getLogger(MillService.class);

    private final static BlockingQueue<Water> waterFlow = new ArrayBlockingQueue<>(10);
    private final static BlockingQueue<Millet> milletFlow = new ArrayBlockingQueue<>(10);
    private final static BlockingQueue<Flour> flourFlow = new ArrayBlockingQueue<>(10);

    private Mill mill; // поле для хранения instance
    // ExecutorService исполняет асинхронный код в одном или нескольких потоках/ для добавления воды
    private final ExecutorService waterPipeExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService milletPipeExecutor = Executors.newSingleThreadExecutor();

    public MillService() {
        mill = new Mill(waterFlow, milletFlow, flourFlow);
        mill.start();
    }

    public MillState getState() {
        return new MillState(mill, waterFlow, milletFlow, flourFlow);
    }

    public void addWater(int capacity) {
        //        waterPipeExecutor.execute(()->{
        this.waterPipeExecutor.execute(new Runnable() { //replaced with lambda this.executorService.execute(() ->{});
            @Override
            public void run() {
                try {
                    int counter = capacity;
                    while (counter-- > 0) {
                        waterFlow.add(new Water());
                    }
                } catch (IllegalStateException e) {
                    logger.info("Water tank is full" + e.getMessage());
                }
            }
        });
    }

    public void addMillet(int capacity) {
        milletPipeExecutor.execute(() -> {
            try {
                int counter = capacity;
                while (counter-- > 0) {
                    milletFlow.add(new Millet());
                }
            } catch (IllegalStateException e) {
                logger.info("Millet tank is full" + e.getMessage());
            }
        });
    }

    public void dropFlour() {
        flourFlow.clear();
    }
}
