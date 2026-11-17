package de.raindancer.modules.speedrun.manhunt;

import de.raindancer.core.ui.messages.Messages;
import de.raindancer.core.ui.preference.Preference;
import de.raindancer.modules.speedrun.SpeedrunSplitAnnouncer;
import de.raindancer.modules.speedrun.manhunt.tracker.TrailPreference;

import java.util.List;
import java.util.function.Supplier;

/** Manhunt's personal switches for Core's {@code /preferences}; {@code /manhunt announcements} and {@code trail} stay. */
public final class ManhuntPreferences {

    private static final String GROUP = "Manhunt";

    private ManhuntPreferences() {
    }

    public static List<Preference> build(Supplier<ManhuntSettings> settings, Messages messages) {
        Preference titles = Preference.toggle("manhunt:titles", GROUP, "Speedrun split titles",
                "Shows a title on screen when somebody reaches a split, like a new personal best.",
                SpeedrunSplitAnnouncer.TITLES::isOn, (player, on) -> {
                    SpeedrunSplitAnnouncer.TITLES.set(player, on);
                    messages.send(player, on ? "manhunt.announcements.on" : "manhunt.announcements.off");
                });
        Preference trail = Preference.toggle("manhunt:trail", GROUP, "Compass particle trail",
                "Shows the dotted line to whatever your compass, or a clicked position, points at.",
                player -> TrailPreference.SWITCH.isOn(player), (player, on) ->
                        messages.send(player, TrailPreference.messageKey(TrailPreference.set(player, settings.get(), on))))
                .shownTo(player -> settings.get().trackerParticleTrail());
        return List.of(titles, trail);
    }
}
