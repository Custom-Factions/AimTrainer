package net.maidkleid.inventorys;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class GuiInv extends InvHandler.Inv {

    private static final NamespacedKey EVENT_KEY = new NamespacedKey("gui_lib", "gui-event-element");
    private static final NamespacedKey LOCK_KEY = new NamespacedKey("gui_lib", "locked-element");
    private static final ItemStack GLASS_PANE = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private static final HashMap<String, InventoryClickExecutor> keyValueEventMap = new HashMap<>();

    static {
        writeLockUniversalByNameSpace(GLASS_PANE);
        GLASS_PANE.editMeta(itemMeta -> itemMeta.displayName(Component.empty()));
    }

    private final boolean[] unlockedSlots;
    private final ItemStack[] officialItems;
    private final Set<ItemStack> lockedItems = new HashSet<>();
    private final Set<NamespacedKey> lockedKeys = new HashSet<>();
    private final HashMap<ItemStack, InventoryClickExecutor> stackEventMap = new HashMap<>();


    GuiInv(int size, int... unlockedSlots) {
        super(size);
        this.officialItems = getInventory().getContents();
        this.unlockedSlots = initSlots(unlockedSlots);
    }

    GuiInv(@NotNull InventoryType type, int... unlockedSlots) {
        super(type);
        this.officialItems = getInventory().getContents();
        this.unlockedSlots = initSlots(unlockedSlots);
    }

    public static void writeItemStackUniversalNameSpaceEvent(ItemStack stack, String uniqueEventKey, InventoryClickExecutor executor) {
        stack.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(EVENT_KEY, PersistentDataType.STRING, uniqueEventKey);
            keyValueEventMap.put(uniqueEventKey, executor);
        });
    }

    public static @Nullable String getItemStackUniversalNameSpaceEvent(ItemStack stack) {
        PersistentDataContainer persistentDataContainer = stack.getItemMeta().getPersistentDataContainer();
        if (!persistentDataContainer.has(EVENT_KEY)) return null;
        return persistentDataContainer.get(EVENT_KEY, PersistentDataType.STRING);
    }

    public static void writeLockUniversalByNameSpace(ItemStack stack) {
        stack.editMeta(itemMeta -> {
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            persistentDataContainer.set(LOCK_KEY, PersistentDataType.INTEGER, 1);
        });
    }

    public static boolean isLockedByNameSpace(ItemStack stack) {
        PersistentDataContainer persistentDataContainer = stack.getItemMeta().getPersistentDataContainer();
        if (!persistentDataContainer.has(EVENT_KEY)) return false;
        return persistentDataContainer.has(EVENT_KEY);
    }

    private boolean[] initSlots(int... unlockedSlots) {
        int size = inv.getSize();
        boolean[] booleans = new boolean[size];
        for (int unlockedSlot : unlockedSlots) booleans[unlockedSlot] = true;
        for (int i = 0; i < booleans.length; i++) if (!booleans[i]) setItem(i, GLASS_PANE);
        return booleans;
    }

    public void unlockSlot(boolean b, int... unlockedSlots) {
        for (int unlockedSlot : unlockedSlots) this.unlockedSlots[unlockedSlot] = b;
        if (b) for (int unlockedSlot : unlockedSlots) inv.setItem(unlockedSlot, null);
    }

    public void setItem(int slot, @NotNull ItemStack stack) {
        officialItems[slot] = stack;
        inv.setItem(slot, stack);
    }

    public void setEventItem(int slot, @NotNull ItemStack stack, InventoryClickExecutor executor, boolean locked) {
        setItem(slot, stack);
        addEventToItem(stack, executor);
        if (locked) addLockedItem(stack);
    }

    public void addLockedItem(@NotNull ItemStack stack) {
        lockedItems.add(stack);
    }

    public void removeLockedItem(@NotNull ItemStack stack) {
        lockedItems.remove(stack);
    }

    public void addLockedNameSpaceKey(@NotNull NamespacedKey key) {
        lockedKeys.add(key);
    }

    public void removeLockedNameSpaceKey(@NotNull NamespacedKey key) {
        lockedKeys.remove(key);
    }

    public boolean hasLockedItem(@NotNull ItemStack stack) {
        return lockedItems.contains(stack.asOne()) && hasLockedKey(stack);
    }

    public boolean hasLockedKey(@NotNull ItemStack stack) {
        if (!stack.hasItemMeta()) return false;
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        for (NamespacedKey containerKey : container.getKeys()) {
            if (lockedKeys.contains(containerKey)) return true;
        }
        //for (NamespacedKey lockedKey : lockedKeys) if(container.has(lockedKey)) return true;
        return false;
    }

    public boolean hasLockedKey(NamespacedKey key) {
        return lockedKeys.contains(key);
    }

    public boolean hasUnlockedSlotAt(int slot) {
        return unlockedSlots[slot];
    }

    public void addEventToItem(ItemStack stack, InventoryClickExecutor executor) {
        stackEventMap.put(stack, executor);
    }

    public void removeEventToItem(ItemStack stack) {
        stackEventMap.remove(stack);
    }

    @Override
    public boolean onInventoryEvent(@NotNull InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent clickEvent) return onClickEvent(clickEvent);
        if (event instanceof InventoryDragEvent dragEvent) return onDragEvent(dragEvent);
        return false;
    }

    private boolean onDragEvent(InventoryDragEvent dragEvent) {
        //System.out.println("test" + dragEvent);
        for (Integer slot : dragEvent.getRawSlots()) {
            if (checkSlotForEvent(dragEvent, slot)) return false;
        }
        return true;
    }

    private boolean checkSlotForEvent(InventoryInteractEvent event, Integer slot) {
        if (slot < 0 || slot >= inv.getSize()) return true;
        if (!hasUnlockedSlotAt(slot)) event.setCancelled(true);
        ItemStack clickedItem = inv.getItem(slot);
        if (clickedItem == null) return true;
        if (hasLockedItem(clickedItem) || isLockedByNameSpace(clickedItem)) event.setCancelled(true);
        return false;
    }

    private boolean onClickEvent(InventoryClickEvent clickEvent) {
        int slot = clickEvent.getSlot();
        checkSlotForEvent(clickEvent, slot);
        ItemStack item = inv.getItem(slot);
        if (item == null) return true;
        ItemStack clickedItem = Objects.requireNonNull(item).asOne();
        InventoryClickExecutor inventoryClickExecutor = stackEventMap.get(clickedItem);
        if (inventoryClickExecutor != null) inventoryClickExecutor.execute(clickEvent);
        InventoryClickExecutor inventoryClickExecutor1 = keyValueEventMap.get(getItemStackUniversalNameSpaceEvent(clickedItem));
        if (inventoryClickExecutor1 != null) inventoryClickExecutor1.execute(clickEvent);
        return true;
    }


}
