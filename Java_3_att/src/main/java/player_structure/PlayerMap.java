package player_structure;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlayerMap {

    private Map<Integer, String> player = new TreeMap<>();

    private Map<Integer, List<Items>> items = new TreeMap<>();

    private Map<Integer, List<Currencies>> currencies = new TreeMap<>();

    private Map<Integer, List<Progresses>> progresses = new TreeMap<>();

    public PlayerMap(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            player.put(p.getPlayerId(), p.getNickname());
            items.put(p.getPlayerId(), p.getItems());
            currencies.put(p.getPlayerId(), p.getCurrencies());
            progresses.put(p.getPlayerId(), p.getProgresses());
        }
    }

    public Map<Integer, String> getPlayer() {
        return player;
    }

    public void setPlayer(Map<Integer, String> player) {
        this.player = player;
    }

    public Map<Integer, List<Items>> getItems() {
        return items;
    }

    public void setItems(Map<Integer, List<Items>> items) {
        this.items = items;
    }

    public Map<Integer, List<Currencies>> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<Integer, List<Currencies>> currencies) {
        this.currencies = currencies;
    }

    public Map<Integer, List<Progresses>> getProgresses() {
        return progresses;
    }

    public void setProgresses(Map<Integer, List<Progresses>> progresses) {
        this.progresses = progresses;
    }
}
