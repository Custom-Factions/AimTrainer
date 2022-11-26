package net.maidkleid.arenas;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
        this(l.getWorld(), l.getX(), l.getY(), l.getZ(), l2.getX(), l2.getY(), l2.getZ());
    }

    @NotNull
    public Location getRandomLocation() {
        return new Location(world,
                random.nextDouble(x1, x2 + 0.000000001),
                random.nextDouble(y1, y2 + 0.000000001),
                random.nextDouble(z1, z2 + 0.000000001));
    }

    public int buildBarrierCage() {
        for (int x = (int) x1 - 1; x < x2 + 2; x++) {
            for (int y = (int) y1 - 1; y < y2 + 2; y++) {
                Block b1 = world.getBlockAt(x, y, (int) z1 - 1);
                if (b1.getType().isAir()) b1.setType(Material.BARRIER);
                Block b2 = world.getBlockAt(x, y, (int) z2 + 1);
                if (b2.getType().isAir()) b2.setType(Material.BARRIER);
            }
        }
        for (int x = (int) x1 - 1; x < x2 + 2; x++) {
            for (int z = (int) z1 - 1; z < z2 + 2; z++) {
                Block b1 = world.getBlockAt(x, (int) y1 - 1, z);
                if (b1.getType().isAir()) b1.setType(Material.BARRIER);
                Block b2 = world.getBlockAt(x, (int) y2 + 1, z);
                if (b2.getType().isAir()) b2.setType(Material.BARRIER);
            }
        }
        for (int y = (int) y1 - 1; y < y2 + 2; y++) {
            for (int z = (int) z1 - 1; z < z2 + 2; z++) {
                Block b1 = world.getBlockAt((int) x1 - 1, y, z);
                if (b1.getType().isAir()) b1.setType(Material.BARRIER);
                Block b2 = world.getBlockAt((int) x2 + 1, y, z);
                if (b2.getType().isAir()) b2.setType(Material.BARRIER);
            }
        }
        return ((((int) x2 - (int) x1 + 2) * ((int) y2 - (int) y1 + 2) * ((int) z2 - (int) z1 + 2))
                -
                ((int) x2 - (int) x1) * ((int) y2 - (int) y1) * ((int) z2 - (int) z1));

    }
}
