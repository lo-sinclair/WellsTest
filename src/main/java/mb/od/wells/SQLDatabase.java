package mb.od.wells;

import mb.od.wells.entity.Equipment;
import mb.od.wells.entity.Well;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SQLDatabase {
    private final String url;

    public SQLDatabase() throws Exception{
        Connection conn;

        url = "jdbc:sqlite:test.db";
        Class.forName("org.sqlite.JDBC");
        conn = getConnection();

        Statement s = (Statement) conn.createStatement();

        String q;
        q = "CREATE TABLE IF NOT EXISTS well ("
                + "`id`INTEGER PRIMARY KEY, "
                + "`name` VARCHAR(32) NOT NULL UNIQUE);";
        s.executeUpdate(q);

        q = "CREATE TABLE IF NOT EXISTS equipment ("
                + "`id` INTEGER PRIMARY KEY, "
                + "`name` VARCHAR(32) NOT NULL UNIQUE,"
                + "`well_id` INTEGER, "
                +"FOREIGN KEY(`well_id`) REFERENCES well(id));";
        s.executeUpdate(q);
    }

    public int createEquipment(Equipment eq) {
        int count = 0;

        //insert
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            String q = "INSERT INTO equipment "
                    + "VALUES (NULL, '%s', %d);";
            count = stmt.executeUpdate(String.format(q, eq.getName(), eq.getWell_id()));

            stmt.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        };

        return count;
    }

    public int createWell(Well well) {
        int count = 0;
        int id = 0;
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            String q = "INSERT INTO well "
                    + "VALUES (NULL, '%s');";
            count = stmt.executeUpdate(String.format(q, well.getName()));
            ResultSet keys = stmt.getGeneratedKeys();
            if(keys.next()) {
                id = keys.getInt(1);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        };

        return id;
    }

    public int createEquipments(ArrayList<Equipment> eqs) {
        int count = 0;

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            StringJoiner joiner = new StringJoiner(", ");
            for(Equipment eq : eqs) {
                joiner.add(String.format("(NULL, '%s', %d)", eq.getName(), eq.getWell_id()) );
            }
            String q = "INSERT INTO equipment "
                    + "VALUES " + joiner.toString();
            count = stmt.executeUpdate(q);

            stmt.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        };

        return count;
    }


    public int getMaxEquipmentId(){
        int maxId = 0;

        try {
            Connection c = getConnection();
            Statement s = c.createStatement();
            String q = "SELECT max(`id`) AS `maxId` FROM equipment;";
            ResultSet result = s.executeQuery(q);

            if(result.next()) {
                maxId = result.getInt("maxId");
            }

            s.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId;
    }

    public List<Well> findAllWells(){
        List<Well> wells = new ArrayList<>();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            String q = "SELECT * FROM well;";

            ResultSet result = stmt.executeQuery(q);

            while(result.next()) {
                wells.add(new Well(
                    result.getInt("id"),
                    result.getString("name")
                ));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wells;
    }


    public List<Equipment> findEquipmentByWell(int wellId) {
        List<Equipment> equipments = new ArrayList<>();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            String q = "SELECT * FROM equipment WHERE `well_id` = %d;";

            ResultSet result = stmt.executeQuery(String.format(q, wellId));

            while(result.next()) {
                equipments.add (new Equipment(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("well_id")
                ));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return equipments;
    }


    public Well findWellByName(String name){
        Well well = null;

        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            String q = "SELECT * FROM well WHERE `name` = '%s';";

            ResultSet result = s.executeQuery(String.format(q, name));

            if(result.next()) {
                well = new Well(
                        result.getInt("id"),
                        result.getString("name")
                );
            }

            s.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return well;
    }


    /*

    public int createAction(Action a) {
        int count = 0;

        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

//			String q = "SELECT COUNT(*) AS rcount FROM priap_actions WHERE `sender` = '%s' AND `target` = '%s';";
//			ResultSet result = s.executeQuery(String.format(q, a.getSender(), a.getTarget()));
//			int rcount = result.getInt("rcount");
//			result.close();

            String q = "SELECT * FROM priap_actions WHERE `sender` = '%s' AND `target` = '%s';";
            ResultSet result = s.executeQuery(String.format(q, a.getSender(), a.getTarget()));
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            long diffInMillies = Math.abs(result.getLong("time") - timestamp.getTime() );
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if ( result.next() ) {
                int kissDaysInterval = Priap.getInstance().getConfig().getInt("params.kissDaysInterval");
                if ( diff > kissDaysInterval ) {

                    q = "UPDATE priap_actions SET "
                            + "time = %d "
                            + "WHERE sender = '%s' AND target = '%s';";
                    count = s.executeUpdate( String.format(q, a.getTime(), a.getSender(), a.getTarget()) );
                    s.close();
                    c.close();
                    return count;
                }
                else {
                    s.close();
                    c.close();
                    return -1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        };


        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            String q = "INSERT INTO priap_actions (`sender`, `target`, `action`, time) "
                    + "VALUES ('%s', '%s', '%s', %d)";
            count = s.executeUpdate(String.format(q, a.getSender(), a.getTarget(), a.getAction(), a.getTime()));

            s.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
*/



    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
