package pt.joelcosta.sttpa;

import org.bukkit.plugin.java.JavaPlugin;
import pt.joelcosta.sttpa.commands.ATpaCommand;
import pt.joelcosta.sttpa.commands.RTpaCommand;
import pt.joelcosta.sttpa.commands.TpaCommand;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("atpa").setExecutor(new ATpaCommand());
        getCommand("rtpa").setExecutor(new RTpaCommand());
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    public static Main getInstance() {
        return instance;
    }
}
