package net.cnam.nfe204;

import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraficDispatcher {
    @Autowired
    private TraficRepository traficRepository;
    @Autowired
    private TraficDataRetriever traficDataRetriever;

    @PostConstruct
    public void save() {
        for (; ; ) {
            List<TraficEntry> traficEntries = this.traficDataRetriever.get();
            this.traficRepository.saveAll(traficEntries);
        }
    }
}
