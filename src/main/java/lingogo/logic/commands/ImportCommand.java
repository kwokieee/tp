package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_CONTENT;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_HEADERS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

/**
 * Imports the cards in the CSV file to the flashcard app.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_DESCRIPTION = "Imports flashcards from a CSV file into LingoGO!";

    public static final String[] COMMAND_PARAMETERS = new String[] {
        Parameter.CSV_FILE_NAME.withCondition("must exist in the data folder and have .csv extension")
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD + " dictionary.csv"
    };

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

    public static final String MESSAGE_SUCCESS = "LingoGO! has been updated with all the flashcards from %1$s";

    public static final String MESSAGE_NOT_UPDATED =
            "LingoGO! already contains all the flashcards you are importing from %1$s";

    public static final String IMPORT_IOEXCEPTION = "Could not load flashcards from %1$s into LingoGO!";

    private static final String[] csvHeaders = {"Language", "Foreign", "English"};
    private static List<Flashcard> importedFlashcardList;
    private final String fileName;

    /**
     * @param fileName of the CSV file to be imported to the flashcard app
     */
    public ImportCommand(String fileName) {
        requireNonNull(fileName);

        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        File f = new File("data/" + fileName);
        if (!f.exists()) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, fileName));
        }

        // try-with-resources ensures that CSVReader is closed after execution of this try block
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream("data/" + fileName), StandardCharsets.UTF_8)).build()) {
            getImportedFlashcardList(reader);
        } catch (CsvValidationException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_CSV_CONTENT, fileName));
        } catch (IOException ioe) {
            throw new CommandException(String.format(IMPORT_IOEXCEPTION, fileName));
        }

        boolean isUpdated = false;
        for (Flashcard card : importedFlashcardList) {
            if (!model.hasFlashcard(card)) {
                isUpdated = true;
                model.addFlashcard(card);
            }
        }

        if (isUpdated) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
        }
        return new CommandResult(String.format(MESSAGE_NOT_UPDATED, fileName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && fileName.equals(((ImportCommand) other).fileName)); // state check
    }

    /**
     * Uses CSVReader to import the contents of the {@code fileName} to {@code model}.
     * @throws CommandException to indicate that CSV file content is not of the correct format
     * @throws CsvValidationException when CSV file is not valid
     * @throws IOException involved when reading from an external file
     */
    private void getImportedFlashcardList(CSVReader reader)
            throws CommandException, CsvValidationException, IOException {
        String[] line = reader.readNext();
        if (!Arrays.toString(line).equals(Arrays.toString(csvHeaders))) {
            throw new CommandException(String.format(MESSAGE_INVALID_CSV_HEADERS, fileName));
        }
        importedFlashcardList = new LinkedList<>();
        while ((line = reader.readNext()) != null) {
            if (line.length != 3 || line[0].isBlank() || line[1].isBlank() || line[2].isBlank()) {
                throw new CommandException(String.format(MESSAGE_INVALID_CSV_CONTENT, fileName));
            }
            String languageType = line[0];
            String englishPhrase = line[2];
            String foreignPhrase = line[1];
            if (!LanguageType.isValidLanguageType(languageType)
                    || !Phrase.isValidPhrase(englishPhrase)
                    || !Phrase.isValidPhrase(foreignPhrase)) {
                throw new CommandException(String.format(MESSAGE_INVALID_CSV_CONTENT, fileName));
            }
            Flashcard card = new Flashcard(
                    new LanguageType(languageType), new Phrase(englishPhrase), new Phrase(foreignPhrase));
            importedFlashcardList.add(card);
        }
    }
}
