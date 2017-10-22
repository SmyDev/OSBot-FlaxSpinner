package com.smy.flax.tasks;

import com.smy.flax.Task;
import org.osbot.rs07.script.MethodProvider;

public class AntiBanTask extends Task {

    public AntiBanTask(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() throws InterruptedException {
        int rnd = api.random(0, 100);

    }
}
