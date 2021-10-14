package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lingogo.logic.commands.AddCommand;
import lingogo.logic.commands.ClearCommand;
import lingogo.logic.commands.Command;
import lingogo.logic.commands.DeleteCommand;
import lingogo.logic.commands.DownloadCommand;
import lingogo.logic.commands.EditCommand;
import lingogo.logic.commands.ExitCommand;
import lingogo.logic.commands.FindCommand;
import lingogo.logic.commands.FlipCommand;
import lingogo.logic.commands.HelpCommand;
import lingogo.logic.commands.ListCommand;
import lingogo.logic.commands.TestCommand;
import lingogo.logic.commands.UploadCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class FlashcardAppParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case DownloadCommand.COMMAND_WORD:
            return new DownloadCommandParser().parse(arguments);

        case UploadCommand.COMMAND_WORD:
            return new UploadCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case FlipCommand.COMMAND_WORD:
            return new FlipCommandParser().parse(arguments);

        case TestCommand.COMMAND_WORD:
            return new TestCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}