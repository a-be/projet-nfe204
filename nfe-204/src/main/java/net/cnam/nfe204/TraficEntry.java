package net.cnam.nfe204;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class TraficEntry {
    private String id;
    private double ratio;
    private LocalDateTime date;
    private int traficArcId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRatio() {
        return this.ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTraficArcId() {
        return this.traficArcId;
    }

    public void setTraficArcId(int traficArcId) {
        this.traficArcId = traficArcId;
    }
}
