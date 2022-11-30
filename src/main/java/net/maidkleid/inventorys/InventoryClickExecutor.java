package net.maidkleid.inventorys;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

public interface InventoryClickExecutor {

    Reaction defaultReaction = new Reaction(Sound.ENTITY_ITEM_PICKUP, null);

    static Reaction defaultReaction(Component message) {
        return new Reaction(defaultReaction.sound, message);
    }

    @Nullable Reaction execute(InventoryClickEvent clickEvent);

    record Reaction(@Nullable Sound sound, @Nullable Component message) {
        public void execute(HumanEntity whoClicked) {
            if (message != null) whoClicked.sendMessage(message);
            if (sound != null)
                whoClicked.playSound(net.kyori.adventure.sound.Sound.sound(sound.key(), () -> net.kyori.adventure.sound.Sound.Source.PLAYER, 1, 1), 1, 1, 1);
        }
    }


}
