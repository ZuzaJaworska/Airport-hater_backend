package com.kodilla.airporthater.scheduler;

import com.kodilla.airporthater.config.AdminConfig;
import com.kodilla.airporthater.domain.Email;
import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.repository.UserRepository;
import com.kodilla.airporthater.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailService emailService;
    private final AdminConfig adminConfig;
    private final AirportRepository airportRepository;
    private final AirportScoreAvgRepository airportScoreAvgRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final static String SUBJECTNEWAIRPORT = "New airport for rating in our database";
    private final static String SUBJECTLOWSCORE = "New airport with score below 3!";
    private final static String SUBJECTEVERYWEEK = "Update what's going on in our Airport Hater";

    @Scheduled(cron = "0 0 10 * * MON")
    public void sendMondayInformationEmail() {
        long sizeUsers = userRepository.count();
        String userOrUsers = (sizeUsers == 1) ? " user" : " users";
        long sizeAirports = airportRepository.count();
        String airportOrAirports = (sizeAirports == 1) ? " airport" : " airports";
        long sizeComments = commentRepository.count();
        String commentOrComments = (sizeComments == 1) ? " comment" : " comments";
        emailService.send(
                new Email(
                        adminConfig.getAdminMail(),
                        SUBJECTEVERYWEEK,
                        "Currently in database we got:\n" +
                                "- " + sizeUsers + userOrUsers + ",\n" +
                                "- " + sizeAirports + airportOrAirports + ",\n" +
                                "- impressive " + sizeComments + commentOrComments + "!\n\n" +
                                "Thank you for using our Airport Hater!\n" +
                                "Let's rate airports together!"
                )
        );
    }

    public void sendLowScoreAirportEmail(String iataCode) {
        emailService.send(
                new Email(
                        adminConfig.getAdminMail(),
                        SUBJECTLOWSCORE,
                        "Another airport is rated below 3.0 -  " + airportRepository.findByIataCode(iataCode).get().getName() +
                                " with score = " + airportScoreAvgRepository.findByIataCode(iataCode).get().getScoreAvg() +
                                "!\nIt has to be a really bad airport..."
                )
        );
    }

    public void sendNewAirportEmail(String iataCode) {
        emailService.send(
                new Email(
                        adminConfig.getAdminMail(),
                        SUBJECTNEWAIRPORT,
                        "There's new airport in our database - " + airportRepository.findByIataCode(iataCode).get().getName() +
                                "(IATA: " + iataCode +
                                ", ICAO: " + airportRepository.findByIataCode(iataCode).get().getIcaoCode() +
                                ") in " + airportRepository.findByIataCode(iataCode).get().getCity() + "!"
                )
        );
    }
}
