package fr.romain.lobby;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;

public class Main extends Plugin {
    public static Main INSTANCE;

    private HashMap<String, Integer> ServerPlaces = new HashMap<>();
    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getProxy().registerChannel("romain:rush");
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageManager());

        for(ServerInfo server : this.getProxy().getServers().values()){
            ServerPlaces.put(server.getName(), 8);
            System.out.println(server.getName() + " slots is by default to 8");
        }
    }

    @Override
    public void onDisable() {

    }

    public void setServerPlaces(String server, int places) {
        ServerPlaces.put(server, places);
    }

    public HashMap<String, Integer> getServerPlaces() {
        return ServerPlaces;
    }

    public int getPlaces(String server) { return this.ServerPlaces.getOrDefault(server, 0); }
}