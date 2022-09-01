package net.maidkleid.arenas;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Box {
    private final static Random random = new Random();
    public final World world;
    public final double x1;
    public final double x2;
    public final double y1;
    public final double y2;
    public final double z1;
    public final double z2;

    public Box(World w, double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
        this.world = w;
    }

    public Box(Location l, Location l2) {
        this(l.getWorld(),l.getX(),l.getY(),l.getZ(),l2.getX(),l2.getY(),l2.getZ());
    }

    @NotNull
    public Location getRandomLocation() {
        return new Location(world,
                random.nextDouble(x1,x2+0.000000001),
                random.nextDouble(y1,y2+0.000000001),
                random.nextDouble(z1,z2+0.000000001));
    }
}
