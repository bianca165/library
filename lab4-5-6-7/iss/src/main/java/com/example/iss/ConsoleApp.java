/*
package com.example.iss;

import domain.User;
import domain.Carte;
import repo.CarteDBRepository;
import repo.RepositoryException;
import repo.UserDBRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class ConsoleApp {
    private static CarteDBRepository carteRepository = new CarteDBRepository();
    private static UserDBRepository userRepository = new UserDBRepository();
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Bine ai venit!");

        // Autentificare
        User currentUser = authenticate();
        if (currentUser == null) {
            System.out.println("Autentificare nereușită. Închidere aplicație.");
            return;
        }

        System.out.println("Autentificare reușită ca " + currentUser.getRol());

        // Meniu principal în funcție de rolul utilizatorului
        if (currentUser.getRol().equals("abonat")) {
            meniuAbonat(currentUser);
        } else {
            // Alte acțiuni pentru alte roluri
        }

        userRepository.closeConnection();
    }


    private static User authenticate() {
        System.out.println("Introduceți numele de utilizator:");
        String username = scanner.nextLine();
        System.out.println("Introduceți parola:");
        String password = scanner.nextLine();
        return userRepository.authenticate(username, password);
    }

    private static void meniuAbonat(User currentUser) {
        boolean continua = true;
        while (continua) {
            System.out.println("Meniu abonat:");
            System.out.println("1. Împrumută carte");
            System.out.println("2. Returnează carte");
            System.out.println("0. Ieșire");

            int optiune = Integer.parseInt(scanner.nextLine());
            switch (optiune) {
                case 1:
                    imprumutaCarte(currentUser);
                    break;
                case 2:
                    returneazaCarte(currentUser);
                    break;
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Opțiune invalidă!");
            }
        }
    }

    private static void imprumutaCarte(User currentUser) {
        System.out.println("Introduceți codul exemplarului pe care doriți să-l împrumutați:");
        String codExemplar = scanner.nextLine();

        // Verificăm dacă exemplarul există și este disponibil
        if (verificaDisponibilitateExemplar(codExemplar)) {
            // Exemplarul este disponibil, putem împrumuta
            carteRepository.imprumutaExemplar(currentUser.getId(),codExemplar);
            System.out.println("Exemplar împrumutat cu succes!");
        } else {
            System.out.println("Exemplarul nu este disponibil sau nu există!");
        }
    }

    private static void returneazaCarte(User currentUser) {
        System.out.println("Introduceți codul exemplarului pe care doriți să-l returnați:");
        String codExemplar = scanner.nextLine();

        // Verificăm dacă exemplarul este înregistrat ca împrumutat de către utilizatorul curent
        if (verificaExemplarImprumutat(codExemplar)) {
            // Exemplarul este împrumutat de utilizatorul curent, putem să-l returnăm
            carteRepository.returneazaExemplar(currentUser.getId(), codExemplar);
            System.out.println("Exemplar returnat cu succes!");
        } else {
            System.out.println("Exemplarul nu este împrumutat de către utilizatorul curent sau nu există!");
        }
    }

    private static boolean verificaDisponibilitateExemplar(String codExemplar) {
        // Verificăm disponibilitatea exemplarului în baza de date
        // Utilizăm metoda din CarteDBRepository care verifică disponibilitatea exemplarului
        return carteRepository.verificaDisponibilitateExemplar(codExemplar);
    }

    private static boolean verificaExemplarImprumutat(String codExemplar) {
        // Verificăm dacă exemplarul este împrumutat de către utilizatorul curent
        // Utilizăm metoda din CarteDBRepository care verifică dacă exemplarul este împrumutat de un anumit utilizator
        return carteRepository.verificaExemplarImprumutat(codExemplar);
    }

}
*/
