package com.kodilla.airporthater.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "AIRPORTS_SCORES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportScoreAvg {

    @Id
    @Column(name = "IATA_CODE", unique = true)
    private String iataCode;

    @Column(name = "AIRPORT_SCORE")
    @Builder.Default
    private double scoreAvg = 0.0;

    @Setter
    @OneToOne
    @JoinColumn(name = "IATA_CODE", referencedColumnName = "IATA_CODE"/*, insertable = false*/)
    @ToString.Exclude
    private Airport airport;

    public AirportScoreAvg(String iataCode, double scoreAvg) {
        this.iataCode = iataCode;
        this.scoreAvg = scoreAvg;
    }

    // Metoda do aktualizacji AIRPORT_SCORE
    public void updateAirportScoreAvg(double newScoreAvg) {
        this.scoreAvg = newScoreAvg;
    }
}
