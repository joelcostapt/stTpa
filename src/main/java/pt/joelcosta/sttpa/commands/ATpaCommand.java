package pt.joelcosta.sttpa.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static pt.joelcosta.sttpa.Main.getInstance;

public class ATpaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) commandSender.sendMessage("§cEste comando apenas pode ser executado por jogadores.");

        assert commandSender instanceof Player;
        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("atpa")){
            if (strings.length == 0){
                List<String> messageList = getInstance().getConfig().getStringList("AcceptTpaMessages.NoArgsFound");

                for (String message : messageList){
                    player.sendMessage(message);
                }
            }

            if (strings.length >= 1){
                String pName = strings[0];
                Player pCome = Bukkit.getPlayer(pName);

                if (!pCome.isOnline()) player.sendMessage(getInstance().getConfig().getString("PlayerNotOnline"));

                if (TpaCommand.tpaList.containsKey(pCome.getName())){
                    pCome.teleport(player.getLocation());
                    player.sendMessage(getInstance().getConfig().getString("AcceptTpaMessages.Accepted").replace("&", "§").replace("%player%", pCome.getName()));
                    pCome.sendMessage(getInstance().getConfig().getString("AcceptTpaMessages.PlayerAccepted").replace("&", "§").replace("%player%", player.getName()));
                    TpaCommand.tpaList.remove(pCome.getName());
                } else {
                   player.sendMessage(getInstance().getConfig().getString("Nothing").replace("&", "§"));
                }
            }
        }
        return false;
    }
}
