package mb.od.wells.command;

public interface ICommand {
    String getName();

    String getDescription();

    boolean execute();
}
