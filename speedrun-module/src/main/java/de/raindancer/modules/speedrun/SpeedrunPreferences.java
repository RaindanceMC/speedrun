package de.raindancer.modules.speedrun;

import de.raindancer.core.ui.preference.Preference;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

/** The splits HUD for Core's {@code /preferences}; {@code /speedrun hud} stays. */
public final class SpeedrunPreferences {

    private SpeedrunPreferences() {
    }

    /**
     * @param current where this player sees the splits now, their own pick or the server's default
     * @param set     what {@code /speedrun hud <place>} does
     */
    public static Preference hud(Function<UUID, SpeedrunHudMode> current, BiConsumer<Player, SpeedrunHudMode> set) {
        SpeedrunHudMode[] modes = SpeedrunHudMode.values();
        List<String> labels = Arrays.stream(modes).map(SpeedrunHudMode::label).toList();
        return Preference.choice("speedrun:hud", "Speedrun", "Speedrun splits display",
                "Where you see your splits during a run: sidebar, boss bar, action bar, or only the clock.", labels,
                player -> current.apply(player.getUniqueId()).ordinal(), (player, index) -> set.accept(player, modes[index]));
    }
}
