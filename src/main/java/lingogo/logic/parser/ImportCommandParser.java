package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_FILE_NAME;

import java.io.File;

import lingogo.logic.commands.ImportCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns a ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String fileName = args.trim();

        File f = new File("data/" + fileName);
        if (!fileName.endsWith(".csv")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_CSV_FILE_NAME, fileName));
        }
        if (!f.exists()) {
            throw new ParseException(
                    String.format(MESSAGE_FILE_NOT_FOUND, fileName));
        }

        return new ImportCommand(fileName);
    }
}
