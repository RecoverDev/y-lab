package ru.list;

import ru.list.Controller.AdminController;
import ru.list.Controller.AutorizationController;
import ru.list.Controller.PersonController;
import ru.list.Controller.StatisticController;
import ru.list.Db.DBConnection;
import ru.list.Db.Migration;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Repository.PersonRepository;
import ru.list.Repository.DBImplementation.HabitRepositoryDBImplementation;
import ru.list.Repository.DBImplementation.LogBookRepositoryDBImplementation;
import ru.list.Repository.DBImplementation.PersonRepositoryDBImplementation;
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
import ru.list.logger.Logger;

/**
 * Класс инициализирует все объекты приложения и предоставляет их при необходимости
 * Реализован шаблон Одиночка
 */
public class ApplicationData implements Observe{
    Person currentPerson = null;

    Logger logger = null;
    HabitProperties properties = null;
    DBConnection dbConnection = null;
    
    PersonRepository personRepository = null;
    HabitRepository habitRepository = null;
    LogBookRepository logBookRepository = null;

    PersonService personService = null;
    HabitService habitService = null;
    LogBookService logBookService = null;
    AutorizationService autorizationService = null;
    StatisticService statisticService = null;

    AdminController adminController = null;
    AutorizationController autorizationController = null;
    PersonController personController = null;
    StatisticController statisticController = null;

    private final static ApplicationData application = new ApplicationData();


    private ApplicationData() {
        logger = new Logger();
        properties = new HabitProperties("application.properties");
        properties.load();
        dbConnection = new DBConnection(properties, logger);
        dbConnection.connect();

        Migration migration = new Migration(dbConnection.getConnection());
        boolean resultMirration = migration.migrate(properties.getChangelogFile());

        if (resultMirration) {
            personRepository = new PersonRepositoryDBImplementation(dbConnection, logger);
            habitRepository = new HabitRepositoryDBImplementation(dbConnection, logger);
            logBookRepository = new LogBookRepositoryDBImplementation(dbConnection, logger);

            personService = new PersonServiceImplementation(personRepository);
            habitService = new HabitServiceImplementation(habitRepository, logBookRepository);
            logBookService = new LogBookServiceImplementation(logBookRepository);
            statisticService = new StatisticServiceImplementation(habitRepository, logBookRepository);
            autorizationService = new AutorizationServiceImplementation(personRepository);

            adminController = new AdminController(personService);
            autorizationController = new AutorizationController(autorizationService, personService);
            autorizationController.addListener(this);
            personController = new PersonController(personService, habitService, logBookService, statisticService);
            statisticController = new StatisticController(null, statisticService);
        } else {
            logger.addRecord("Ошибка миграции", false);
        }
    }

    public AutorizationService getAutorizationService() {
        return autorizationService;
    }
    public static ApplicationData getInstance() {
        return application;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public AutorizationController getAutorizationController() {
        return autorizationController;
    }

    public PersonController getPersonController() {
        return personController;
    }

    public StatisticController getStatisticController() {
        return statisticController;
    }

    @Override
    public void observe(Object o) {
        if (o instanceof Person person) {
            personController.setCurrentPerson(person, logger);
            statisticController.setCurrentPerson(person);
        }
    }

}
