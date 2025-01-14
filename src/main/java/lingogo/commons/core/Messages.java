package lingogo.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX = "The flashcard index provided is invalid";
    public static final String MESSAGE_FLASHCARDS_LISTED_OVERVIEW = "%1$d flashcards listed!";
    public static final String MESSAGE_INVALID_CSV_CONTENT = "The flashcards in %1$s are not in the correct format";
    public static final String MESSAGE_INVALID_CSV_HEADERS = "The headers in %1$s are not in the correct format";
    public static final String MESSAGE_FILE_NOT_FOUND = "%1$s cannot found in the data folder";
    public static final String MESSAGE_INVALID_CSV_FILE_NAME = "%1$s is not a valid CSV file name";
    public static final String MESSAGE_IN_SLIDESHOW_MODE = "This command can only be run when slideshow mode is not"
            + " active!";
    public static final String MESSAGE_NOT_IN_SLIDESHOW_MODE = "This command can only be run when slideshow mode is"
            + " active!";
    public static final String MESSAGE_FLASHCARD_ALREADY_ANSWERED = "This flashcard has already been answered!";
    public static final String MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_INDEX_RANGE = "The index range provided is invalid, exactly 2 indices "
        + "must be given where the second given index is larger or equal to the first index";
    public static final String MESSAGE_INVALID_N = "Input n has to be an integer that is greater than 0 but less than "
            + "the total number of flashcards in the list";

}
