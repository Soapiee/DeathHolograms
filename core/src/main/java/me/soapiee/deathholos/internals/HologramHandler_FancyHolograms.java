package me.soapiee.deathholos.internals;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import me.soapiee.deathholos.logic.Hologram;
import org.bukkit.Location;

public class HologramHandler_FancyHolograms implements HologramHandler {

    private final HologramManager fancyHologramManager;

    public HologramHandler_FancyHolograms() {
        fancyHologramManager = FancyHologramsPlugin.get().getHologramManager();
    }

    @Override public void spawnHologram(Hologram holo) {
        Location location = holo.getLocation().clone();
        location.setY((location.getY() - 0.5));

        TextHologramData fancyData = new TextHologramData(holo.getKeyID(), location);
        fancyData.setPersistent(false);
        fancyData.setText(holo.getText());

        fancyHologramManager.addHologram(fancyHologramManager.create(fancyData));
    }

    @Override public void despawn(Hologram holo) {
        de.oliver.fancyholograms.api.hologram.Hologram fancyHolo = fancyHologramManager.getHologram(holo.getKeyID()).orElse(null);
        if (fancyHolo == null) return;

        fancyHologramManager.removeHologram(fancyHolo);
    }
}
