package com.kodilla.airporthater.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Entity
@Table(name = "AIRPORTS_SCORES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportScoreAvg {

    @Id
    @Column(name = "IATA_CODE", unique = true, nullable = false)
    private String iataCode;

    @Column(name = "AIRPORT_SCORE")
    @Builder.Default
    private double scoreAvg = 0.0;

    @Setter
    @OneToOne
    @JoinColumn(name = "IATA_CODE", referencedColumnName = "IATA_CODE", insertable = false, updatable = false)
    @ToString.Exclude
    private Airport airport;

    public AirportScoreAvg(String iataCode, double scoreAvg) {
        this.iataCode = iataCode;
        this.scoreAvg = scoreAvg;
    }

    public void updateAirportScoreAvg(double newScoreAvg) {
        this.scoreAvg = newScoreAvg;
    }
}
