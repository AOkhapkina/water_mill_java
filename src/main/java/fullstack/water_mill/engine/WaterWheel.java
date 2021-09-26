package fullstack.water_mill.engine;

import fullstack.water_mill.bean.Water;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaterWheel extends Engine {
    //добавление logger правило хорошего тона для отслеживания событий. Также позволяет менять цвета в консоли за счет изменения уровня логгирования
    private final Logger logger = LoggerFactory.getLogger(WaterWheel.class);

    private final Queue<Water> waterFlow;

    private static int MAX_POWER = 10;//колесо имеет максимальную емкость энергии, чтобы не аккумулировать энергию до бесконечности без воды ограничиваем до 10

    private final ExecutorService executor = Executors.newFixedThreadPool(2);//одновременно на две лопатки (2 threads) колеса поступает вода и крутит его

    public WaterWheel(Queue<Water> waterFlow) {//значение передаем через конструктор
        setName("Thread.WaterWheel");
        this.waterFlow = waterFlow;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Water water = waterFlow.poll();
//            this.executorService.execute(new Runnable() { //replaced with lambda this.executorService.execute(() ->{});
//                @Override
//                public void run() {
//                }
            this.executor.execute(() -> {
                if (water != null && this.getPower() < MAX_POWER) {//даже если вода будет поступать, мы ее пропустим, т.к. колесо крутится на максимальных оборотах MAX_POWER
                    this.incPower(water.getPower()); //если есть место в емкости и вода, то мы incrementим энергию, получаемую от колеса за счет энергии, получаемой от воды,
                }
            });
            try {
                Thread.sleep(200); // т.к. в реальности выполнение происходит не быстро, то ставим обработку в 200 мс (для наглядности увеличим скорость)
            } catch (InterruptedException e) {
                logger.error(e.getMessage()); // logging позволяет менять цвета в консоли за счет изменения уровня логгирования в зависимости от характера Exception
            }
        }
    }
}
