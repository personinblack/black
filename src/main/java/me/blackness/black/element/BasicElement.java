package me.blackness.black.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.blackness.black.Element;
import me.blackness.black.event.ElementClickEvent;

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

/**
 * an element that has all the basic stuff.
 *
 * @see Element
 */
public final class BasicElement implements Element {
    private final String id;
    private final ItemStack icon;
    private final Consumer<ElementClickEvent> function;

    /**
     * ctor.
     *
     * @param icon an icon to represent this element
     * @param function handler for the elementclickevents
     * @param id id of this element. should be unique
     */
    public BasicElement(final ItemStack icon, final Consumer<ElementClickEvent> function,
            final String id) {

        this.id = id;
        this.icon = icon.getType() == Material.AIR
            ? icon
            : encrypted(icon, this.id);
        this.function = Objects.requireNonNull(function);
    }

    /**
     * ctor.
     * @param icon an icon to represent this element
     * @param function handler for the elementclickevents
     */
    public BasicElement(final ItemStack icon, final Consumer<ElementClickEvent> function) {
        this(icon, function, UUID.randomUUID().toString() + System.currentTimeMillis());
    }

    private ItemStack encrypted(final ItemStack itemStack, final String textToEncrypt) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> lore = itemMeta.getLore() == null
            ? new ArrayList<String>()
            : itemMeta.getLore();
        lore.add(encrypted(textToEncrypt));
        itemMeta.setLore(lore);

        final ItemStack encryptedItem = itemStack.clone();
        encryptedItem.setItemMeta(itemMeta);
        return encryptedItem;
    }

    private String encrypted(final String textToEncrypt) {
        final StringBuilder encryptedText = new StringBuilder();
        for (final char ch : textToEncrypt.toCharArray()) {
            encryptedText.append(ChatColor.COLOR_CHAR).append(ch);
        }

        return encryptedText.toString();
    }

    private String decrypted(final ItemStack itemStack) throws IllegalArgumentException {
        if (itemStack.getType() == Material.AIR) {
            return "";
        } else if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
            final List<String> lore = itemStack.getItemMeta().getLore();
            return lore.get(lore.size() - 1).replace(String.valueOf(ChatColor.COLOR_CHAR), "");
        } else {
            throw new IllegalArgumentException(
                "The itemStack couldn't be decrypted because it has no lore\n" +
                itemStack
            );
        }
    }

    @Override
    public void displayOn(final Inventory inventory, final int locX, final int locY) {
        inventory.setItem(locX + locY * 9, icon.clone());
    }

    @Override
    public void accept(final ElementClickEvent event) {
        if (this.is(event.currentItem())) {
            function.accept(event);
        }
    }

    @Override
    public boolean is(final ItemStack itemStack) {
        if (itemStack.getType() == Material.AIR && icon.getType() == Material.AIR) {
            return true;
        }

        try {
            return decrypted(itemStack).equals(id);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public boolean is(final Element element) {
        if (element instanceof BasicElement) {
            return is(((BasicElement) element).icon);
        } else {
            return false;
        }
    }
}
