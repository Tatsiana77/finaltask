package by.edu.sarnatskaya.pharmacy.controller.command;

import by.edu.sarnatskaya.pharmacy.controller.command.impl.common.GoToMainCommand;
import by.edu.sarnatskaya.pharmacy.controller.command.impl.common.NonExistentCommand;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import java.util.EnumSet;
import static by.edu.sarnatskaya.pharmacy.model.entity.User.Role.*;

public enum CommandType {
    GO_TO_MAIN_COMMAND(new GoToMainCommand(), EnumSet.of(ADMIN, CLIENT, GUEST, PHARMACIST)),
    NON_EXISTENT_COMMAND(new NonExistentCommand(), EnumSet.of(ADMIN,CLIENT, GUEST, PHARMACIST)),
    ;

    private Command command;
    private EnumSet<User.Role> allowedRoles;

    CommandType(Command command, EnumSet<User.Role> allowedRoles) {
        this.command = command;
        this.allowedRoles = allowedRoles;
    }

    public Command getCurrentCommand() {
        return command;
    }

    public void setAllowedRoles(EnumSet<User.Role> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }
}
