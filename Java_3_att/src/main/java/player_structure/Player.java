package player_structure;

import java.util.List;

public class Player {
    private int playerId;
    private String nickname;
    private List<Progresses> progresses;
    private List<Currencies> currencies;
    private List<Items> items;


    public Player() {
        this.playerId = -1;
        this.nickname = null;
        this.progresses = null;
        this.currencies = null;
        this.items = null;
    }
    public Player(int playerId, String nickname, List<Progresses> progresses, List<Currencies> currencies, List<Items> items) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.progresses = progresses;
        this.currencies = currencies;
        this.items = items;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Progresses> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<Progresses> progresses) {
        this.progresses = progresses;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
        this.currencies = currencies;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", nickname='" + nickname + '\'' +
                ", progresses=" + progresses +
                ", currencies=" + currencies +
                ", items=" + items +
                '}';
    }
}
