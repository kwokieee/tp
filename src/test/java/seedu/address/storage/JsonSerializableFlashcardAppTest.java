package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.FlashcardApp;
import seedu.address.testutil.TypicalFlashcards;

public class JsonSerializableFlashcardAppTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFlashcardAppTest");
    private static final Path TYPICAL_FLASHCARDS_FILE = TEST_DATA_FOLDER.resolve("typicalFlashcardsFlashcardApp.json");
    private static final Path INVALID_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("invalidFlashcardFlashcardApp.json");
    private static final Path DUPLICATE_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("duplicateFlashcardFlashcardApp"
        + ".json");

    @Test
    public void toModelType_typicalFlashcardsFile_success() throws Exception {
        JsonSerializableFlashcardApp dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARDS_FILE,
            JsonSerializableFlashcardApp.class).get();
        FlashcardApp flashCardAppFromFile = dataFromFile.toModelType();
        FlashcardApp typicalFlashcardsFlashcardApp = TypicalFlashcards.getTypicalFlashcardApp();

        System.out.println(flashCardAppFromFile.getFlashcardList());
        System.out.println(typicalFlashcardsFlashcardApp.getFlashcardList());

        assertEquals(flashCardAppFromFile, typicalFlashcardsFlashcardApp);
    }

    @Test
    public void toModelType_invalidFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcardApp dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
            JsonSerializableFlashcardApp.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateFlashcards_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcardApp dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
            JsonSerializableFlashcardApp.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFlashcardApp.MESSAGE_DUPLICATE_FLASHCARD,
            dataFromFile::toModelType);
    }

}
