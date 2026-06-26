package me.soapiee.deathholos.internals;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import me.soapiee.deathholos.logic.Hologram;

public class HologramHandler_FancyHolograms implements HologramHandler {

    private final HologramManager fancyHologramManager;

    public HologramHandler_FancyHolograms (){
        fancyHologramManager = FancyHologramsPlugin.get().getHologramManager();
    }

    @Override public void spawnHologram(Hologram holo) {
        TextHologramData fancyData = new TextHologramData(holo.getKeyID(), holo.getLocation());
        fancyData.setPersistent(false);
        fancyData.setText(holo.getText());

//        de.oliver.fancyholograms.api.hologram.Hologram fancyHolo = fancyHologramManager.create(fancyData);
        fancyHologramManager.addHologram(fancyHologramManager.create(fancyData));

//        DHAPI.createHologram(holo.getKeyID(), holo.getLocation(), holo.getText());
    }

    @Override public void despawn(Hologram holo) {
        de.oliver.fancyholograms.api.hologram.Hologram fancyHolo = fancyHologramManager.getHologram(holo.getKeyID()).orElse(null);
        if (fancyHolo == null) return;

        fancyHologramManager.removeHologram(fancyHolo);

//        DHAPI.removeHologram(holo.getKeyID());
    }
}
