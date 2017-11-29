package me.blackness.black.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.blackness.black.Element;
import net.md_5.bungee.api.ChatColor;

/*
       .                                                    .
    .$"                                    $o.      $o.  _o"
   .o$$o.    .o$o.    .o$o.    .o$o.   .o$$$$$  .o$$$$$ $$P `4$$$$P'   .o$o.
  .$$| $$$  $$' $$$  $$' $$$  $$' $$$ $$$| $$$ $$$| $$$ ($o $$$: $$$  $$' $$$
  """  """ """  """ """  """ """  """ """  """ """  """  "  """  """ """  """
.oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo.
  ooo_ ooo ooo. ... ooo. ... ooo.  .. `4ooo.  .`4ooo.   ooo.ooo. ooo ooo.  ..
  $$$"$$$$ $$$| ... $$$| ... $$$$$$ ..    "$$o     "$$o $$$|$$$| $$$ $$$|   .
  $$$| $$$ $$$|     $$$|     $$$|     $$$: $$$ $$$: $$$ $$$|$$$| $$$ $$$|
  $$$| $$$ $$$| $o. $$$| $o. $$$| $o. $$$| $$$ $$$| $$$ $$$|$$$| $$$ $$$| $.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$|$$$| $$$ $$$| $o.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$|$$$| $$$ $$$| $$$
  $$$| $$$  $$. $$$  $$. $$$  $$. $$$ $$$| $$$ $$$| $$$ $$$|$$$| $$$  $$. $$$
  $$$: $P'  `4$$$Ü'__`4$$$Ü'  `4$$$Ü' $$$$$P'  $$$$$P'  $$$|$$$: $P' __`4$$$Ü'
 _ _______/∖______/  ∖______/∖______________/|________ "$P' _______/  ∖_____ _
                                                        i"  personinblack
                                                        |
 */
public final class BasicElement implements Element {
    private final String id;
    private final ItemStack icon;
    private final Consumer<InventoryClickEvent> function;

    public BasicElement(ItemStack icon, Consumer<InventoryClickEvent> function, String id) {
        Objects.requireNonNull(icon);

        this.id = id;
        this.icon = icon.getType().equals(Material.AIR)
            ? icon
            : encrypted(icon, this.id);
        this.function = Objects.requireNonNull(function);
    }

    public BasicElement(ItemStack icon, Consumer<InventoryClickEvent> function) {
        this(icon, function, UUID.randomUUID().toString() + System.currentTimeMillis());
    }

    private ItemStack encrypted(ItemStack itemStack, String textToEncrypt) {
        final StringBuilder encryptedText = new StringBuilder();
        for (char ch : textToEncrypt.toCharArray()) {
            encryptedText.append(ChatColor.COLOR_CHAR).append(ch);
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> lore = itemMeta.getLore() != null
            ? itemMeta.getLore()
            : new ArrayList<String>();
        lore.add(encryptedText.toString());
        itemMeta.setLore(lore);

        final ItemStack encryptedItemStack = itemStack.clone();
        encryptedItemStack.setItemMeta(itemMeta);
        return encryptedItemStack;
    }

    private String decrypted(ItemStack itemStack) throws Exception {
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            throw new Exception("The itemStack couldn't be decrypted because it has no lore");
        } else {
            final List<String> lore = itemStack.getItemMeta().getLore();
            return lore.get(lore.size() - 1).replace(String.valueOf(ChatColor.COLOR_CHAR), "");
        }
    }

    @Override
    public void displayOn(Inventory inventory, int locX, int locY) {
        inventory.setItem(locX + locY * 9, icon.clone());
    }

    @Override
    public void accept(InventoryClickEvent event) {
        if (this.equals(event.getCurrentItem())) {
            function.accept(event);
        }
    }

    @Override
    public boolean equals(ItemStack itemStack) {
        if (itemStack.getType().equals(Material.AIR) && icon.getType().equals(Material.AIR)) {
            return true;
        }

        try {
            return icon.equals(itemStack) && decrypted(itemStack).equals(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equals(Element element) {
        if (element instanceof BasicElement) {
            return equals(((BasicElement) element).icon);
        } else {
            return false;
        }
    }
}
