package kot.hou.fullapprxjava.cmd;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import kot.hou.fullapprxjava.model.CoinBaseResponse;
import kot.hou.fullapprxjava.observer.ConsolePrintObserver;
import kot.hou.fullapprxjava.service.CoinBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CmdLineUi implements CommandLineRunner {

    @Autowired
    private CoinBaseService coinBaseService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println();
        System.out.println("linkedin learning reactive programming java 8");
        System.out.println();

        Observable.interval(3000, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(tick -> {
                    return coinBaseService.getCrypotoPrice("BTC-USD");
                }).subscribe(new ConsolePrintObserver());


        Observable.interval(3000, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(tick -> {
                    return coinBaseService.getCrypotoPrice("ETH-USD");
                }).subscribe(new ConsolePrintObserver());



    }
}
