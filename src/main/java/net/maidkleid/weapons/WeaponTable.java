package net.maidkleid.weapons;

import net.maidkleid.weaponapi.weaponlib.Weapon;
import net.maidkleid.weaponapi.weaponlib.WeaponProvider;

import java.util.List;

public class WeaponTable extends WeaponProvider {

    public final static int AK47_ID = 100;

    public final static Ak47 AK47 = addConfigWeapon(new Ak47(), AK47_ID);

    public final static List<Weapon> weaponTableOnlyWeapons = List.of(AK47);
    public final static List<String> weaponTableOnlyWeaponsNames = List.of(AK47.getName());

}
