package mb.od.wells;

import mb.od.wells.command.ICommand;
import mb.od.wells.command.OneCommand;
import mb.od.wells.command.ThreeCommand;
import mb.od.wells.command.TwoCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Main {
    private static SQLDatabase db;

    public static void main(String[] args) {
        //Все доступные команды
        Map<String, ICommand> commands = new HashMap<>();
        commands.put("1", new OneCommand());
        commands.put("2", new TwoCommand());
        commands.put("3", new ThreeCommand());

        try {
            db = new SQLDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String cmd = "";

        if(args.length == 0) {
            System.out.println("\u001B[33m [1] - Создание N кол-ва оборудования на скважине\n\u001B[0m");
            System.out.println("\u001B[33m [2] - Вывод общей информации об оборудовании на скважинах\n\u001B[0m");
            System.out.println("\u001B[33m [3] - Вывод общей информации об оборудовании на скважинах\n\u001B[0m");

            try {
                BufferedReader is = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                cmd = is.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            cmd = args[0];
        }

        if (commands.containsKey(cmd)){
            commands.get(cmd).execute();
        }
    }

    public static SQLDatabase getDb(){
        return db;
    }

}