package com.smy.flax.tasks;

import com.smy.flax.Task;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
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

        if(luck < 5){
            switch(rnd){
                case 0:     /*Move mouse out of screen*/
                    api.mouse.moveOutsideScreen();
                    break;
                case 1:     /*Hovers and right clicks on random players*/
                    List<RS2Object> objectList = api.objects.getAll();
                    int right = MethodProvider.random(0,objectList.size());

                    RS2Object obj = objectList.get(right);

                    if(obj != null){
                        if(obj.isVisible()){
                            obj.interact("Examine");
                        } else {
                            api.getCamera().toEntity(obj);

                            obj.hover();

                            MethodProvider.sleep(MethodProvider.random(400,900));

                            api.getMouse().click(true);

                            MethodProvider.sleep(MethodProvider.random(1000,2000));

                            if(MethodProvider.random(0, 100) < 25){
                                int rx = MethodProvider.random(1, api.getBot().getCanvas().getX());
                                int ry = MethodProvider.random(1, api.getBot().getCanvas().getY());
                                api.mouse.move(rx, ry);
                            }
                        }
                    }
                    break;
                case 2:     /*Crafting skill check*/

                    break;
            }
        }
    }
}
