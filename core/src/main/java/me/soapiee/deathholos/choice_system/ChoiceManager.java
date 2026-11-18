package me.soapiee.deathholos.choice_system;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.choice_system.priority.PriorityDataManager;
import me.soapiee.deathholos.choice_system.priority.PriorityGroupManager;
import me.soapiee.deathholos.choice_system.selector.SelectorDataManager;
import me.soapiee.deathholos.choice_system.selector.SelectorGroupManager;
import me.soapiee.deathholos.internals.data.DataHandler;
import me.soapiee.deathholos.internals.data.DataHandler_Priority;
import me.soapiee.deathholos.logic.GroupFactory;
import me.soapiee.deathholos.utils.Choices;
import me.soapiee.deathholos.utils.CustomLogger;

public class ChoiceManager {

    @Getter private final Choices choice;
    @Getter private final GroupManager groupManager;
    @Getter private final DataManager dataManager;

    public ChoiceManager(DeathHolos main, Choices choice) {
        GroupFactory groupFactory = new GroupFactory(main);
        CustomLogger customLogger = main.getCustomLogger();

        DataHandler dataHandler = main.getInternalsManager().initialiseDataHandler(choice);
        dataHandler.injectManagers(customLogger, main.getConfigManager().isDebugMode());

        if (dataHandler instanceof DataHandler_Priority && choice != Choices.PRIORITY) {
            choice = Choices.PRIORITY;
        }

        this.choice = choice;

        switch (choice) {
            case SELECTOR:
                SelectorGroupManager selectorGroupManager = new SelectorGroupManager(main, groupFactory);
                groupManager = selectorGroupManager;
                dataManager = new SelectorDataManager(customLogger, selectorGroupManager, dataHandler);
                break;
            case RANDOM:
            case PRIORITY:
            default:
                PriorityGroupManager priorityGroupManager = new PriorityGroupManager(main, groupFactory);
                groupManager = priorityGroupManager;
                dataManager = new PriorityDataManager(priorityGroupManager);
        }
    }

}
