package net.cnam.nfe204;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TraficDataRetriever {
    private static final String URL = "https://opendata.paris.fr/api/records/1.0/search/?dataset=comptages-routiers-permanents&rows={size}&start={index}";
    @Value(value = "${page-size:100}")
    private int size;
    @Value(value = "${page-index:0}")
    private int index;
    @Autowired
    private RestTemplate restTemplate;

    List<TraficEntry> get() {
        System.out.println("fetching : " + this.size + "record at : " + this.index);
        Map<String, List<Map>> result = this.restTemplate.getForEntity("https://opendata.paris.fr/api/records/1.0/search/?dataset=comptages-routiers-permanents&rows={size}&start={index}", Map.class, new Object[]{this.size, this.index}).getBody();
        this.index += this.size;
        return result.get("records").stream().flatMap(this::getTraficEntry).collect(Collectors.toList());
    }

    private Stream<TraficEntry> getTraficEntry(Map map) {
        Map fields = (Map) map.get("fields");
        if (fields.get("taux") != null) {
            TraficEntry traficEntry = new TraficEntry();
            traficEntry.setId((String) map.get("recordid"));
            traficEntry.setRatio(((Double) fields.get("taux")).doubleValue());
            traficEntry.setDate(LocalDateTime.parse((String) fields.get("horodate"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            traficEntry.setTraficArcId(((Integer) fields.get("id_arc_trafic")).intValue());
            return Stream.of(traficEntry);
        }
        return Stream.empty();
    }
}