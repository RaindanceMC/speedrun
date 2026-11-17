package de.raindancer.modules.speedrun.manhunt;

import de.raindancer.core.ui.messages.Messages;
import de.raindancer.core.ui.preference.Preference;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("manhunt on /preferences")
class ManhuntPreferencesTest {

    private final Messages messages = mock(Messages.class);
    private final Player player = mock(Player.class);
    private final PersistentDataContainer data = mock(PersistentDataContainer.class);
    private final ManhuntSettings trailOn = ManhuntSettings.DEFAULTS.withTrackerParticleTrail(true);
    private final ManhuntSettings trailOff = ManhuntSettings.DEFAULTS.withTrackerParticleTrail(false);

    ManhuntPreferencesTest() {
        when(player.getPersistentDataContainer()).thenReturn(data);
    }

    private void stored(boolean on) {
        when(data.get(any(NamespacedKey.class), eq(PersistentDataType.BOOLEAN))).thenReturn(on);
    }

    @Test
    @DisplayName("split titles and the trail, both switches in group Manhunt")
    void built() {
        List<Preference> all = ManhuntPreferences.build(() -> trailOn, messages);
        assertThat(all).extracting(Preference::id).containsExactly("manhunt:titles", "manhunt:trail");
        assertThat(all).extracting(Preference::group).containsOnly("Manhunt");
        assertThat(all).allSatisfy(each -> assertThat(each.options()).containsExactly("On", "Off"));
    }

    @Test
    @DisplayName("titles are read from and kept in the announcer's switch, with the command's messages")
    void titles() {
        Preference titles = ManhuntPreferences.build(() -> trailOn, messages).get(0);
        stored(false);
        assertThat(titles.state(player)).isEqualTo("Off");
        titles.choose().accept(player, 0);
        verify(data).set(any(NamespacedKey.class), eq(PersistentDataType.BOOLEAN), eq(true));
        verify(messages).send(player, "manhunt.announcements.on");
        titles.choose().accept(player, 1);
        verify(messages).send(player, "manhunt.announcements.off");
    }

    @Test
    @DisplayName("the trail is kept in its switch and offered only while the server has the trail on")
    void trail() {
        Preference trail = ManhuntPreferences.build(() -> trailOn, messages).get(1);
        assertThat(trail.shown().test(player)).isTrue();
        trail.choose().accept(player, 1);
        verify(data).set(any(NamespacedKey.class), eq(PersistentDataType.BOOLEAN), eq(false));
        verify(messages).send(player, "manhunt.trail.off");
        assertThat(ManhuntPreferences.build(() -> trailOff, messages).get(1).shown().test(player)).isFalse();
    }

    @Test
    @DisplayName("a switch made while the server has the trail off changes nothing and says so")
    void serverOff() {
        Preference trail = ManhuntPreferences.build(() -> trailOff, messages).get(1);
        trail.choose().accept(player, 1);
        verify(data, never()).set(any(NamespacedKey.class), any(PersistentDataType.class), any());
        verify(messages).send(player, "manhunt.trail.server-off");
    }
}
