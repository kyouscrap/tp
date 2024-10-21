package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;


/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        String[] indexMultimapSplit = args.split(" ", 2);
        // Ensure the input can be split into exactly two parts (index and the rest)
        if (indexMultimapSplit.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(indexMultimapSplit[1], PREFIX_APPOINTMENT, PREFIX_START, PREFIX_END);
        if (!arePrefixesPresent(argMultimap, PREFIX_APPOINTMENT, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().isEmpty()
                || indexMultimapSplit.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_APPOINTMENT, PREFIX_START, PREFIX_END);

        Index index;
        try {
            index = ParserUtil.parseIndex(indexMultimapSplit[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE), pe);
        }
        String appointmentDescription = ParserUtil.parseAppointmentDescription(
                argMultimap.getValue(PREFIX_APPOINTMENT).get());
        LocalDateTime startDateTime = ParserUtil.parseLocalDateTime(
                argMultimap.getValue(PREFIX_START).get());
        LocalDateTime endDateTime = ParserUtil.parseLocalDateTime(
                argMultimap.getValue(PREFIX_END).get());
        if (endDateTime.isBefore(startDateTime)) {
            throw new ParseException("The end time must be after the start time.");
        }
        Appointment appointment = new Appointment(appointmentDescription, startDateTime, endDateTime, null);
        return new AddAppointmentCommand(appointment, index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}