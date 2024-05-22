package com.kodilla.airporthater.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "AIRPORTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EntityListeners(AirportListener.class)
public class Airport {

    @Id
    @Column(name = "IATA_CODE", unique = true)
    private String iataCode;

    @Column(name = "ICAO_CODE", nullable = false, unique = true)
    private String icaoCode;

    @Column(name = "AIRPORT_NAME", nullable = false)
    private String name;

    @Column(name = "CITY", nullable = false)
    private String city;

    @OneToOne(mappedBy = "airport",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    @ToString.Exclude
    private AirportScoreAvg airportScoreAvg;

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "airport",
            fetch = FetchType.EAGER
    )
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public Airport(String iataCode, String icaoCode, String name, String city, AirportScoreAvg airportScoreAvg) {
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.name = name;
        this.city = city;
        this.airportScoreAvg = airportScoreAvg;
        if (airportScoreAvg != null) {
            airportScoreAvg.setAirport(this);
        }
    }
}
