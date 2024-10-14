package ru.list;

import ru.list.Controller.AdminController;
import ru.list.Controller.AutorizationController;
import ru.list.Controller.PersonController;
import ru.list.Controller.StatisticController;
import ru.list.Model.Person;
import ru.list.Out.MainView;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Repository.PersonRepository;
import ru.list.Repository.Implementation.HabitRepositoryImplementation;
import ru.list.Repository.Implementation.LogBookRepositoryImplementation;
import ru.list.Repository.Implementation.PersonRepositoryImplementation;
import ru.list.Service.AutorizationService;
import ru.list.Service.HabitService;
import ru.list.Service.LogBookService;
import ru.list.Service.PersonService;
import ru.list.Service.StatisticService;
import ru.list.Service.Implementation.AutorizationServiceImplementation;
import ru.list.Service.Implementation.HabitServiceImplementation;
import ru.list.Service.Implementation.LogBookServiceImplementation;
import ru.list.Service.Implementation.PersonServiceImplementation;
import ru.list.Service.Implementation.StatisticServiceImplementation;

/*
 * главный цикл программы
 */
public class MainCycle implements Observe {
    private PersonRepository personRepository = null;
    private HabitRepository habitRepository = null;
    private LogBookRepository logBookRepository = null;
    private PersonService personService = null;
    private HabitService habitService = null;
    private LogBookService logBookService = null;
    private AutorizationService autorizationService = null;
    private StatisticService statisticService = null;
    private AutorizationController autorizationController = null;
    private PersonController personController = null;
    private AdminController adminController = null;
    private boolean repeat = true;

    public MainCycle() {
        personRepository = new PersonRepositoryImplementation();
        habitRepository = new HabitRepositoryImplementation();
        logBookRepository = new LogBookRepositoryImplementation();

        personService = new PersonServiceImplementation(personRepository);
        habitService = new HabitServiceImplementation(habitRepository, logBookRepository);
        autorizationService = new AutorizationServiceImplementation(personRepository);
        logBookService = new LogBookServiceImplementation(logBookRepository);
        statisticService = new StatisticServiceImplementation(habitRepository, logBookRepository);

        autorizationController = new AutorizationController(autorizationService, personService);
        personController = new PersonController(personService, habitService, logBookService, statisticService);
        adminController = new AdminController(personService);
        autorizationController.addListener(this);
        personController.addListener(this);
        adminController.addListener(this);
    }


    public void run() {
        Person currentUser = null;

        while (repeat) {
            MainView.showTitle();
            if (currentUser == null) {
                currentUser = autorizationController.startForm();
            } else {
                switch (currentUser.getRole()) {
                    case 0:
                        personController.setCurrentPerson(currentUser);
                        personController.showMenu();
                        break;
                    case 1:
                        adminController.showMenu();
                        break;
                    default:
                        break;
                }
            }
        }
        MainView.showFinal();
    }

    @Override
    public void observe(Object o) {
        if(o instanceof Integer answer) {
            repeat = (answer != 0);
        }
    }

}
