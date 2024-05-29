//package com.kodilla.airporthater.service;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SecurityService {
//
//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            try {
//                return Long.parseLong(authentication.getName());
//            } catch (NumberFormatException ex) {
//                // Jeśli nie uda się rzutować na Long, zwróć null lub inny domyślny identyfikator
//                return null;
//            }
//        }
//        return null;
//    }
//}