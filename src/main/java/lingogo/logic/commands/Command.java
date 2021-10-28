package lingogo.logic.commands;

import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Contains parameters that each command uses.
     */
    public enum Parameter {
        ENGLISH_PHRASE,
        FOREIGN_PHRASE,
        LANGUAGE,
        ENGLISH_KEYWORD,
        FOREIGN_KEYWORD,
        NUMBER_OF_FLASHCARDS,
        INDEX,
        INDEX_LIST,
        INDEX_RANGE,
        FILE_NAME,
        CSV_FILE_PATH;

        public String withCondition(String condition) {
            return toString() + " (" + condition + ")";
        }
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;
}
