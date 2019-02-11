package seedu.addressbook.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.ui.TextUi;
import seedu.addressbook.util.TestUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UpdateTagCommandTest {
    private AddressBook emptyAddressBook;
    private AddressBook addressBook;

    private List<ReadOnlyPerson> emptyDisplayList;
    private List<ReadOnlyPerson> listWithEveryone;
    private List<ReadOnlyPerson> listWithSurnameDoe;

    @Before
    public void setUp() throws Exception {
        Person johnDoe = new Person(new Name("John Doe"), new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false), Collections.emptySet());
        Person janeDoe = new Person(new Name("Jane Doe"), new Phone("91234567", false),
                new Email("jane@doe.com", false), new Address("33G Ohm Road", false), Collections.emptySet());
        Person samDoe = new Person(new Name("Sam Doe"), new Phone("63345566", false),
                new Email("sam@doe.com", false), new Address("55G Abc Road", false), Collections.emptySet());
        Person davidGrant = new Person(new Name("David Grant"), new Phone("61121122", false),
                new Email("david@grant.com", false), new Address("44H Define Road", false),
                Collections.emptySet());

        emptyAddressBook = TestUtil.createAddressBook();
        addressBook = TestUtil.createAddressBook(johnDoe, janeDoe, davidGrant, samDoe);

        emptyDisplayList = TestUtil.createList();

        listWithEveryone = TestUtil.createList(johnDoe, janeDoe, davidGrant, samDoe);
    }

    @Test
    public void execute_validIndex_personIsUpdated() throws UniquePersonList.PersonNotFoundException, IllegalValueException {
        Set<String> tags1 = new HashSet<String>();
        Set<String> tags2 = new HashSet<String>();
        Set<String> tags3 = new HashSet<String>();
        tags1.add("family");
        tags2.add("friends");
        tags3.add("animals");
        assertUpdateTagSuccessful(1, tags1, addressBook, listWithEveryone);
        assertUpdateTagSuccessful(listWithEveryone.size(), tags2, addressBook, listWithEveryone);

        int middleIndex = (listWithEveryone.size() / 2) + 1;
        assertUpdateTagSuccessful(middleIndex, tags3, addressBook, listWithEveryone);
    }

    /**
     * Creates a new update command.
     *
     * @param targetVisibleIndex of the person that we want to delete
     */
    private UpdateTagCommand createUpdateCommand(int targetVisibleIndex, Set<String> tags, AddressBook addressBook,
                                              List<ReadOnlyPerson> displayList) throws IllegalValueException {

        UpdateTagCommand command = new UpdateTagCommand(targetVisibleIndex, tags);
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertCommandBehaviour(UpdateTagCommand updateTagCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = updateTagCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook.getAllPersons(), actualAddressBook.getAllPersons());
    }

    /**
     * Asserts that the person at the specified index can be successfully updated
     * The addressBook passed in will not be modified (no side effects).
     *
     * @throws UniquePersonList.PersonNotFoundException if the selected person is not in the address book
     */
    private void assertUpdateTagSuccessful(int targetVisibleIndex, Set<String> tags, AddressBook addressBook,
                                           List<ReadOnlyPerson> displayList) throws UniquePersonList.PersonNotFoundException, IllegalValueException {

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }



        AddressBook expectedAddressBook = TestUtil.clone(addressBook);
        expectedAddressBook.updateTagPerson(targetVisibleIndex, tagSet);
        String targetPerson = expectedAddressBook.getPersonByIndex(targetVisibleIndex);
        String expectedMessage = String.format(UpdateTagCommand.MESSAGE_UPDATE_PERSON_SUCCESS, targetPerson);

        AddressBook actualAddressBook = TestUtil.clone(addressBook);
        UpdateTagCommand command = createUpdateCommand(targetVisibleIndex, tags, actualAddressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, expectedAddressBook, actualAddressBook);
    }

}