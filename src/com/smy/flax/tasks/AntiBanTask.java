package com.smy.flax.tasks;

import com.smy.flax.Task;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

import java.util.List;

public class AntiBanTask extends Task {

    private long lastOutOfScreen;

    public AntiBanTask(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() throws InterruptedException {
        int rnd = MethodProvider.random(0,2);
        int luck = MethodProvider.random(0, 100);

        if(luck < 25){
            switch(rnd){
                case 0:     /*Move mouse out of screen*/
                    api.mouse.moveOutsideScreen();
                    break;
                case 1:     /*Hovers and right clicks on random players*/
                    List<Player> playerList = api.players.getAll();
                    int right = MethodProvider.random(0,playerList.size());

                    Player p = playerList.get(right);

                    if(p != null){
                        if(p.isVisible()){
                            api.getMouse().click(p.getX(),p.getY(), true);
                        } else {
                            api.getCamera().toEntity(p);

                            api.getMouse().click(p.getX(),p.getY(), true);
                        }
                    }
                    break;
            }
        }
    }
}
