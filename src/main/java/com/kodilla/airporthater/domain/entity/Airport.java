package com.kodilla.airporthater.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "AIRPORTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
            fetch = FetchType.EAGER,
            optional = false)
    @ToString.Exclude
    private AirportScoreAvg airportScoreAvg;

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "airport",
            cascade = CascadeType.ALL,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(iataCode, airport.iataCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iataCode, icaoCode, name, city, airportScoreAvg, comments);
    }
}
