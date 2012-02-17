import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


/**
 * MySQLSource.java - Used for accessing users and such from a mysql database
 * 
 * @author James
 */
public class MySQLSource extends DataSource {

    private String table_groups, table_users, table_items, table_kits, table_warps, table_homes, table_reservelist, table_whitelist, table_bans, table_enderblocks, table_antixrayblocks, table_muted_players;

    @Override
    public void initialize() {
        PropertiesFile properties = new PropertiesFile("mysql.properties");

        table_groups = properties.getString("groups", "groups");
        table_users = properties.getString("users", "users");
        table_items = properties.getString("items", "items");
        table_kits = properties.getString("kits", "kits");
        table_warps = properties.getString("warps", "warps");
        table_homes = properties.getString("homes", "homes");
        table_whitelist = properties.getString("whitelist", "whitelist");
        table_reservelist = properties.getString("reservelist", "reservelist");
        table_bans = properties.getString("bans", "bans");
        table_enderblocks = properties.getString("enderblocks", "enderblocks");
        table_antixrayblocks = properties.getString("antixrayblocks", "antixrayblocks");
        table_muted_players = properties.getString("muted-players", "muted_players");
        loadGroups();
        loadKits();
        loadHomes();
        loadWarps();
        loadItems();
        loadEnderBlocks();
        loadAntiXRayBlocks();
        loadMutedPlayers();
        // loadBanList();
    }

    @Override
    public void loadGroups() {
        synchronized (groupLock) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                groups = new ArrayList<Group>();
                ps = conn.prepareStatement("SELECT * FROM " + table_groups);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Group group = new Group();

                    group.Administrator = rs.getBoolean("admin");
                    group.CanModifyWorld = rs.getBoolean("canmodifyworld");
                    group.Commands = rs.getString("commands").split(",");
                    group.DefaultGroup = rs.getBoolean("defaultgroup");
                    group.ID = rs.getInt("id");
                    group.IgnoreRestrictions = rs.getBoolean("ignoresrestrictions");
                    group.InheritedGroups = rs.getString("inheritedgroups").split(",");
                    group.Name = rs.getString("name");
                    group.Prefix = rs.getString("prefix");
                    if (group.InheritedGroups.length == 1) {
                        if (group.InheritedGroups[0].equalsIgnoreCase(group.Name)) {
                            group.InheritedGroups = new String[] { "" };
                        }
                    }
                    groups.add(group);
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive groups from group table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }

    @Override
    public void loadKits() {
        synchronized (kitLock) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                kits = new ArrayList<Kit>();
                ps = conn.prepareStatement("SELECT * FROM " + table_kits);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Kit kit = new Kit();

                    kit.Delay = rs.getInt("delay");
                    kit.Group = rs.getString("group");
                    kit.ID = rs.getInt("id");
                    kit.Name = rs.getString("name");
                    kit.IDs = new HashMap<String, Integer>();

                    String[] ids = rs.getString("items").split(",");

                    for (String str : ids) {
                        String id = "";
                        int amount = 1;

                        if (str.contains(" ")) {
                            id = str.split(" ")[0];
                            amount = Integer.parseInt(str.split(" ")[1]);
                        } else {
                            id = str;
                        }
                        kit.IDs.put(id, amount);
                    }
                    kits.add(kit);
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive kits from kit table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }

    @Override
    public void loadHomes() {
        synchronized (homeLock) {
            if (!etc.getInstance().canSaveHomes()) {
                return;
            }
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                homes = new ArrayList<Warp>();
                ps = conn.prepareStatement("SELECT * FROM " + table_homes);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Location location = new Location();

                    location.x = rs.getDouble("x");
                    location.y = rs.getDouble("y");
                    location.z = rs.getDouble("z");
                    location.rotX = rs.getFloat("rotX");
                    location.rotY = rs.getFloat("rotY");
                    Warp home = new Warp();

                    home.ID = rs.getInt("id");
                    home.Location = location;
                    home.Name = rs.getString("name");
                    home.Group = rs.getString("group");
                    homes.add(home);
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive homes from home table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }

    @Override
    public void loadWarps() {
        synchronized (warpLock) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                warps = new ArrayList<Warp>();
                ps = conn.prepareStatement("SELECT * FROM " + table_warps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Location location = new Location();

                    location.x = rs.getDouble("x");
                    location.y = rs.getDouble("y");
                    location.z = rs.getDouble("z");
                    location.rotX = rs.getFloat("rotX");
                    location.rotY = rs.getFloat("rotY");
                    location.dimension = rs.getInt("dimension");
                    Warp warp = new Warp();

                    warp.ID = rs.getInt("id");
                    warp.Location = location;
                    warp.Name = rs.getString("name");
                    warp.Group = rs.getString("group");
                    warps.add(warp);
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive warps from warp table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }

    @Override
    public void loadItems() {
        synchronized (itemLock) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                items = new HashMap<String, Integer>();
                ps = conn.prepareStatement("SELECT * FROM " + table_items);
                rs = ps.executeQuery();
                while (rs.next()) {
                    items.put(rs.getString("name"), rs.getInt("itemid"));
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive items from item table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }
    
    @Override
    public void loadEnderBlocks() {
        synchronized (enderBlocksLock) {
            enderBlocks = new ArrayList<Integer>();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                ps = conn.prepareStatement("SELECT * FROM " + table_enderblocks);
                rs = ps.executeQuery();
                while (rs.next()) {
                    enderBlocks.add(rs.getInt("blockid"));
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive enderman blocks from enderblocks table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
            for (int i = 0; i < 256; i += 1) {
                OEntityEnderman.setHoldable(i, false);
            }
            for (Integer id : enderBlocks) {
                OEntityEnderman.setHoldable(id, true);
            }
        }
    }
    
    @Override
    public void loadAntiXRayBlocks() {
        synchronized (antiXRayBlocksLock) {
            antiXRayBlocks = new ArrayList<Integer>();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                ps = conn.prepareStatement("SELECT * FROM " + table_antixrayblocks);
                rs = ps.executeQuery();
                while (rs.next()) {
                    antiXRayBlocks.add(rs.getInt("blockid"));
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive anti-xray blocks from anti-xray table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }

    // Users
    @Override
    public void addPlayer(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table_users + " (name, groups, prefix, commands, admin, canmodifyworld, ignoresrestrictions) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getName());
            ps.setString(2, etc.combineSplit(0, player.getGroups(), ","));
            ps.setString(3, player.getPrefix());
            ps.setString(4, etc.combineSplit(0, player.getCommands(), ","));
            ps.setBoolean(5, player.getAdmin());
            ps.setBoolean(6, player.canModifyWorld());
            ps.setBoolean(7, player.ignoreRestrictions());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                player.setSqlId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to insert user into users table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public void modifyPlayer(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("UPDATE " + table_users + " SET groups = ?, prefix = ?, commands = ?, admin = ?, canmodifyworld = ?, ignoresrestrictions = ? WHERE id = ?");
            ps.setString(1, etc.combineSplit(0, player.getGroups(), ","));
            ps.setString(2, player.getPrefix());
            ps.setString(3, etc.combineSplit(0, player.getCommands(), ","));
            ps.setBoolean(4, player.getAdmin());
            ps.setBoolean(5, player.canModifyWorld());
            ps.setBoolean(6, player.ignoreRestrictions());
            ps.setInt(7, player.getSqlId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update user in users table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public boolean doesPlayerExist(String player) {
        boolean exists = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table_users + " WHERE name = ?");
            ps.setString(1, player);
            rs = ps.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to check if user exists", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
        return exists;
    }

    // Groups
    @Override
    public void addGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifyGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Kits
    @Override
    public void addKit(Kit kit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifyKit(Kit kit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Homes
    @Override
    public void addHome(Warp home) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table_homes + " (name, x, y, z, rotX, rotY, `group`) VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, home.Name);
            ps.setDouble(2, home.Location.x);
            ps.setDouble(3, home.Location.y);
            ps.setDouble(4, home.Location.z);
            ps.setFloat(5, home.Location.rotX);
            ps.setFloat(6, home.Location.rotY);
            ps.setString(7, home.Group);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                home.ID = rs.getInt(1);
                synchronized (homeLock) {
                    homes.add(home);
                }
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to insert home into homes table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public void changeHome(Warp home) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("UPDATE " + table_homes + " SET x = ?, y = ?, z = ?, rotX = ?, rotY = ?, `group` = ? WHERE name = ?");
            ps.setDouble(1, home.Location.x);
            ps.setDouble(2, home.Location.y);
            ps.setDouble(3, home.Location.z);
            ps.setFloat(4, home.Location.rotX);
            ps.setFloat(5, home.Location.rotY);
            ps.setString(6, home.Group);
            ps.setString(7, home.Name);
            ps.executeUpdate();

            synchronized (homeLock) {
                Warp toRem = null;

                for (Warp h : homes) {
                    if (h.Name.equalsIgnoreCase(home.Name)) {
                        toRem = h;
                    }
                }
                if (toRem != null) {
                    homes.remove(toRem);
                }
                homes.add(home);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update home in homes table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    // Warps
    @Override
    public void addWarp(Warp warp) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table_warps + " (name, x, y, z, rotX, rotY, dimension, `group`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, warp.Name);
            ps.setDouble(2, warp.Location.x);
            ps.setDouble(3, warp.Location.y);
            ps.setDouble(4, warp.Location.z);
            ps.setFloat(5, warp.Location.rotX);
            ps.setFloat(6, warp.Location.rotY);
            ps.setInt(7, warp.Location.dimension);
            ps.setString(8, warp.Group);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                warp.ID = rs.getInt(1);
                synchronized (warpLock) {
                    warps.add(warp);
                }
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to insert warp into warps table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public void changeWarp(Warp warp) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("UPDATE " + table_warps + " SET x = ?, y = ?, z = ?, rotX = ?, rotY = ?, dimension = ?, `group` = ? WHERE name = ?");
            ps.setDouble(1, warp.Location.x);
            ps.setDouble(2, warp.Location.y);
            ps.setDouble(3, warp.Location.z);
            ps.setFloat(4, warp.Location.rotX);
            ps.setFloat(5, warp.Location.rotY);
            ps.setInt(6, warp.Location.dimension);
            ps.setString(7, warp.Group);
            ps.setString(8, warp.Name);
            ps.executeUpdate();

            synchronized (warpLock) {
                Warp toRem = null;

                for (Warp h : warps) {
                    if (h.Name.equalsIgnoreCase(warp.Name)) {
                        toRem = h;
                    }
                }
                if (toRem != null) {
                    warps.remove(toRem);
                }
                warps.add(warp);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update warp in warps table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public void removeWarp(Warp warp) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM " + table_warps + " WHERE id = ?");
            ps.setDouble(1, warp.ID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to delete warp from warps table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
        synchronized (warpLock) {
            warps.remove(warp);
        }
    }

    // Whitelist
    @Override
    public void addToWhitelist(String name) {
        if (isUserOnWhitelist(name)) {
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table_whitelist + " VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update whitelist", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public void removeFromWhitelist(String name) {
        if (!isUserOnWhitelist(name)) {
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM " + table_whitelist + " WHERE name = ?");
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update whitelist", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public Player getPlayer(String name) {
        Player player = new Player();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table_users + " WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                player.setSqlId(rs.getInt("id"));
                player.setGroups(rs.getString("groups").split(","));
                player.setCommands(rs.getString("commands").split(","));
                player.setPrefix(rs.getString("prefix"));
                player.setAdmin(rs.getBoolean("admin"));
                player.setCanModifyWorld(rs.getBoolean("canmodifyworld"));
                player.setIgnoreRestrictions(rs.getBoolean("ignoresrestrictions"));
                player.setIps(rs.getString("ip").split(","));
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to retreive users from user table", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
        return player;
    }

    @Override
    public void loadBanList() {
        synchronized (banLock) {
            bans = new ArrayList<Ban>();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = etc.getSQLConnection();
                ps = conn.prepareStatement("SELECT * FROM " + table_bans);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Ban ban = new Ban();

                    ban.setName(rs.getString("name"));
                    ban.setIp(rs.getString("ip"));
                    ban.setReason(rs.getString("reason"));
                    ban.setTimestamp(rs.getInt("length"));
                    bans.add(ban);
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "Unable to retreive bans from ban table", ex);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {}
            }
        }
    }

    @Override
    public boolean isUserOnWhitelist(String user) {
        boolean toRet = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table_whitelist + " WHERE name = ?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            if (rs.next()) {
                toRet = true;
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to check if user is on whitelist", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
        return toRet;
    }

    @Override
    public void modifyBan(Ban ban) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isUserOnReserveList(String user) {
        boolean toRet = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table_reservelist + " WHERE name = ?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            if (rs.next()) {
                toRet = true;
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to check if user is on reservelist", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
        if (toRet || user.charAt(0) == '@') {
            return toRet;
        }
        Player pl = getPlayer(user);
        String[] playerGroups = pl.getGroups();

        for (int i = 0; i < playerGroups.length; ++i) {
            if (isUserOnReserveList("@" + playerGroups[i])) {
                return true;
            }
        }
        return toRet;
    }

    // Reservelist
    @Override
    public void addToReserveList(String name) {
        if (isUserOnReserveList(name)) {
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table_reservelist + " VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update reservelist", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }

    @Override
    public void removeFromReserveList(String name) {
        if (!isUserOnReserveList(name)) {
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM " + table_reservelist + " WHERE name = ?");
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to update reservelist", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
    }
    
    //Grouplist
    @Override
    public List getGroupList(){
        return this.groups;
    }

	@Override
	public void loadMutedPlayers() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table_muted_players);
           ResultSet rs = ps.executeQuery();
           while(rs.next()) {
        	   this.mutedPlayers.add(rs.getString("name"));
           }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to load muted players list", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
		
	}

	@Override
	public void setPlayerToMuteList(String name) {
		Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table_muted_players + "(name) VALUES (?)");
            ps.setString(1, name);
           ps.executeUpdate();
           this.mutedPlayers.add(name);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to add player to muted players list", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
		
	}

	@Override
	public void removePlayerFromMuteList(String name) {
		Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = etc.getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM " + table_muted_players + "WHERE name = ?");
            ps.setString(1, name);
           ps.executeUpdate();
           this.mutedPlayers.remove(name);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to add player to muted players list", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {}
        }
	}
}
