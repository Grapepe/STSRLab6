package org.example.stsr_6.Controllers;

import org.example.stsr_6.Entities.*;
import org.example.stsr_6.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin
public class HomeController {

    @Autowired private UserRepository userRepository;
    @Autowired private CinemaRepository cinemaRepository;
    @Autowired private CinemaUserRepository cinemaUserRepository;
    @Autowired private FriendRelationshipRepository friendRelationshipRepository;
    @Autowired private HallRepository hallRepository; // Новый репозиторий

    private User processUser; // В реальном проекте использовать HttpSession

    // --- РАБОТА С ПОЛЬЗОВАТЕЛЯМИ ---
    @PostMapping("/add")
    public String adder(@RequestBody User user) {
        if (userRepository.findByLogin(user.getLogin()) != null) return "ERRORLOGIN";
        if (userRepository.findByEmail(user.getEmail()) != null) return "ERROREMAIL";
        user.setShowforaddfr(false);
        user.setShowotherfilms(false);
        userRepository.save(user);
        return "GOOD";
    }

    @PostMapping("/autorization")
    public String auth(@RequestBody User user) {
        User foundUser = userRepository.findByLogin(user.getLogin());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            processUser = foundUser;
            return "AUTH";
        }
        return "NOAUTH";
    }

    @GetMapping("/deauth")
    public String deauth() {
        processUser = null;
        return "DEAUTH";
    }

    @GetMapping("/getprocessuser2")
    public String rs2() {
        if (processUser == null) return "NULL";
        if (processUser.getLogin().equals("admin")) return "ADMIN";
        return "USER";
    }

    @GetMapping("/allfilms")
    public List<Cinema> allfilms() {
        return cinemaRepository.findAll();
    }

    @PostMapping("/addnewfilm")
    public String addnewfilm(@RequestBody Cinema cinema) {
        // 1. Устанавливаем значения по умолчанию, если фронтенд их не прислал
        if (cinema.getMarks() == null) cinema.setMarks(0);
        if (cinema.getRating() == null) cinema.setRating(0.0f);
        if (cinema.getYear() == null) cinema.setYear(0); // <-- ДОБАВЛЕНО

        if (!cinemaRepository.findByHeadnameAndYear(cinema.getHeadname(), cinema.getYear()).isEmpty()) {
            return "Этот фильм уже есть в базе";
        }

        // 3. Сохранение
        cinemaRepository.save(cinema);
        return "1. Фильм успешно добавлен в каталог";
    }

    @PostMapping("/addmyfilm")
    public String addmyfilm(@RequestBody Cinema cinema) {
        if (processUser == null) return "ERROR";
        if (cinemaUserRepository.findByCinemasAndUsers(cinema, processUser) != null) {
            return "ERROR";
        }
        CinemaUser cu = new CinemaUser();
        cu.setCinemas(cinema);
        cu.setUsers(processUser);
        cu.setStatuscinema("Без статуса просмотра");
        cu.setRatinguser(0); // Уже установлено
        cinemaUserRepository.save(cu);
        return "GOOD";
    }

    @GetMapping("/allhalls")
    public List<Hall> allhalls() {
        return hallRepository.findAll();
    }

    @PostMapping("/addhall")
    public String addhall(@RequestBody Hall hall) {
        if (processUser == null || !processUser.getLogin().equals("admin")) {
            return "ERROR_AUTH";
        }
        hallRepository.save(hall);
        return "1. Кинозал успешно добавлен";
    }

    @DeleteMapping("/deletehall/{id}")
    public String deletehall(@PathVariable int id) {
        if (processUser == null || !processUser.getLogin().equals("admin")) {
            return "ERROR_AUTH";
        }
        hallRepository.deleteById(id);
        return "1. Кинозал удален";
    }
}