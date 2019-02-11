package seedu.addressbook.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.tag.Tag;

import static seedu.addressbook.ui.TextUi.DISPLAYED_INDEX_OFFSET;

public class UpdateTagCommand extends Command{

    public static final String COMMAND_WORD = "updateTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": updates the person's tags identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 t/family";

    public static final String MESSAGE_UPDATE_PERSON_SUCCESS = "Updated Person: %1$s";
    private final Set<Tag> tags;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UpdateTagCommand(int targetVisibleIndex, Set<String> tags) throws IllegalValueException {
        super(targetVisibleIndex);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.tags = tagSet;
    }


    @Override
    public CommandResult execute() {
        try {
            addressBook.updateTagPerson(getTargetIndex(), tags);
            final ReadOnlyPerson target = getTargetPerson();
            return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }


}
