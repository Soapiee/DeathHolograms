package me.soapiee.deathholos.internals;

import me.soapiee.deathholos.logic.Hologram;
import me.soapiee.deathholos.utils.Keys;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;

public class HologramHandler_1_19_4 implements HologramHandler {

    @Override public void spawn(Hologram holo) {
        Location location = holo.getLocation().clone();
        location.setY((location.getY() - 0.25));

        for (String line : holo.getText()) {
            TextDisplay text = location.getWorld().spawn(location, TextDisplay.class);
            text.setBillboard(Display.Billboard.CENTER);
            text.getPersistentDataContainer().set(Keys.HOLOGRAMKEY, PersistentDataType.STRING, holo.getKeyID());
            text.setText(Utils.addColour(line));
            location.subtract(0, 0.25, 0);
        }
    }

    //TODO:
//    private void spawnTextDisplay(){}
//    private void spawnItemDisplay(){}
//    private void spawnBlockDisplay(){}

    @Override public void despawn(Hologram holo) {
        Location location = holo.getLocation();
        for (Entity entity : location.getWorld().getNearbyEntities(location, 5, 5, 5)) {
            if (entity instanceof TextDisplay && entity.getPersistentDataContainer().has(Keys.HOLOGRAMKEY, PersistentDataType.STRING)) {
                String id = entity.getPersistentDataContainer().get(Keys.HOLOGRAMKEY, PersistentDataType.STRING);
                if (id.equalsIgnoreCase(holo.getKeyID())) entity.remove();
            }
        }
    }
}
