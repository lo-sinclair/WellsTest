package mb.od.wells.command;

import mb.od.wells.tools.XmlWriter;

import java.util.Scanner;

public class ThreeCommand implements ICommand{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean execute() {
        System.out.println("Укажите название файла (без расширения):");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();

        XmlWriter dataFile = new XmlWriter();
        dataFile.setDataFile(fileName + ".xml");
        try {
            dataFile.saveDataFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
