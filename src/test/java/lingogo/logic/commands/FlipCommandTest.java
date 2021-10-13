package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static lingogo.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.Flashcard;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FlipCommand}.
 */
public class FlipCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Flashcard flashcardToFlip = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        FlipCommand flipCommand = new FlipCommand(INDEX_FIRST_FLASHCARD);

        String expectedMessage =
            String.format(FlipCommand.MESSAGE_FLIP_FLASHCARD_SUCCESS, flashcardToFlip.getForeignPhrase());


        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());

        expectedModel.setFlashcard(flashcardToFlip, flashcardToFlip.getFlippedFlashcard());

        assertCommandSuccess(flipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListAgain_success() {

        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        Flashcard originalFlashcard = model.getFilteredFlashcardList().get(INDEX_SECOND_FLASHCARD.getZeroBased());
        Flashcard flashcardToFlip = originalFlashcard.getFlippedFlashcard();
        model.setFlashcard(originalFlashcard, flashcardToFlip);

        FlipCommand flipCommand = new FlipCommand(INDEX_SECOND_FLASHCARD);

        String expectedMessage =
            String.format(FlipCommand.MESSAGE_FLIP_FLASHCARD_SUCCESS, flashcardToFlip.getForeignPhrase());


        assertCommandSuccess(flipCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        FlipCommand flipCommand = new FlipCommand(outOfBoundIndex);

        assertCommandFailure(flipCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FlipCommand flipFirstFlashcard = new FlipCommand(INDEX_FIRST_FLASHCARD);
        FlipCommand flipSecondFlashcard = new FlipCommand(INDEX_SECOND_FLASHCARD);

        // same object -> returns true
        assertTrue(flipFirstFlashcard.equals(flipFirstFlashcard));

        // same values -> returns true
        FlipCommand flipFirstCard = new FlipCommand(INDEX_FIRST_FLASHCARD);
        assertTrue(flipFirstFlashcard.equals(flipFirstCard));

        // different types -> returns false
        assertFalse(flipFirstFlashcard.equals(1));

        // null -> returns false
        assertFalse(flipFirstFlashcard.equals(null));

        // different person -> returns false
        assertFalse(flipFirstFlashcard.equals(flipSecondFlashcard));
    }
}
