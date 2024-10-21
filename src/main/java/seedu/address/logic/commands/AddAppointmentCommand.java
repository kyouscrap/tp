package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;


/**
 * Add the appointment details of an existing person in the address book.
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "make_appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add appointment details of the patient specified "
            + "by the index number used in the displayed patient list.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_APPOINTMENT + "DESCRIPTION "
            + PREFIX_START + "START DATE TIME "
            + PREFIX_END + "END DATE TIME "
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT + " Surgery "
            + PREFIX_START + " dd-MM-yyyy-HH-mm "
            + PREFIX_END + " dd-MM-yyyy-HH-mm ";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Appointment Added \n\n%1$s";
    private final Index index;
    private final Appointment appointment;
    /**
     * @param index of the person in the filtered person list to edit
     * @param appointment details to edit the person with
     */
    public AddAppointmentCommand(Appointment appointment, Index index) {
        requireNonNull(index);
        requireNonNull(appointment);
        this.index = index;
        this.appointment = appointment;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());




        return new CommandResult(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddAppointmentCommand = (AddAppointmentCommand) other;
        return index.equals(otherAddAppointmentCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .add("appointment", appointment)
                .toString();
    }
}