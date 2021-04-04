package farmerthanos.mea;

import arc.func.Cons;
import arc.util.Log;
import arc.util.Timer;
import mindustry.gen.*;

import static mindustry.Vars.*;
import static arc.scene.actions.Actions.delay;

public class Calls {
    public static void load() {
        //I thank megacatswoop#7269 for this
        //Does not work with walls, as they seem to do nothing. :^)
        Timer.schedule(() -> {
            for (Building build : Groups.build) {
                class Breakable implements Cons<Bullet> {
                    public boolean stop = false;

                    @Override
                    public void get(Bullet bullet) {
                        if (bullet.team != build.team && bullet.team != player.team()) {
                            Log.info("Base is under attack.");
                            Announcements.BaseUnderAttack.play();
                            delay(120);
                            stop = true;
                        }
                    }
                }
                Breakable intersect = new Breakable();

                Groups.bullet.intersect(build.x - (build.block().size / 2) - 8 * 8,
                        build.y - (build.block().size / 2) - 8 * 8,
                        8 * 8, 8 * 8, intersect);
                if (intersect.stop) break;
            }
        }, 0f, 0.5f);
    }
}