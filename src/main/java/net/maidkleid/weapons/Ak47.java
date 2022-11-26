package net.maidkleid.weapons;

import net.maidkleid.weaponapi.weaponlib.AmmoType;
import net.maidkleid.weaponapi.weaponlib.ProjectileWeaponInstance;
import net.maidkleid.weaponapi.weaponlib.Weapon;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.maidkleid.weaponapi.weaponlib.AmmoType.AR_AMMO;

public class Ak47 implements Weapon {

    @Override
    public double getSpread(int i) {
        return 0;
    }

    @Override
    public int getMagSize(int i) {
        return 60;
    }

    @Override
    public String getName() {
        return "Ak47";
    }

    @Override
    public AmmoType getAmmoType() {
        return AR_AMMO;
    }

    @Override
    public ProjectileWeaponInstance<Firework> getWeaponNewInstance(Player player, int i, ItemStack itemStack) {
        return new ProjectileWeaponInstance<>(this, player, i, itemStack, Firework.class, 5);
    }

    @Override
    public Sound getShootSound(int i) {
        return Sound.BLOCK_BAMBOO_HIT;
    }

    @Override
    public double getBulletDamage(int i) {
        return 0;
    }

    @Override
    public long getReloadMagazineTime(int i) {
        return 0;
    }

    @Override
    public long getReloadTime(int i) {
        return 0;
    }

    @Override
    public Particle getParticle() {
        return Particle.ASH;
    }

}
