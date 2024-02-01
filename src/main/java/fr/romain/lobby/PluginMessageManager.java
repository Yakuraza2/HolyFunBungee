package fr.romain.lobby;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageManager implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e){
        if(e.getTag().equalsIgnoreCase("romain:rush")){
            final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
            final String sub = in.readUTF();

            if(sub.equalsIgnoreCase("PlaceLibre")){
                final String server = in.readUTF();
                final String player = in.readUTF();

                if(!Main.INSTANCE.getServerPlaces().containsKey(server)){
                    System.out.println("Wanted server " + server + " does not exists !");
                    return;
                }
                final ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("PlaceLibre");
                if(Main.INSTANCE.getPlaces(server)>0) {
                    System.out.println("Wanted server " + server + " has place !");
                    Main.INSTANCE.setServerPlaces(server, Main.INSTANCE.getPlaces(server)-1);
                    out.writeUTF("YES");
                }
                else out.writeUTF("NO");
                out.writeUTF(player);
                out.writeUTF(server);

                final ProxiedPlayer proxiedPlayer = Main.INSTANCE.getProxy().getPlayer(e.getReceiver().toString());

                System.out.println("Data sent !");
                proxiedPlayer.getServer().sendData("romain:rush", out.toByteArray());
            }



            if(sub.equalsIgnoreCase("Slots")){
                final String server = in.readUTF();
                final int slots = Integer.parseInt(in.readUTF());

                Main.INSTANCE.setServerPlaces(server, slots);
                System.out.println(server + " has " + slots + " slots left.");

            }
        }
    }
}
