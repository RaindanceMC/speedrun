package de.raindancer.modules.speedrun;

import de.raindancer.core.ui.preference.Preference;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("the splits HUD on /preferences")
class SpeedrunPreferencesTest {

    private final Player player = mock(Player.class);
    private final UUID id = UUID.randomUUID();
    private final List<SpeedrunHudMode> set = new ArrayList<>();

    SpeedrunPreferencesTest() {
        when(player.getUniqueId()).thenReturn(id);
    }

    @Test
    @DisplayName("a choice in group Speedrun with every mode as an option, in the mode's own order")
    void options() {
        Preference hud = SpeedrunPreferences.hud(who -> SpeedrunHudMode.BOSSBAR, (who, mode) -> set.add(mode));
        assertThat(hud.id()).isEqualTo("speedrun:hud");
        assertThat(hud.group()).isEqualTo("Speedrun");
        assertThat(hud.options()).containsExactly("Sidebar", "Boss bar", "Action bar", "Off");
        assertThat(hud.state(player)).isEqualTo("Boss bar");
    }

    @Test
    @DisplayName("choosing sets that mode through the same call as /speedrun hud")
    void choosing() {
        Preference hud = SpeedrunPreferences.hud(who -> SpeedrunHudMode.SIDEBAR, (who, mode) -> set.add(mode));
        hud.choose().accept(player, 2);
        hud.next(player);
        assertThat(set).containsExactly(SpeedrunHudMode.ACTIONBAR, SpeedrunHudMode.BOSSBAR);
    }
}
