package pt.joelcosta.sttpa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

import static pt.joelcosta.sttpa.Main.*;

public class TpaCommand implements CommandExecutor {

    public static HashMap<String, String> tpaList = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) commandSender.sendMessage("§cEste comando apenas pode ser executado por jogadores.");

        assert commandSender instanceof Player;
        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("tpa")){
            if (strings.length == 0){
                List<String> messageList = getInstance().getConfig().getStringList("TpaMessages.NoArgsFound");

                for (String message : messageList){
                    player.sendMessage(message);
                }
            }

            if (strings.length >= 1){
                String targetName = strings[0];
                Player target = Bukkit.getPlayer(targetName);

                if (!target.isOnline()) player.sendMessage(getInstance().getConfig().getString("PlayerNotOnline").replace("&", "§c"));
                if (player.getName().equalsIgnoreCase(target.getName())) player.sendMessage(getInstance().getConfig().getString("TpaMessages.Yourself").replace("&", "§c"));
                if (tpaList.containsKey(player.getName())) player.sendMessage(getInstance().getConfig().getString("TpaMessages.OnGoing").replace("&", "§c"));

                if (!tpaList.containsKey(player.getName())) {

                    List<String> messageList = getInstance().getConfig().getStringList("TpaMessages.Sended");

                    for (String message : messageList){
                        player.sendMessage(message);
                    }

                    tpaList.put(player.getName(), target.getName());

                    target.sendMessage(getInstance().getConfig().getString("TpaMessages.WhoWants").replace("&", "§c").replace("%player%", player.getName()));
                    target.sendMessage(getInstance().getConfig().getString("TpaMessages.Accept").replace("&", "§c").replace("%player%", player.getName()));
                    target.sendMessage(getInstance().getConfig().getString("TpaMessages.Refuse").replace("&", "§c").replace("%player%", player.getName()));
                }
            }
        }
        return false;
    }
}
