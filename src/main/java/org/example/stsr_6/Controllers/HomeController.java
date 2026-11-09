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
    @Autowired private HallRepository hallRepository;

    private User processUser;

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
        if (cinema.getMarks() == null) cinema.setMarks(0);
        if (cinema.getRating() == null) cinema.setRating(0.0f);
        if (cinema.getYear() == null) cinema.setYear(0);

        if (!cinemaRepository.findByHeadnameAndYear(cinema.getHeadname(), cinema.getYear()).isEmpty()) {
            return "Этот фильм уже есть в базе";
        }

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

    @GetMapping("/allmyfriend")
    public List<FriendRelationship> allmyfriend() {
        if (processUser == null) return null;
        return friendRelationshipRepository.findFriendRelationshipsByUsersone_Id(processUser.getId());
    }

    @GetMapping("/allusersforfriend")
    public List<User> allusersforfriend() {
        if (processUser == null) return null;
        List<User> allUsers = userRepository.findAll();
        // Убираем из списка самого себя
        allUsers.removeIf(u -> u.getId().equals(processUser.getId()));
        return allUsers;
    }

    @PostMapping("/addmyfriend")
    public String addmyfriend(@RequestBody User user) {
        if (processUser == null) return "ERROR_AUTH";
        if (user.getId().equals(processUser.getId())) return "ERROR_SELF";

        if (friendRelationshipRepository.findFriendRelationshipsByUsersone_IdAndUserstwo_Id(processUser.getId(), user.getId()) != null) {
            return "ERROR_ALREADY_FRIEND";
        }

        FriendRelationship fr = new FriendRelationship();
        fr.setUsersone(processUser);
        fr.setUserstwo(userRepository.findById(user.getId()).orElse(null));
        friendRelationshipRepository.save(fr);
        return "GOOD";
    }

    @PostMapping("/deletoemnyfriend")
    public String deletoemnyfriend(@RequestBody FriendRelationship friendRelationship) {
        if (processUser == null) return "ERROR_AUTH";

        FriendRelationship fr = friendRelationshipRepository.findFriendRelationshipsByUsersone_IdAndUserstwo_Id(
                processUser.getId(),
                friendRelationship.getUserstwo().getId()
        );

        if (fr != null) {
            friendRelationshipRepository.delete(fr);
            return "GOOD";
        }
        return "ERROR_NOT_FOUND";
    }

    @GetMapping("/getAccount")
    public User getAccount() {
        if (processUser == null) return null; // Если не авторизован
        User user = userRepository.findById(processUser.getId()).orElse(null);
        if (user != null) {
            user.setPassword(" ");
        }
        return user;
    }
}